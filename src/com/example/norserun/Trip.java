package com.example.norserun;

import java.util.List;
import android.location.*;
import android.text.format.*;
import java.util.ArrayList;

public class Trip {
	
	private String name;
	private String tripType;
	private Time date; //<-- Neppe string, men Dateobjekt, se om det finnes noen greie ferdiglagde som kan brukes, ala jodatime.
	private double distance;
	
	private List<Location> geoPointArray; //<-- Kan være du kan bytte ut List med noe mer brukende!
	
	
	
	//Ting som er avhengige av en timer
	private double avgSpeed;
	private int duration; //<-- Denne kan bruke jodatime f eks hvis ikke android har en bedre timerfunksjon innebygget.
	
	public Trip(String name, String tripType){
		this.name = name;
		this.tripType = tripType;
		date = new Time();
		date.setToNow();
		this.distance = 0;
		this.avgSpeed = 0;
		this.duration = 0;
		
		this.geoPointArray = new ArrayList<Location>();
	}
	
	public void AddPoint(Location loc){
		this.geoPointArray.add(loc);
	}
	
	public Location GetLastPoint(){
		return this.geoPointArray.get(this.geoPointArray.size());
	}
	
	public boolean isListEmpty(){
		return this.geoPointArray.isEmpty();
	}
	
	
	
	

}
