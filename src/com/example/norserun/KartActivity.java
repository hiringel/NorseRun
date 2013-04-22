package com.example.norserun;

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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
	private Location lastLocation;
	public static KartActivity dummyActivity;
	
	private static final String TAGService = "LOCATION_SERVICE";
	
	//Variabler
	private static final String TAG = "FilterLocation";

	Trip currentTrip;
	WAPolyLine tripLine;
	private Boolean tracking, firstDraw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kartet);
		startService(new Intent(KartActivity.this, GPSService.class));
		
		mapView = (WAMapView) findViewById(R.id.kartet);
        WASettings.getInstance().setTilesFadeIn(true);    //Denne er valgfri men visuelt penere
        //      Init mapView
        layer = new WALayer(WAMapStyle.VECTOR);
        mapView.addLayer(layer);					//Du må ha et "filter" for at mappet ditt skal vises på skjermen
        
        tripLine = new WAPolyLine();
        tripLine.setARGB(1, 255, 0, 0);
        drawLayer = new WADrawLayer();
        dummyActivity = this;
        
        
        
        
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
		mapView.resume();
	}
 
	@Override
	protected void onPause() {
		super.onPause();

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
	
	protected void DrawPolyLine(Location location){
		this.tripLine.addCoordinate(new WACoordinate(location.getLongitude(), location.getLatitude(), WACRS.EPSG4326));
		this.drawLayer.addPolyLine(tripLine);
		if(!this.mapView.getDrawLayers().contains(drawLayer))this.mapView.addDrawLayer(drawLayer);
		Log.d(TAGService, "Drawing..");
		
		
		if(this.firstDraw){
			Log.d(TAGService, "Drawing first time");
			this.mapView.centerOnCoordinate(new WACoordinate(location.getLongitude(),location.getLatitude(), WACRS.EPSG4326));
			
			firstDraw = false;
		}
		
	}






	
	
	//Onclick implementation
	@Override
	public void onClick(View v) {
		Log.d(TAG, "OnCLick was ticked in Kart.xml");
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startTrack:
		{	
			Log.d(TAGService, "started tracking");
			tracking = true;
			break;
		}
		
		case R.id.stopTrack:
		{	
			Log.d(TAGService, "stopped tracking");
			tracking = false;
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
