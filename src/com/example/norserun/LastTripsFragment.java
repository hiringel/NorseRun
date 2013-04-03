package com.example.norserun;




import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class LastTripsFragment extends SherlockListFragment{
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle){
		Log.d("FromFrag", "Hi from tripfrag");

		
		return inflater.inflate(R.layout.lasttrips_layout, group, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		Log.d("FromFrag", "onviewcreated");

		//Finner listviewen jeg laget i xml
		lastTripsList = (ListView)getSherlockActivity().findViewById(R.id.mainListView);
		
		//Setter adapter til å laste fra trips, hvor layout på item er definert i simplerow
		lastTripsList.setAdapter(new ArrayAdapter<String>(getSherlockActivity(), R.layout.simplerow, trips));
		
		//Legg til en onclicklistener på lastTripsLista
		lastTripsList.setOnItemClickListener(new ListClickHandler());
	}
	
	
	
	
	/* On click listener for lista definert i egen private class
	 * Hvis jeg gjorde d på standardmåten ble d megastygt
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	
	private class ListClickHandler implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> Adapter, View view, int positionClicked,
				long arg3) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getSherlockActivity().getApplicationContext(), "Position clicked: "+ positionClicked, Toast.LENGTH_SHORT).show();
			
		}
		
	}
	
	
	
	@Override
	public void onStart(){
		super.onStart();
		
	}
	
}
