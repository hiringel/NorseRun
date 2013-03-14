package com.example.norserun;
import android.location.*;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.List;

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
		
		//Setdate med en funskjon
		
		this.distance = 0;
		this.avgSpeed = 0;
		this.duration = 0;
		
		this.geoPointArray = new ArrayList<Location>();
	}
	
	
	
	

}
