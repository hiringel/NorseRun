package com.example.norserun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity {

	//Instansiere Button widget
	Button kartButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Button-ting, lage button og lage listener
		addListenerOnButton();
	}

	//Dette er for menu-knappen
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		kartButton = (Button) findViewById(R.id.kartButton);
 
		kartButton.setOnClickListener(new OnClickListener() {
 
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, KartActivity.class);
                            startActivity(intent);   
 
			}
 
		});
	}
	
	
}
