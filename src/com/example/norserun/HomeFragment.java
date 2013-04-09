package com.example.norserun;

import java.util.zip.Inflater;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.internal.widget.ActionBarView.HomeView;

public class HomeFragment extends SherlockFragment implements OnClickListener{
	
	private EditText result;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from homefrag");
		
		//Create the view
		View homeFragment = inflater.inflate(R.layout.home_layout, group, false);
		
		//Set onclicklistneres where they belong, to "this" class
		homeFragment.findViewById(R.id.kartButton).setOnClickListener(this);
		homeFragment.findViewById(R.id.settingsButton).setOnClickListener(this);
		
		
		//Return to be drawed
		return homeFragment;
	}
	
	public void onResume(){
		super.onResume();
	}
	
	public void onStop(){
		super.onStop();
	}



	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.kartButton:
			{
				Log.d("Click", "Clicked mapbutton");

				Intent goToMap = new Intent(this.getView().getContext(), KartActivity.class);
				startActivity(goToMap);
				break;
			}
		
			case R.id.settingsButton:
			{
				Log.d("Click", "Clicked settingsbutton");
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				break;
			}
		}
		
	}
	
}
