package com.example.norserun;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service implements LocationListener{
	private static final String TAG = "LOCATION_SERVICE";
	private static final int TIME_THRESHOLD = 1;  //1sec
	private static final int ACCURACY_PERCENT = 10; //%
	private static final int VELOCITY_THRESHOLD = 100; //m/s
	
	
	private LocationManager locationManager;
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
		KartActivity.dummyActivity.DrawPolyLine(location);
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
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "Service, onstartcommand..");
		for (String provider : locationManager.getProviders(true))
		{
			locationManager.requestLocationUpdates(provider, 0, 0, this);
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
	


}
