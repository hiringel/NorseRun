package com.example.norserun;

import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import no.nkgs.webatlas.android.WACRS;
import no.nkgs.webatlas.android.WACoordinate;
import no.nkgs.webatlas.android.WADrawLayer;
import no.nkgs.webatlas.android.WALayer;
import no.nkgs.webatlas.android.WAMapStyle;
import no.nkgs.webatlas.android.WAMapView;
import no.nkgs.webatlas.android.WAPolyLine;
import no.nkgs.webatlas.android.WAPositionMarker;
import no.nkgs.webatlas.android.WASettings;


public class KartActivity extends Activity implements OnClickListener{

	WAMapView mapView;
	WALayer layer;
	WADrawLayer drawLayer;
	WADrawLayer redLayer;
	WADrawLayer greenLayer;
	private Location lastLocation;
	public static KartActivity dummyActivity;
	public RemindersDbAdapter db;
	private boolean GPSServiceEnabled;
	
	WAPolyLine fastLine;
	WAPolyLine slowLine;
	WAPolyLine medLine;
	
	private static final String TAGService = "LOCATION_SERVICE";
	
	//Variabler
	private static final String TAG = "FilterLocation";

	Trip currentTrip;
	private Boolean tracking, firstDraw;
	
	private void startGPSService(){
		if(!GPSServiceEnabled){
		startService(new Intent(KartActivity.this, GPSService.class));
		GPSService.startTracking();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kartet);
		startService(new Intent(KartActivity.this, GPSService.class));
		GPSServiceEnabled = true;
		
		Button startTracking = (Button)findViewById(R.id.startTrack);
		Button stopTracking = (Button)findViewById(R.id.stopTrack);
		startTracking.setOnClickListener(this);
		stopTracking.setOnClickListener(this);
		
		mapView = (WAMapView) findViewById(R.id.kartet);
        WASettings.getInstance().setTilesFadeIn(true);    //Denne er valgfri men visuelt penere
        //      Init mapView
        layer = new WALayer(WAMapStyle.VECTOR);
        mapView.addLayer(layer);					//Du må ha et "filter" for at mappet ditt skal vises på skjermen
        
	     fastLine = new WAPolyLine();
	     slowLine = new WAPolyLine();
	     medLine = new WAPolyLine();
	     fastLine.setARGB(1, 254, 0, 0);
	     medLine.setARGB(1, 254, 0, 254);
	     slowLine.setARGB(1, 0, 254, 0);
	     drawLayer = new WADrawLayer();
	     redLayer = new WADrawLayer();
	     greenLayer = new WADrawLayer();
        drawLayer = new WADrawLayer();
        dummyActivity = this;
        
        db = new RemindersDbAdapter(this);
//        db.open();
        
        
        
        
        tracking = true;
        firstDraw = true;
        
        
        
        currentTrip = new Trip("DummyTrip", "Run");
        
		
	}
	Handler handler = new Handler(){
	public void handleMessage(Message msg){
		Log.d(TAG, "Handle my message: "+msg.toString());
	}
	};


	
	@Override
	protected void onResume() {
		super.onResume();
		db.open();
		mapView.resume();
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
 
	@Override
	protected void onDestroy() {
		stopService(new Intent(KartActivity.this, GPSService.class));
		mapView.dispose();
		super.onDestroy();
	}
	
//	protected void DrawPolyLine(Location location){
//		this.tripLine.addCoordinate(new WACoordinate(location.getLongitude(), location.getLatitude(), WACRS.EPSG4326));
//		this.drawLayer.addPolyLine(tripLine);
//		if(!this.mapView.getDrawLayers().contains(drawLayer))this.mapView.addDrawLayer(drawLayer);
//		Log.d(TAGService, "Drawing..");
//		
//		
//		if(this.firstDraw){
//			Log.d(TAGService, "Drawing first time");
//			this.mapView.centerOnCoordinate(new WACoordinate(location.getLongitude(),location.getLatitude(), WACRS.EPSG4326));
//			
//			firstDraw = false;
//		}
//		
//	}
	
	public void jumpToFirst(Location location){
		this.mapView.centerOnCoordinate(new WACoordinate(location.getLongitude(),location.getLatitude(), WACRS.EPSG4326));
	}
	
//	protected void DrawTheRoute(List<Location> lol){
//		
//		for(Location loc : lol){
//			tripLine.addCoordinate(new WACoordinate(loc.getLongitude(), loc.getLatitude(), WACRS.EPSG4326));
//		}
//		drawLayer.addPolyLine(tripLine);
//		mapView.addDrawLayer(drawLayer);
//		mapView.centerOnCoordinate(new WACoordinate(lol.get(0).getLongitude(),lol.get(0).getLatitude(), WACRS.EPSG4326));
//		
//		
//	}
	
	protected void DrawRouteSpeed(List<Location> loclist){
		double tempSpeed = 0;
		Location A = new Location("start");
		Location B = new Location("end");

		
		if(loclist.isEmpty()) return;
		Location lastLoc = loclist.get(0);
		for(Location loc : loclist){
			if(loclist.indexOf(loc) == loclist.size()-1) continue;
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


	
	//Onclick implementation
	@Override
	public void onClick(View v) {
		Log.d(TAG, "OnCLick was ticked in Kart.xml");
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startTrack:
		{	
			Log.d(TAG, "started tracking");
			startGPSService();
			GPSService.startTracking();
			break;
		}
		
		case R.id.stopTrack:
		{	
			Log.d(TAG, "stopped tracking");
			GPSService.stopTracking();
			stopService(new Intent(KartActivity.this, GPSService.class));
			GPSServiceEnabled = false;
			break;
		}
		case R.id.kartet:
		{
			Log.d(TAGService, "kartet ble klikka");
			break;
		}
			
			
		}
	}
}
