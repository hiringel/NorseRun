package com.example.norserun;

import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener{

	//Instansiere Button widget
	Button kartButton;
	HomeFragment homeFrag;
	StatisticsFragment statFrag;
	LastTripsFragment tripFrag;
	public static int tripChosenInt;

	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tripChosenInt = -1;
		
		//Lager tabsa
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		initializeActionBarTabs();
		homeFrag = null;
		statFrag = null;
		tripFrag = null;

		
		
		
		
		//Button-ting, lage button og lage listener
		//addListenerOnButton();
	}
	




	//Dette er for menu-knappen
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getSupportMenuInflater().inflate(R.menu.activity_item, menu);
		
		menu.add("Overflow")
		.setIcon(R.drawable.ic_action_overflow).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


		
		return true;
	}
	
	/* initializeAcitonBarTabs() 
	 * Lager bare 3 tabs rett under
	 * actionbaren i applikasjonen,
	 * og setter listeners på de slik
	 * at hvis de klikker kan ting skje
	 */
	private void initializeActionBarTabs() {
		
		ActionBar.Tab homeTab = getSupportActionBar().newTab();
		ActionBar.Tab lastTripsTab = getSupportActionBar().newTab();
		ActionBar.Tab statisticsTab = getSupportActionBar().newTab();
		homeTab.setText(this.getString(R.string.homeTabString));
		lastTripsTab.setText(this.getString(R.string.lastTripsTabString));
		statisticsTab.setText(this.getString(R.string.statisticTabString));
		homeTab.setTag(1);
		lastTripsTab.setTag(2);
		statisticsTab.setTag(3);
		homeTab.setTabListener(this);
		lastTripsTab.setTabListener(this);
		statisticsTab.setTabListener(this);
		getSupportActionBar().addTab(homeTab);
		getSupportActionBar().addTab(lastTripsTab);
		getSupportActionBar().addTab(statisticsTab);	
	}
	



		
	


	
	
//	public void addListenerOnButton() {
//		 
//		final Context context = this;
// 
//		kartButton = (Button) findViewById(R.id.kartButton);
// 
//		kartButton.setOnClickListener(new OnClickListener() {
// 
//			public void onClick(View arg0) {
// 
//			    Intent intent = new Intent(context, KartActivity.class);
//                            startActivity(intent);   
// 
//			}
// 
//		});
//	}
	
	
	











@Override
public void onTabSelected(Tab tab, FragmentTransaction ft) {
	
	switch(Integer.parseInt(tab.getTag().toString())){
		case 1: 
			if(homeFrag == null){
				homeFrag = new HomeFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, homeFrag).commit();
				Log.d("Fragment", "Added home!");
			}
			else
			{
			getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, homeFrag).commit();
			Log.d("Fragment", "Replaced to home");
			}
			
			break;
		case 2:
			if(tripFrag == null){
				tripFrag = new LastTripsFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, tripFrag).commit();
				Log.d("Fragment", "Added trip");
			}
			else{
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, tripFrag).commit();
				Log.d("Fragment", "Replaced to trip");
			}

			break;
		case 3:
			if(statFrag == null){
				statFrag = new StatisticsFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, statFrag).commit();
				Log.d("Fragment", "Added stat");
			}
			else{
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, statFrag).commit();
				Log.d("Fragment", "Replaced to stat");
			}
	
			break;
		default:
			break;
			

	}
	

}

@Override
public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	
	// TODO Auto-generated method stub
	
}

@Override
public void onTabReselected(Tab tab, FragmentTransaction ft) {
	// TODO Auto-generated method stub
	
}
}
