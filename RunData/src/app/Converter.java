package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Converter {
	public static SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	
	public static SimpleDateFormat dformat = new SimpleDateFormat("hh:mm:ss");
	
	public static SimpleDateFormat niceDate = new SimpleDateFormat("MM-dd-yyyy");
	public static SimpleDateFormat niceTime = new SimpleDateFormat("hh:mm a");
	public static SimpleDateFormat niceDuration = new SimpleDateFormat("hh:mm:ss");
	
	

	public static Date getDate(Run r) {
		try {
			String date = r.getDate();
			String time = r.getTime();
			String tmz = r.getTMZ();
			String datetime = date + " " + time;
			SimpleDateFormat newFormat = (SimpleDateFormat) format.clone();
			TimeZone tz = TimeZone.getTimeZone(tmz);
			newFormat.setTimeZone(tz);
			Date realDate = newFormat.parse(datetime);
			return realDate;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	public static Long getDuration(Run r) {
		Long newDuration = null;
		try {
			String duration = formatDuration(r);
			Date goodDuration = niceDuration.parse(duration);
			newDuration = goodDuration.getTime();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newDuration;
	}
	
	public static Calendar getCalendar(Run r) {
		Date date = getDate(r);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	
	public static String formatDate(Run r, String tmz) {
		

		TimeZone tz = TimeZone.getTimeZone(tmz);
		SimpleDateFormat newFormat = (SimpleDateFormat) niceDate.clone();
		newFormat.setTimeZone(tz);
		String newDate = niceDate.format(getDate(r));
		return newDate;
	}
	
	public static String formatTime(Run r, String tmz) {

		TimeZone tz = TimeZone.getTimeZone(tmz);

		SimpleDateFormat newFormat = (SimpleDateFormat) niceTime.clone();

		newFormat.setTimeZone(tz);
		String newTime = newFormat.format(getDate(r));
		return newTime;
	}

	public static String formatDuration(Run r) {
		
		String duration = r.getDuration();
		Date d;
		try {
			d = dformat.parse(duration);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		String newDuration = niceDuration.format(d);
		return newDuration;
	}
	
	
	public static float milesToKilometers(float miles) {
		return (miles*1.60934f);
	}
	
	public static float kilometersToMiles(float kilometers) {
		return (kilometers/1.60934f);
	}
}
