package com.example.norserun;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class HomeFragment extends SherlockFragment{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from homefrag");
		return inflater.inflate(R.layout.home_layout, group, false);
	}
	
	public void onResume(){
		super.onResume();
	}
	
	public void onStop(){
		super.onStop();
	}
	
}
