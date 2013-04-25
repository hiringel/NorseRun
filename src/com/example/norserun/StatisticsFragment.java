package com.example.norserun;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class StatisticsFragment extends SherlockFragment implements OnClickListener{
	
	private static final String SQL_TAG = "SQL_TAG";
	RemindersDbAdapter mDbHelper;
	String nameFromDb;
	String latFromDb;
	String longtFromDb;
	String timeFromDb;
	TextView speedText;
	TextView distanceText;
	TextView titleText;
	TextView distTextStatic;
	TextView speedTextStatic;
	TextView timeText;
	TextView timeTextStatic;
	TextView maxSpeed;
	TextView maxSpeedStatic;
	Button drawButton;
	Button deleteButton;
	TextView text;
	List<Posisjon> liste;
	public static List<Posisjon> sendList;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from statfrag");
		View statisticsFragment = inflater.inflate(R.layout.statistics_layout, group, false);
		statisticsFragment.findViewById(R.id.DrawRouteButton).setOnClickListener(this);
		statisticsFragment.findViewById(R.id.DeleteButton).setOnClickListener(this);
		liste = new ArrayList<Posisjon>();
		
		return statisticsFragment;

		
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mDbHelper = new RemindersDbAdapter(this.getSherlockActivity());
        mDbHelper.open();
        
        speedText = (TextView)getView().findViewById(R.id.StatisticsSpeedInput);
        titleText = (TextView)getView().findViewById(R.id.StatisticsTitle);
        distanceText = (TextView)getView().findViewById(R.id.StatisticsDistanceInput);
        distTextStatic = (TextView)getView().findViewById(R.id.StatisticsDistance);
        speedTextStatic = (TextView)getView().findViewById(R.id.StatisticsSpeed);
        timeTextStatic = (TextView)getView().findViewById(R.id.StatisticsTime); 
        timeText = (TextView)getView().findViewById(R.id.StatisticsTimeInput);	
        maxSpeed = (TextView)getView().findViewById(R.id.StatisticsMaxSpeedInput);	
        maxSpeedStatic = (TextView)getView().findViewById(R.id.StatisticsMaxSpeedStatic);	
        
        drawButton = (Button)getView().findViewById(R.id.DrawRouteButton);
        deleteButton = (Button)getView().findViewById(R.id.DeleteButton);
        
        drawButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        speedText.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);
        distanceText.setVisibility(View.VISIBLE);
        distTextStatic.setVisibility(View.VISIBLE);
        speedTextStatic.setVisibility(View.VISIBLE);
        timeText.setVisibility(View.VISIBLE);
        timeTextStatic.setVisibility(View.VISIBLE);
        maxSpeed.setVisibility(View.VISIBLE);
        maxSpeedStatic.setVisibility(View.VISIBLE);
		
		if(MainActivity.tripChosenInt == -1){
			this.noRemindersShow();
		
        
		}
		
		else{
			try {
			Log.d(SQL_TAG, "tripChosenInt = " + String.valueOf(MainActivity.tripChosenInt));
			
			Log.d(SQL_TAG, "(long)MainActivity.tripChosenInt = " +(long)MainActivity.tripChosenInt);
			
			Cursor reminder = mDbHelper.fetchReminder((long)MainActivity.tripChosenInt);
			
			 getSherlockActivity().startManagingCursor(reminder);
			Log.d(SQL_TAG, "reminder = " +reminder.toString());
			nameFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TITLE));
			latFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_LAT));
			longtFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_LONG));
			timeFromDb = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TIME));
			Log.d(SQL_TAG, "Strings: " + nameFromDb + latFromDb + longtFromDb + timeFromDb);
			DecimalFormat format = new DecimalFormat("00.00");
			liste = StatisticsHelper.StringDeserializer(latFromDb, longtFromDb, timeFromDb);
			speedText.setText(String.valueOf(format.format(StatisticsHelper.GetAverageSpeed(liste)))+" Km/timen");
			distanceText.setText(String.valueOf(format.format(StatisticsHelper.GetDistance(liste)))+" meter");
			timeText.setText(StatisticsHelper.TimeDifference(liste));
			maxSpeed.setText(String.valueOf(format.format(StatisticsHelper.GetMaxSpeed(liste)))+" Km/timen");
			titleText.setText(nameFromDb);
//			Log.d("POSISJON", String.valueOf(StatisticsHelper.GetAverageSpeed(liste)));
//			Log.d("POSISJON", String.valueOf(StatisticsHelper.GetDistance(liste)));
			}
			catch (Exception e){
				Log.d(SQL_TAG, "Exception: "+e.toString()+ "       StackTrace = "+e.getStackTrace()+"   nameFromDb = "+nameFromDb);
			}
			
		}
		
//		private void fillData() {
//	        Cursor remindersCursor = mDbHelper.fetchAllReminders();
//	        getSherlockActivity().startManagingCursor(remindersCursor);
//	        
//	        // Create an array to specify the fields we want to display in the list (only TITLE)
//	        String[] from = new String[]{RemindersDbAdapter.KEY_TITLE};
//	        
//	        // and an array of the fields we want to bind those fields to (in this case just text1)
//	        int[] to = new int[]{R.id.text1};
//	        
//	        // Now create a simple cursor adapter and set it to display
//	        SimpleCursorAdapter reminders = 
//	        	    new SimpleCursorAdapter(getSherlockActivity(), R.layout.reminder_row, remindersCursor, from, to);
//	        setListAdapter(reminders);
//	    }
		
		/*	// Only populate the text boxes and change the calendar date
    	// if the row is not null from the database. 
        if (mRowId != null) {
            Cursor reminder = mDbHelper.fetchReminder(mRowId);
            startManagingCursor(reminder);
            mTitleText.setText(reminder.getString(
    	            reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TITLE)));
            mBodyText.setText(reminder.getString(
                    reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_BODY)));
            

            // Get the date from the database and format it for our use. 
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = null;
			try {
				String dateString = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_DATE_TIME)); 
				date = dateTimeFormat.parse(dateString);
	            mCalendar.setTime(date); 
			} catch (ParseException e) {
				Log.e("ReminderEditActivity", e.getMessage(), e); 
			} 
        } else {
        	// This is a new task - add defaults from preferences if set. 
        	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); 
        	String defaultTitleKey = getString(R.string.pref_task_title_key); 
        	String defaultTimeKey = getString(R.string.pref_default_time_from_now_key); 
        	
        	String defaultTitle = prefs.getString(defaultTitleKey, null);
        	String defaultTime = prefs.getString(defaultTimeKey, null); 
        	
        	if(defaultTitle != null)
        		mTitleText.setText(defaultTitle); 
        	
        	if(defaultTime != null)
        		mCalendar.add(Calendar.MINUTE, Integer.parseInt(defaultTime));
        	
        }
        
        updateDateButtonText(); 
        updateTimeButtonText(); */
	}
	
	private void noRemindersShow(){
		text = (TextView) getSherlockActivity().findViewById(R.id.StatisticsText);
		text.setText("Ingen tur valgt!");
        speedText.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);
        distanceText.setVisibility(View.GONE);
        distTextStatic.setVisibility(View.GONE);
        speedTextStatic.setVisibility(View.GONE);
        timeText.setVisibility(View.GONE);
        timeTextStatic.setVisibility(View.GONE);
        drawButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        maxSpeed.setVisibility(View.GONE);
        maxSpeedStatic.setVisibility(View.GONE);
	}
	
	public void onStop(){
		super.onStop();
	}
	
	public void onDestroy(){
		super.onDestroy();
		mDbHelper.close();
	}


	@Override
	public void onClick(View v) {
		Log.d(SQL_TAG, "Something was clicked..");
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.DeleteButton:{
			Log.d(SQL_TAG, "Deleting..");
			this.noRemindersShow();
			mDbHelper.deleteReminder(MainActivity.tripChosenInt);
			MainActivity.tripChosenInt = -1;
			//TO-DO: Delete
			break;
		}
			
		case R.id.DrawRouteButton:{
			Log.d(SQL_TAG, "Draw was clicked..");
			Intent goToDrawMap = new Intent(this.getView().getContext(), KartDrawActivity.class);
			startActivity(goToDrawMap);
			//TO-DO Create new activity and draw
			break;
		}


		}
	}
	
	
	
}
