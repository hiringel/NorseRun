package com.example.norserun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.actionbarsherlock.app.SherlockFragment;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class StatisticsFragment extends SherlockFragment{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from statfrag");
		return inflater.inflate(R.layout.statistics_layout, group, false);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if(MainActivity.tripChosenInt == -1){
		TextView text = (TextView) getSherlockActivity().findViewById(R.id.StatisticsText);
		text.setText("No trip chosen");
		}
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
	
}
