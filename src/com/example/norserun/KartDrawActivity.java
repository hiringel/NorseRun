package com.example.norserun;

import java.util.ArrayList;
import java.util.List;

import no.nkgs.webatlas.android.WACRS;
import no.nkgs.webatlas.android.WACoordinate;
import no.nkgs.webatlas.android.WADrawLayer;
import no.nkgs.webatlas.android.WALayer;
import no.nkgs.webatlas.android.WAMapStyle;
import no.nkgs.webatlas.android.WAMapView;
import no.nkgs.webatlas.android.WAPolyLine;
import no.nkgs.webatlas.android.WASettings;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class KartDrawActivity extends Activity{
	
	WAMapView mapView;
	WALayer layer;
	WADrawLayer drawLayer;
	WADrawLayer redLayer;
	WADrawLayer greenLayer;
	private static String SQL_TAG = "SQL_TAG";
	
	WAPolyLine fastLine;
	WAPolyLine slowLine;
	WAPolyLine medLine;
	List<Posisjon> liste;
	
	String nameFromDb;
	String latFromDb;
	String longtFromDb;
	String timeFromDb;
	
	private boolean firstDraw;
	
	public RemindersDbAdapter db;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawkartet); //Bytt
		firstDraw = false;
		
		mapView = (WAMapView) findViewById(R.id.kartetdraw); //Bytt
		 WASettings.getInstance().setTilesFadeIn(true); 
		 layer = new WALayer(WAMapStyle.VECTOR);
	     mapView.addLayer(layer);				
	     fastLine = new WAPolyLine();
	     slowLine = new WAPolyLine();
	     medLine = new WAPolyLine();
	     fastLine.setARGB(1, 254, 0, 0);
	     medLine.setARGB(1, 254, 0, 254);
	     slowLine.setARGB(1, 0, 254, 0);
	     drawLayer = new WADrawLayer();
	     redLayer = new WADrawLayer();
	     greenLayer = new WADrawLayer();
		
	}
	
	private void DrawTheRoute(List<Posisjon> lol){
		
		for(Posisjon loc : lol){
			fastLine.addCoordinate(new WACoordinate(loc.getLongitude(), loc.getLatitude(), WACRS.EPSG4326));
		}
		drawLayer.addPolyLine(fastLine);
		mapView.addDrawLayer(drawLayer);

		mapView.centerOnCoordinate(new WACoordinate(lol.get(0).getLongitude(),lol.get(0).getLatitude(), WACRS.EPSG4326));
		
		
	}
	
	private void DrawRouteSpeed(List<Posisjon> loclist){
		double tempSpeed = 0;
		Location A = new Location("start");
		Location B = new Location("end");

		
		if(loclist.isEmpty()) return;
		Posisjon lastLoc = loclist.get(0);
		for(Posisjon loc : loclist){
			if(!(lastLoc == null)){
				A.setLatitude(lastLoc.getLatitude());
				A.setLongitude(lastLoc.getLongitude());
				A.setTime(lastLoc.getTime());
				B.setLatitude(loc.getLatitude());
				B.setLongitude(loc.getLongitude());
				B.setTime(loc.getTime());
				tempSpeed = A.distanceTo(B)/((B.getTime()-A.getTime())/1000);
				if(tempSpeed > 15){
					fastLine.addCoordinate(new WACoordinate(lastLoc.getLongitude(), lastLoc.getLatitude(), WACRS.EPSG4326));
					fastLine.addCoordinate(new WACoordinate(loc.getLongitude(), loc.getLatitude(), WACRS.EPSG4326));
					redLayer.addPolyLine(fastLine);
				}
				else if (tempSpeed > 10) {
					medLine.addCoordinate(new WACoordinate(lastLoc.getLongitude(), lastLoc.getLatitude(), WACRS.EPSG4326));
					medLine.addCoordinate(new WACoordinate(loc.getLongitude(), loc.getLatitude(), WACRS.EPSG4326));
					drawLayer.addPolyLine(medLine);
				}
				else{
					slowLine.addCoordinate(new WACoordinate(lastLoc.getLongitude(), lastLoc.getLatitude(), WACRS.EPSG4326));
					slowLine.addCoordinate(new WACoordinate(loc.getLongitude(), loc.getLatitude(), WACRS.EPSG4326));
					greenLayer.addPolyLine(slowLine);
				}
				lastLoc = loc;
			}
		}
		mapView.addDrawLayer(drawLayer);
		mapView.addDrawLayer(redLayer);
		mapView.addDrawLayer(greenLayer);
		mapView.centerOnCoordinate(new WACoordinate(loclist.get(0).getLongitude(),loclist.get(0).getLatitude(), WACRS.EPSG4326));
		return;
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.resume();
        db = new RemindersDbAdapter(this);
        db.open();
        
        liste = new ArrayList<Posisjon>();
        
        try {
			Log.d(SQL_TAG, "tripChosenInt = " + String.valueOf(MainActivity.tripChosenInt));
			
			Log.d(SQL_TAG, "(long)MainActivity.tripChosenInt = " +(long)MainActivity.tripChosenInt);
			
			Cursor reminder = db.fetchReminder((long)MainActivity.tripChosenInt);
			
			 this.startManagingCursor(reminder);
			Log.d(SQL_TAG, "reminder = " +reminder.toString());
			nameFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TITLE));
			latFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_LAT));
			longtFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_LONG));
			timeFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TIME));
			Log.d(SQL_TAG, "Strings: " + nameFromDb + latFromDb + longtFromDb + timeFromDb);
			
			liste = StatisticsHelper.StringDeserializer(latFromDb, longtFromDb, timeFromDb);
			
//			Log.d("POSISJON", String.valueOf(StatisticsHelper.GetAverageSpeed(liste)));
//			Log.d("POSISJON", String.valueOf(StatisticsHelper.GetDistance(liste)));
			}
			catch (Exception e){
				Log.d(SQL_TAG, "Exception: "+e.toString()+ "       StackTrace = "+e.getStackTrace()+"   nameFromDb = "+nameFromDb);
			}
		if(firstDraw == false){
			DrawRouteSpeed(liste);
			firstDraw = true;
		}
		
	}
 
	@Override
	protected void onPause() {
		super.onPause();
		db.close();
		mapView.pause();
	}
 
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		mapView.dispose();
	}

}
