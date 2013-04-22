package com.example.norserun;

import java.util.List;

import android.location.Location;

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
	
	

}
