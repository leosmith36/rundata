package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
	 private static String url = "jdbc:mysql://LAPTOP-6AF779C2:3306/RunData";
     private static String user = "leosmith";
     private static String password = "coding";
     
     public static DateFormat timeFormat1 = new SimpleDateFormat("hh:mm a");
     public static DateFormat timeFormat2 = new SimpleDateFormat("kk:mm");

     public static void insertRun(Run run) {
    	 
    	 try{
    		 Connection con = DriverManager.getConnection(url, user, password);
    		 Statement st = con.createStatement();
    		 
    		 String date = run.getDate();
    		 String[] dateParts = date.split("-");
    		 String newDate = String.format("%s-%s-%s", dateParts[2], dateParts[0], dateParts[1]);
    		 
        	 String time = run.getTime();
        	 Date fTime = timeFormat1.parse(time);
        	 String newTime = timeFormat2.format(fTime);
        	 
        	 String location = run.getLocation();
        	 
        	 float distance = run.getDistance();
        	 
        	 String duration = run.getDuration();

        	 String type = run.getType();
        	 String query = String.format("INSERT INTO running_log (Run_date, Run_time, Run_location, Run_distance, Run_duration, Run_type) "
        	 		+ "VALUES ('%s','%s','%s',%.2f,'%s','%s');",newDate,newTime,location,distance,duration,type);
    		 
    		 int x = st.executeUpdate(query);
    		 
    		 if(x > 0) {
    			 System.out.println("Your run was successfully logged!");
    		 }else {
    			 System.out.println("Due to an error, your run could not be logged.");
    		 }
    		 con.close();
    	 }catch (Exception e){
    		 System.out.println("Database connection failed.");
    		 System.out.println(e);
    	 }
    	 
     }
     
     public static void viewAll() {
    	 
    	 try{
    		 Connection con = DriverManager.getConnection(url, user, password);
    		 Statement st = con.createStatement();
        	 String query = "SELECT * FROM running_log";
    		 
    		 ResultSet rs = st.executeQuery(query);
    		 ResultSetMetaData rd = rs.getMetaData();
    		 int colNumber = rd.getColumnCount();
    		 boolean heading = false;
    		 while(rs.next()) {
    			 if (!heading) {
    				 for(int i = 0; i < Run.keyList.length; i++) {
    					 System.out.print(Run.keyList[i] + " ");
    				 }
				 }
    			 System.out.println();
    			 heading = true;
    			 for(int i = 2; i <= colNumber; i++) {
    				 System.out.print(rs.getString(i) + " ");
    			 }
    			 
    			 System.out.println();
    		 }
    		 con.close();
    	 }catch (Exception e){
    		 System.out.println("Database connection failed.");
    		 System.out.println(e);
    	 }
    	 
     }
}
