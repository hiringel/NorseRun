package com.example.norserun;

import android.util.Log;

public class Posisjon {
	double Latitude;
	double Longitude;
	long Time;
	
	private static final String TAG = "Posisjon";
	
	public Posisjon(String lat, String lon, String time){
		this.Latitude = Double.parseDouble(lat);
		this.Longitude = Double.parseDouble(lon);
		this.Time = Long.parseLong(time);
	}
	
	public double getLatitude(){
		return this.Latitude;
	}
	
	public double getLongitude(){
		return this.Longitude;
	}
	
	public long getTime(){
		return this.Time;
	}
	
	public void setLatitude(String input){
		try{
		this.Latitude = Double.parseDouble(input);
		}catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, e.toString());
		}
	}
	
	public void setLongitude(String input){
		try{
		this.Longitude = Double.parseDouble(input);
		}catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, e.toString());
		}
	}
	
	public void setTime(String input){
		try{
		this.Time = Long.parseLong(input);
		}catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, e.toString());
		}
	}
	@Override
	public String toString(){
		return ("Lat: "+this.Latitude+" Lon: "+this.Longitude+" Time: "+this.Time);
	}
	

}
