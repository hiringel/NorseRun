package com.example.norserun;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import no.nkgs.webatlas.android.WALayer;
import no.nkgs.webatlas.android.WAMapStyle;
import no.nkgs.webatlas.android.WAMapView;
import no.nkgs.webatlas.android.WAPositionMarker;
import no.nkgs.webatlas.android.WASettings;


public class KartActivity extends Activity {

	WAMapView mapView;
	WALayer layer;
	private LocationManager locationManager;
	Trip currentTrip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kartet);
		mapView = (WAMapView) findViewById(R.id.kartet);
        WASettings.getInstance().setTilesFadeIn(true);    //Denne er valgfri men visuelt penere
        //      Init mapView
        layer = new WALayer(WAMapStyle.VECTOR);
        mapView.addLayer(layer);					//Du må ha et "filter" for at mappet ditt skal vises på skjermen
        
        locationManager = (LocationManager) this.getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        mapView.setPositionMarker(new WAPositionMarker(R.drawable.abs__spinner_48_inner_holo));
        WASettings.getInstance().setFollowMode(true);
        
        
        
       currentTrip = new Trip("DummyTrip", "Run");
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
		
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 6000, 0.1f, mapView);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				6000, 0.1f, mapView);
 
		mapView.resume();
	}
 
	@Override
	protected void onPause() {
		super.onPause();
 
		locationManager.removeUpdates(mapView);
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
