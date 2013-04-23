package com.example.norserun;




import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;



public class LastTripsFragment extends SherlockListFragment{
	
	 private static final int ACTIVITY_CREATE=0;
	 private static final int ACTIVITY_EDIT=1;
	
	 private RemindersDbAdapter mDbHelper;
	    
	 
	 
	ListView lastTripsList;
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
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mDbHelper = new RemindersDbAdapter(this.getSherlockActivity());
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from tripfrag");

		View lastTripFragment = inflater.inflate(R.layout.lasttrips_layout, group, false);
		return inflater.inflate(R.layout.lasttrips_layout, group, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		Log.d("FromFrag", "onviewcreated");
/*
		//Finner listviewen jeg laget i xml
		lastTripsList = (ListView)getSherlockActivity().findViewById(R.id.mainListView);
		
		//Setter adapter til å laste fra trips, hvor layout på item er definert i simplerow
		lastTripsList.setAdapter(new ArrayAdapter<String>(getSherlockActivity(), R.layout.simplerow, trips));
		
		//Legg til en onclicklistener på lastTripsLista
		lastTripsList.setOnItemClickListener(new ListClickHandler());
*/	
	}
	
	private void fillData() {
        Cursor remindersCursor = mDbHelper.fetchAllReminders();
        getSherlockActivity().startManagingCursor(remindersCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{RemindersDbAdapter.KEY_TITLE};
        
        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter reminders = 
        	    new SimpleCursorAdapter(getSherlockActivity(), R.layout.reminder_row, remindersCursor, from, to);
        setListAdapter(reminders);
    }
	
	
	
	/* On click listener for lista definert i egen private class
	 * Hvis jeg gjorde d på standardmåten ble d megastygt
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("LOCATION_SERVICE", "Pressed: "+id);
        MainActivity.tripChosenInt = (int)id;
//        Intent i = new Intent(this, ReminderEditActivit.class);
//        i.putExtra(RemindersDbAdapter.KEY_ROWID, id);
//        startActivityForResult(i, ACTIVITY_EDIT); 
    }
	
	
	
	@Override
	public void onStart(){
		super.onStart();
		
	}
	
}
