package com.example.norserun;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Log;

public class StatisticsHelper {

	
	public static double GetDistance(List<Location> loclist){
		double totalDistance = 0;
		Location lastLoc = null;
		
		if(loclist.isEmpty()) return 0;
		
		for(Location loc : loclist){
			if(!(lastLoc == null)){
				totalDistance += Math.sqrt(Math.pow((loc.getLatitude() - lastLoc.getLatitude()),2)	+	Math.pow((loc.getLongitude() - lastLoc.getLongitude()),2));
			}
		}
		
		return totalDistance;
		
	}
	
	public static double GetAverageSpeed(List<Location> loclist){
		if(loclist.isEmpty()) return 0;
		
		Location firstLoc = loclist.get(0);
		Location lastLoc = loclist.get(loclist.size()-1);
		
		double time = (lastLoc.getTime() - firstLoc.getTime())/1000;
		
		double dist = GetDistance(loclist);
		
		return dist/time;
		
		
	}
	
	public static List<Posisjon> StringDeserializer(String lat, String lon, String time){
		// TO-DO: Lage en deserializer av de 3 stringsa 
		String latSub;
		String lonSub;
		String timeSub;
		Posisjon dummyPosisjon;
		List<Posisjon> returnList = new ArrayList<Posisjon>();
		
		if(lat.length() < 1) return null;
		Log.d("TestString", "kom inn i funksjonen...");
		while(lat.contains(" ") && lat.contains(".")){
			
			
			
			
//			latSub = lat.substring(0, lat.indexOf(" "));
//			lat = lat.substring(lat.indexOf(" ") + 1);
////			Log.d("TestString", "Parsa lat..." + latSub);
//			lonSub = lon.substring(0, lon.indexOf(" "));
//			lon = lon.substring(lon.indexOf(" ") + 1);
////			Log.d("TestString", "Parsa lon..." + lonSub);
//			timeSub = time.substring(0, time.indexOf(" "));
//			time = time.substring(time.indexOf(" ") + 1);
			

//			Log.d("TestString", "Parsa Time..."+ timeSub);
			dummyPosisjon = new Posisjon(latSub, lonSub, timeSub);
			Log.d("TestString", dummyPosisjon.toString());
			returnList.add(dummyPosisjon);
		}
		
		
		
		return returnList;
	}
	
	

}
