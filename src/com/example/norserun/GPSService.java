package com.example.norserun;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service implements LocationListener{
	private static final String TAG = "LOCATION_SERVICE";
	private static final int TIME_THRESHOLD = 2000;  //2sec
	private static final int ACCURACY_PERCENT = 10; //%
	private static final int VELOCITY_THRESHOLD = 100; //m/s
	
	
	private LocationManager locationManager;
	private Location lastLocation;
	private static boolean isTracking = false;
	
	
	//Fjern meg etter test
	private static List<Location> listloc;
	

	
	@Override
	public IBinder onBind(Intent intent)
	{
		Log.d(TAG, "Service started");
		return null;
	// ...
	}
	
	@Override
	public void onLocationChanged(Location location)
	{
	// ...
		Log.d(TAG, "Service, Location has changed");
		if(isTracking){
		LocationFilter(location);
		}
	}
	
	@Override
	public void onProviderDisabled(String provider)
	{
	// ...
	}
	
	@Override
	public void onProviderEnabled(String provider)
	{
	locationManager.requestLocationUpdates(provider, TIME_THRESHOLD, 0, this);
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	// ...
	}
	
	@Override
	public void onCreate()
	{
	super.onCreate();
	locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	listloc = new ArrayList<Location>();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "Service, onstartcommand..");
		for (String provider : locationManager.getProviders(true))
		{
			locationManager.requestLocationUpdates(provider, TIME_THRESHOLD, 0, this);
			Log.d(TAG, "Added provider: "+provider);
		}
	return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy()
	{	
		Log.d(TAG, "Service destroyed");
		super.onDestroy();
		locationManager.removeUpdates(this);
	}
	
	
	private void LocationFilter(Location location) {
		// TODO Auto-generated method stub
	
		
	
		if(lastLocation == null){
			Log.d(TAG, "Adding point");
			//KartActivity.dummyActivity.DrawPolyLine(location);
			onLocationChangedDB(location);	
		}
		else
		{
		float currentAccuracy = location.getAccuracy();
		float previousAccuracy = lastLocation.getAccuracy();

		float accuracyDifference = Math.abs(previousAccuracy - currentAccuracy);
					
		boolean lowerAccuracyAcceptable = 	currentAccuracy > previousAccuracy
														&& lastLocation.getProvider().equals(location.getProvider())
														&& (accuracyDifference <= previousAccuracy / ACCURACY_PERCENT);
					
		float[] results = new float[1];
		Location.distanceBetween(lastLocation.getLatitude(),
		lastLocation.getLongitude(),
		location.getLatitude(),
		location.getLongitude(),
		results);
		float velocity =
		results[0] / ((location.getTime() - lastLocation.getTime()) / 1000);
		
					
		// * The velocity seems reasonable (point did not jump)and one of the
		// following:
		// * It has a better accuracy
		// * The app has not accepted a point in TIME_THRESHOLD
		// * It's worse accuracy is still acceptable
		if (velocity <= VELOCITY_THRESHOLD
		&& (currentAccuracy < previousAccuracy
		|| (location.getTime() - lastLocation.getTime()) > TIME_THRESHOLD
		|| lowerAccuracyAcceptable))
		{
						
			Log.d(TAG, "Adding point after filter");
			//KartActivity.dummyActivity.DrawPolyLine(location);
			onLocationChangedDB(location);
					
		}
		else
		{
						
			Log.d(TAG, "Ignoring point");
					
		}	
	}
		
	lastLocation = location;	
	}
	
	public static void startTracking(){
		Log.d(TAG, "Started tracking");
		isTracking = true;
	}
	
	public static void stopTracking(){
		Log.d(TAG, "Stopped tracking");
		isTracking = false;
		createDbTuple(listloc);
		if(!listloc.isEmpty()) 		KartActivity.dummyActivity.DrawTheRoute(listloc);
		

		//Kick the draw and read from db
		
	}

	private void onLocationChangedDB(Location loc) {
	     Log.e(TAG, loc.toString());
	     listloc.add(loc);
	     

	     ContentValues values = new ContentValues();
	   
	     values.put(GPSData.GPSPoint.LONGITUDE, loc.getLongitude());
	        values.put(GPSData.GPSPoint.LATITUDE, loc.getLatitude());
	        values.put(GPSData.GPSPoint.TIME, loc.getTime());
	     getContentResolver().insert(GPSDataContentProvider.CONTENT_URI, values);
	   }
	
	public static void createDbTuple(List<Location> loclist){
		String lat = "";
		String lon = "";
		String time = "";
		
		for(Location loc : loclist){
			lat += (loc.getLatitude()+" ");
			lon += (loc.getLongitude()+ " ");
			time += (loc.getTime()+ " ");
		}
		
		Log.d(TAG, lat);
		
//		RemindersDbAdapter lol = new RemindersDbAdapter(getApplicationContext());
//	    lol.createReminder(title, latitude, longtitude, pointTime, reminderDateTime);
	}


}
