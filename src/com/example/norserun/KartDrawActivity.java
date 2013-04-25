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
	private static String SQL_TAG = "SQL_TAG";
	
	WAPolyLine tripLine;
	List<Posisjon> liste;
	
	String nameFromDb;
	String latFromDb;
	String longtFromDb;
	String timeFromDb;
	
	public RemindersDbAdapter db;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawkartet); //Bytt
		
		mapView = (WAMapView) findViewById(R.id.kartetdraw); //Bytt
		 WASettings.getInstance().setTilesFadeIn(true); 
		 layer = new WALayer(WAMapStyle.VECTOR);
	     mapView.addLayer(layer);				
	     tripLine = new WAPolyLine();
	     tripLine.setPaint(new Paint(Color.RED));
	     drawLayer = new WADrawLayer();
		
	}
	
	private void DrawTheRoute(List<Posisjon> lol){
		
		for(Posisjon loc : lol){
			tripLine.addCoordinate(new WACoordinate(loc.getLongitude(), loc.getLatitude(), WACRS.EPSG4326));
		}
		drawLayer.addPolyLine(tripLine);
		mapView.addDrawLayer(drawLayer);
		mapView.centerOnCoordinate(new WACoordinate(lol.get(0).getLongitude(),lol.get(0).getLatitude(), WACRS.EPSG4326));
		
		
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
		
		DrawTheRoute(liste);
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
