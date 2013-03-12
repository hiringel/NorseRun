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
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends SherlockFragmentActivity {

	//Instansiere Button widget
	Button kartButton;
	TabHost myTabHost;
	TabManager myTabManager;
	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Lager tabsa
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		InitializeTabs(savedInstanceState);
		//initializeActionBarTabs();

		
		
		
		
		//Button-ting, lage button og lage listener
		//addListenerOnButton();
	}
	
	public void InitializeTabs(Bundle savedInstanceState){
		myTabHost = (TabHost)findViewById(android.R.id.tabhost);
		myTabHost.setup();
		
		myTabManager = new TabManager(this, myTabHost, R.id.realtabcontent);
		
		myTabManager.addTab(myTabHost.newTabSpec("Home").setIndicator("Home"),
				HomeFragment.class, null);
		
		myTabManager.addTab(myTabHost.newTabSpec("Last trips").setIndicator("Last trips"),
				HomeFragment.class, null);
		
		myTabManager.addTab(myTabHost.newTabSpec("Statistics").setIndicator("Statistics"),
				StatisticsFragment.class, null);
		
		if(savedInstanceState != null){
			//Last inn sist brukte tab
			myTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Lagre sist brukte tab
    	super.onSaveInstanceState(outState);
        outState.putString("tab", myTabHost.getCurrentTabTag());
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
//	private void initializeActionBarTabs() {
//		
//		ActionBar.Tab homeTab = getSupportActionBar().newTab();
//		ActionBar.Tab lastTripsTab = getSupportActionBar().newTab();
//		ActionBar.Tab statisticsTab = getSupportActionBar().newTab();
//		homeTab.setText(this.getString(R.string.homeTabString));
//		lastTripsTab.setText(this.getString(R.string.lastTripsTabString));
//		statisticsTab.setText(this.getString(R.string.statisticTabString));
//		homeTab.setTag(1);
//		lastTripsTab.setTag(2);
//		statisticsTab.setTag(3);
//		homeTab.setTabListener(this);
//		lastTripsTab.setTabListener(this);
//		statisticsTab.setTabListener(this);
//		getSupportActionBar().addTab(homeTab);
//		getSupportActionBar().addTab(lastTripsTab);
//		getSupportActionBar().addTab(statisticsTab);	
//	}
	



		
	


	
	
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
	
	
	
	public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity,
                                newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }
}
