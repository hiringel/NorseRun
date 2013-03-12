package com.example.norserun;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import no.nkgs.webatlas.android.WALayer;
import no.nkgs.webatlas.android.WAMapStyle;
import no.nkgs.webatlas.android.WAMapView;
import no.nkgs.webatlas.android.WASettings;


public class KartActivity extends Activity {

	WAMapView mapView;
	WALayer layer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kartet);
		mapView = (WAMapView) findViewById(R.id.kartet);
        WASettings.getInstance().setTilesFadeIn(true);    //Denne er valgfri men visuelt penere
        //      Init mapView
        layer = new WALayer(WAMapStyle.VECTOR);
        mapView.addLayer(layer);					//Du må ha et "filter" for at mappet ditt skal vises på skjermen
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
	
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
		mapView.dispose();
 
		super.onDestroy();
	}
}
