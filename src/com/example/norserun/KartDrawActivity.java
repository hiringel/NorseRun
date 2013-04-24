package com.example.norserun;

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
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;

public class KartDrawActivity extends Activity{
	
	WAMapView mapView;
	WALayer layer;
	WADrawLayer drawLayer;
	
	WAPolyLine tripLine;
	
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
		DrawTheRoute(StatisticsFragment.sendList);
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
	
	protected void onDestroy() {
		mapView.dispose();
		super.onDestroy();
	}

}
