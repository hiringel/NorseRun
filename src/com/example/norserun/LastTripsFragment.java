package com.example.norserun;




import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class LastTripsFragment extends SherlockListFragment{
	
	ListView lol;
	static String trips[] = new String[]{
			"NTNU",
			"Dragvoll",
			"Gløs",
			"Singsaker"
	};
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		


		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from tripfrag");

		
		return inflater.inflate(R.layout.lasttrips_layout, group, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		Log.d("FromFrag", "onviewcreated");
		//super.onViewCreated(view, savedInstanceState);
		lol = (ListView)getSherlockActivity().findViewById(R.id.mainListView);
		lol.setAdapter(new ArrayAdapter<String>(getSherlockActivity(), R.layout.abs__list_menu_item_radio, trips));
		//setListAdapter(new ArrayAdapter<String>(getSherlockActivity(), R.layout.abs__list_menu_item_checkbox, trips));
	}
	
	
	@Override
	public void onStart(){
		super.onStart();
		
	}
	
}
