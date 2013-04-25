package com.example.norserun;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Log;

public class StatisticsHelper {

	
	public static double GetDistance(List<Posisjon> loclist){
		double totalDistance = 0;
		Location A = new Location("start");
		Location B = new Location("end");
		
		
		if(loclist.isEmpty()) return -1;
		Posisjon lastLoc = loclist.get(0);
		for(Posisjon loc : loclist){
			if(!(lastLoc == null)){
				A.setLatitude(lastLoc.getLatitude());
				A.setLongitude(lastLoc.getLongitude());
				B.setLatitude(loc.getLatitude());
				B.setLongitude(loc.getLongitude());
				totalDistance += A.distanceTo(B);
				lastLoc = loc;
			}
		}
		
		return totalDistance;
		
	}
	
	public static double GetMaxSpeed(List<Posisjon> loclist){
		double maxSpeed = 0;
		double tempSpeed = 0;
		Location A = new Location("start");
		Location B = new Location("end");
		
		
		if(loclist.isEmpty()) return -1;
		Posisjon lastLoc = loclist.get(0);
		for(Posisjon loc : loclist){
			if(!(lastLoc == null)){
				A.setLatitude(lastLoc.getLatitude());
				A.setLongitude(lastLoc.getLongitude());
				A.setTime(lastLoc.getTime());
				B.setLatitude(loc.getLatitude());
				B.setLongitude(loc.getLongitude());
				B.setTime(loc.getTime());
				tempSpeed = A.distanceTo(B)/((B.getTime()-A.getTime())/1000);
				if(tempSpeed > maxSpeed) maxSpeed = tempSpeed;
				lastLoc = loc;
			}
		}
		
		return (maxSpeed*3.6);
		
	}
	
	public static double GetAverageSpeed(List<Posisjon> loclist){
		if(loclist.isEmpty()) return -1;
		
		Posisjon firstLoc = loclist.get(0);
		Posisjon lastLoc = loclist.get(loclist.size()-1);
		
		double time = (lastLoc.getTime() - firstLoc.getTime())/1000;
		
		double dist = GetDistance(loclist);
		
		return 3.6*dist/time;
		
		
	}
	
	public static List<Posisjon> StringDeserializer(String lat, String lon, String time){
		// TO-DO: Lage en deserializer av de 3 stringsa 
		Posisjon dummyPosisjon;
		List<Posisjon> returnList = new ArrayList<Posisjon>();
		
		String[] latSeparatedList = lat.split(" ");
		String[] lonSeparatedList = lon.split(" ");
		String[] timeSeparatedList = time.split(" ");
		
		if(lat.length() < 1) return null;
		Log.d("TestString", "kom inn i funksjonen...");

		int i = 0;
		for(String latSeperated : latSeparatedList){
			dummyPosisjon = new Posisjon(latSeperated, lonSeparatedList[i], timeSeparatedList[i]);
			returnList.add(dummyPosisjon);
			i++;
			Log.d("POSISJON", dummyPosisjon.toString());
			if(i==latSeparatedList.length) break;
		}
		
		
		
		return returnList;
	}
	
	public static String TimeDifference(List<Posisjon> loclist){
		long returnValue = 0;
		int minutes = 0;
		int seconds = 0;
		if(loclist.isEmpty()) return "0.0";
		
		
		else{
			returnValue = (loclist.get(loclist.size()-1).getTime()/1000 - loclist.get(0).getTime()/1000); 
			minutes = (int) Math.floor(returnValue/60);
			seconds =(int) (returnValue - minutes*60);
			return (minutes+" minutter og "+seconds+" sekunder");
		}
		
	}
	
	

}
