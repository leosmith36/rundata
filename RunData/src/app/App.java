package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
	private Scanner sc = new Scanner(System.in);

	private String tmz;
	public static String defaultTMZ = "US/Mountain"; 
	private String[] tmzs = {"US/Pacific","US/Mountain","US/Central","US/Eastern"};
	
	private String unit;
	public static String defaultUnit = "mi";
	private String[] units = {"mi","km"};
	
//	private String mode = "TXT";
//	private String[] modes = {"TXT","SQL"};
	
	private ArrayList<String> optionList = new ArrayList<String>();
	private ArrayList<String[]> options = new ArrayList<String[]>();
	
	private LinkedList<Run> runs = new LinkedList<Run>();


	public void start(){
		boolean unknown;
		do{
			System.out.println("Please choose from one of the following options:");
			System.out.println("\t1: Add a new run");
			System.out.println("\t2: Edit your runs");
			System.out.println("\t3: View your runs");
			System.out.println("\t4: Configure settings");
			System.out.println("\t5: Exit application");
			unknown = false;
			int input;
			try {
				input = sc.nextInt();
			}catch(Exception e){
				input = 0;
			}finally {
				sc.nextLine();
			}
			switch (input) {
			case 1:
				addRun();
				break;
			case 2:
				editRuns();
				break;
			case 3:
				viewRuns(false);
				start();
				break;
			case 4:
				openSettings();
				break;
			case 5:
				Serializer.saveSettings(new Settings(unit, tmz));
				System.exit(0);
			default:
				System.out.println("Command not defined. Please try again.");
				unknown = true;
			}
		}while(unknown);
	}
	
	public void openSettings() {
		System.out.println("Settings:");
		System.out.printf("\t1: Change time zone (currently: %s)\n",tmz);
		System.out.printf("\t2: Change distance units (currently: %s)\n",unit);
//		System.out.printf("\t3: Change database mode (currently: %s)\n",mode);
		System.out.printf("\t3: Back\n");
		boolean unknown;
		do {
			unknown = false;
			int input;
			try {
				input = sc.nextInt();
				if (input == 3) {
					start();
					return;
				}
				if (input - 1 < optionList.size()) {
					changeSetting(input);
				}else {
					throw new Exception();
				}
				
			}catch(Exception e){
				input = 0;
				System.out.println("Command not defined. Please try again.");

				unknown = true;
			}finally {
				sc.nextLine();
			}
		}while(unknown);
	}
	
	public void changeSetting(int label) {
		int index = label - 1;
		String key = optionList.get(index);
		String[] optionValues = options.get(index);
		boolean unknown;
		do {
			int count = 1;
			System.out.printf("Options for %s:\n", key);
			for (String value : optionValues) {
				System.out.printf("\t%d: %s\n", count, value);
				count++;
			}
			System.out.printf("\t%d: Back\n", count);
			unknown = false;
			int input;
			try {
				input = sc.nextInt();
				if(input == count) {
					openSettings();
					return;
				}else {
					int newIndex = input - 1;
					switch(index) {
					case 0:
						tmz = optionValues[newIndex];
						break;
					case 1:
						unit = optionValues[newIndex];
						break;
//					case 2:
//						mode = optionValues[newIndex];
//						break;
					default:
						throw new Exception();
					}
					Serializer.saveSettings(new Settings(unit,tmz));
					openSettings();
					return;
				}
			}catch(Exception e) {
				input = 0;
				System.out.println("Command not defined. Please try again.");
				e.printStackTrace();
				unknown = true;
			}finally {
				sc.nextLine();
			}
			
		}while(unknown);
	}
	
	public void viewRuns(boolean ind) {
//		switch (mode) {
//		case "SQL":
//			Database.viewAll();
//			break;
//		default:
//			runs = Serializer.getRuns();
//		}
		runs = Serializer.getRuns();
		if (runs.size() == 0) {
			System.out.println("No runs are currently logged.");
			start();
		}
		

		boolean unknown;
		int input;
		String sort = "Date";
		do {
			unknown = false;
			try {
				System.out.println("Choose one of the following options for sorting your runs:");
				System.out.println("\t1: Date/Time");
				System.out.println("\t2: Location");
				System.out.println("\t3: Distance");
				System.out.println("\t4: Duration");
				System.out.println("\t5: Type");
				input = sc.nextInt();
				switch(input) {
				case 1:
					sort = "Date";
					break;
				case 2:
					sort = "Location";
					break;
				case 3:
					sort = "Distance";
					break;
				case 4:
					sort = "Duration";
					break;
				case 5:
					sort = "Type";
					break;
				default:
					throw new Exception();
				}
			}catch(Exception e) {
				System.out.println("Invalid option. Please try again.");
				unknown = true;
			}finally {
				sc.nextLine();
			}
		}while(unknown);
		
		
		do {
			unknown = false;
			try {
				System.out.println("How many runs do you want to view? (or enter 'all' to see all runs)");
				
				if (sc.hasNextInt()) {
					int num = sc.nextInt();
					if (num > 0) {
						printRuns(num, ind, sort);
					}else {
						throw new Exception();
					}
				}else if (sc.hasNext("all")) {
					sc.nextLine();
					printRuns(runs.size(), ind, sort);
				}else {
					throw new Exception();
				}
				
				
			}catch(Exception e) {
				System.out.println("Invalid integer. Please try again.");
				unknown = true;
				sc.nextLine();
			}
		}while (unknown);
		
		

	}
	
	public void addRun(){
		String date;
		boolean invalid;
		do {
			System.out.println("Enter a date in the format MM-DD-YYYY:");
			invalid = false;
			try {
				date = sc.next("[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]");
			} catch (Exception e) {
				date = null;
				System.out.println("Invalid date. Please try again.");
				invalid = true;
			}finally {
				sc.nextLine();
			}
		}while(invalid);
		
		String hms;
		String ampm;
		do {
			System.out.println("Enter a time in the format HH:MM AM:");
			invalid = false;
			try {
				hms = sc.next("[0-9]?[0-9]:[0-9][0-9]");
				ampm = sc.next("[APap][Mm]");

			} catch (Exception e) {
				hms = null;
				ampm = null;
				System.out.println("Invalid time. Please try again.");
				invalid = true;
			}finally {
				sc.nextLine();
			}
		}while(invalid);
		
		String time = hms + " " + ampm;

		
		System.out.println("Enter the location of your run:");
		String location = sc.nextLine();
		
		float dist;
		do {
			System.out.printf("Enter the distance you ran in %s:\n",unit);
			invalid = false;
			try {
				dist = sc.nextFloat();
			} catch (Exception e) {
				dist = 0.0f;
				System.out.println("Invalid distance. Please try again.");
				invalid = true;
			}finally {
				sc.nextLine();
			}
		}while(invalid);
		
		if(unit == "km") {
			dist = Converter.kilometersToMiles(dist);
		}
		
		String duration;
		do {
			System.out.println("Enter the amount of time you ran in HH:MM:SS:");
			invalid = false;
			try {
				duration = sc.next("[0-9]?[0-9]:[0-9][0-9]:[0-9][0-9]");
			} catch (Exception e) {
				duration = null;
				System.out.println("Invalid time. Please try again.");
				invalid = true;
			}finally {
				sc.nextLine();
			}
		}while(invalid);
		
		String type;
		List<String> types = Arrays.asList(Run.types);
		do {
			System.out.println("What category does your run fall into?");
			System.out.format("Options: %s\n", types.toString().replace("[","").replace("]",""));
			invalid = false;
			type = sc.nextLine();
			if(!types.contains(type)) {
				System.out.println("Invalid category. Please try again.");
				invalid = true;
			}
		}while(invalid);
		
		Run newRun = new Run(date, time, location, dist, duration, type, tmz);
//		switch (mode) {
//		case "SQL":
//			Database.insertRun(newRun);
//			break;
//		default:
//			Serializer.saveRun(newRun);
//		}
		Serializer.saveRun(newRun);
		start();
	}
	
	public void editRuns() {
//		printRuns(runs.size(), true, "Date");
		
//		if (runs.size() == 0) {
//			System.out.println("No runs are currently logged.");
//			start();
//		}
		viewRuns(true);

		boolean unknown;
		do {
			unknown = false;
			try {
				System.out.println("Enter the index of the run you want to delete (or enter 'all' to remove all runs).");
				if (sc.hasNextInt()) {
					int input = sc.nextInt();
					if (input - 1 < runs.size()) {
						Run r = runs.get(input - 1);
						Serializer.remove(r);
					}else {
						throw new Exception();
					}
				}else if (sc.hasNext("all")) {
					sc.nextLine();
					Serializer.removeAll();
				}else {
					throw new Exception();
				}
				
			}catch(Exception e) {
				System.out.println("Invalid index. Please try again.");
				unknown = true;
				sc.nextLine();
			}
			
		}while (unknown);
		
//		Serializer.removeAll();
		start();
	}
	
	public String getUnit() {
		return unit;
	}
	
	public String getTMZ() {
		return tmz;
	}
	
	public void printRuns(int num, boolean ind, String sort) {
		runs = Serializer.getRuns();
		for (Run r: runs) {
			r.setSort(sort);
		}

		Collections.sort(runs);
//		System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s\n", Run.keyList);
//		String realTMZ;
//		switch(tmz) {
//		case "PST":
//			realTMZ = "America/Los_Angeles";
//			break;
//		case "CST":
//			realTMZ = "America/Chicago";
//			break;
//		case "EST":
//			realTMZ = "America/New_York";
//			break;
//		default:
//			realTMZ = "America/Phoenix";
//		}
		int count = 0;
		for (Run r : runs) {
			if (count == num) {
				break;
			}
			boolean labels = false;
			if (count == 0) {
				labels = true;
			}
			r.printValues(labels,unit,tmz,ind,count + 1);
			count++;
		}
	}
	
	public App() {
		System.out.println("Welcome to the RunData running log!\n");
		
		Settings s = Serializer.loadSettings();
		tmz = s.getTMZ();
		unit = s.getUnit();

		optionList.add("Time Zone");
		optionList.add("Distance Unit");
//		optionList.add("Database Mode");
		
		options.add(tmzs);
		options.add(units);
//		options.add(modes);
		
		start();
	}
	
	public static void main(String[] args) {
		new App();
		
	}
}
