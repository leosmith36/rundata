package app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Run implements Serializable, Comparable<Run> {

	private static final long serialVersionUID = 1L;
	public static String[] types = {"Easy","Long","Tempo","Interval"};
	private float dist;
	private String location;
	private String type;
	private String date;
	private String time;
	private String duration;
	public static String[] keyList = {"Date","Time","Location","Distance","Duration","Type"};
	private HashMap<String,String> storedValues = new HashMap<String,String>();
	private String id;
	private String tmz;
	private String sortBy = "Date";
	
	
	public Run(String date, String time, String location, float dist, String duration, String type, String tmz) {
		this.dist = dist;
		this.location = location;
		this.type = type;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.tmz = tmz;

		
		id = UUID.randomUUID().toString();
		
		storedValues.put("Date",date);
		storedValues.put("Time",time);
		storedValues.put("Location",location);
		storedValues.put("Distance",String.valueOf(dist));
		storedValues.put("Duration",duration);
		storedValues.put("Type",type);
		
	}
	
	public void printValues(boolean labels, String unit, String newTMZ, boolean ind, int iter) {
		String niceDate = Converter.formatDate(this, newTMZ);
		String niceTime = Converter.formatTime(this, newTMZ);
		String niceDuration = Converter.formatDuration(this);
		float newDist;

		if(unit.equals("km")) {

			newDist = Converter.milesToKilometers(dist);
		}else {
			newDist = dist;
		}

		if (ind) {
			if (labels) {
				System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s", 
						"Index", "Date","Time","Location","Distance (" + unit + ")","Duration","Type");
				System.out.println();
				System.out.printf("%-15d%-15s%-15s%-15s%-15.2f%-15s%-15s", 
						iter, niceDate,niceTime,location,newDist,niceDuration,type);
			}else {
				System.out.printf("%-15d%-15s%-15s%-15s%-15.2f%-15s%-15s", 
						iter,niceDate,niceTime,location,newDist,niceDuration,type);
			}
		}else {
			if (labels) {
				System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s", 
						"Date","Time","Location","Distance (" + unit + ")","Duration","Type");
				System.out.println();
				System.out.printf("%-15s%-15s%-15s%-15.2f%-15s%-15s", 
						niceDate,niceTime,location,newDist,niceDuration,type);
			}else {
				System.out.printf("%-15s%-15s%-15s%-15.2f%-15s%-15s", 
						niceDate,niceTime,location,newDist,niceDuration,type);
			}
		}
		
		
		
		
		
		
		System.out.println();
	}
	
	public String[] getList() {
		String[] list = {date, time, location, String.valueOf(dist), duration, type};
		return list;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getLocation() {
		return location;
	}
	
	public float getDistance() {
		return dist;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public String getType() {
		return type;
	}
	
	public HashMap<String,String> getValues(){
		return storedValues;
	}
	
	public String getID() {
		return id;
	}

	@Override
	public int compareTo(Run o) {
		if (sortBy == "Date") {
			return Converter.getDate(o).compareTo(Converter.getDate(this));
		}else if (sortBy == "Distance") {
			return Float.valueOf(o.getDistance()).compareTo(Float.valueOf(dist));
		}else if (sortBy == "Location") {
			return location.compareTo(o.getLocation());
		}else if (sortBy == "Type") {
			return type.compareTo(o.getType());
		}else if (sortBy == "Duration") {
			return Converter.getDuration(o).compareTo(Converter.getDuration(this));
		}else {
			return Converter.getDate(o).compareTo(Converter.getDate(this));
		}
		
	}
	
	public String getTMZ() {
		return tmz;
	}
	
	public void setSort(String sort) {
		sortBy = sort;
	}
}
