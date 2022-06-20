package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Serializer {

	public static void saveRun(Run run) {
		
		File runStorage = new File("storage");
		if(!runStorage.exists()) {
			runStorage.mkdir();
		}
		String id = run.getID();
		String name = Paths.get("storage",id+".txt").toString();
		
		try {
			FileOutputStream fos = new FileOutputStream(name);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(run);
			
			fos.close();
			oos.close();

		}catch(Exception e) {
			System.out.println(e);
			return;
		}
		
		System.out.println("Run successfully logged!");
	}

	public static LinkedList<Run> getRuns() {
		
		LinkedList<Run> runs = new LinkedList<Run>();
		File storage = new File(Paths.get("storage").toString());
		String[] list = storage.list();
		
		
		for (int i = 0; i < list.length; i++) {
			Run run = null;
			try {
				FileInputStream fis = new FileInputStream(Paths.get("storage",list[i]).toString());
				ObjectInputStream ois = new ObjectInputStream(fis);

				run = (Run)ois.readObject();
				
				runs.add(run);
				
				fis.close();
				ois.close();
			}
			catch(Exception e) {
				System.out.println(e);
				return null;
			}

			
		}
		
		return runs;

		
	}
	
	public static void removeAll() {
		File storage = new File(Paths.get("storage").toString());
		String[] list = storage.list();
		int num = list.length;
		for (String item : list) {
			File f = new File(Paths.get("storage",item).toString());
			f.delete();
		}
		if (num == 1) {
			System.out.print("Removed 1 run successfully.\n");
		}else if (num > 1){
			System.out.printf("Removed %d runs successfully.\n", num);
		}
	}
	
	public static void remove(Run r) {
		File storage = new File(Paths.get("storage").toString());
		String[] list = storage.list();
		
		
		for (int i = 0; i < list.length; i++) {
			Run run = null;
			
			try {
				FileInputStream fis = new FileInputStream(Paths.get("storage",list[i]).toString());
				ObjectInputStream ois = new ObjectInputStream(fis);

				run = (Run)ois.readObject();
				File runFile = new File(Paths.get("storage",list[i]).toString());
				
				
				fis.close();
				ois.close();
				
				if (run.getID().equals(r.getID())) {
					runFile.delete();
					System.out.println("Run successfully removed.");
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public static void saveSettings(Settings s) {
		try {
			FileOutputStream fos = new FileOutputStream("settings.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(s);
			
			fos.close();
			oos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Settings loadSettings() {
		Settings s = null;
		try {
			FileInputStream fis = new FileInputStream("settings.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			s = (Settings)ois.readObject();
			
			fis.close();
			ois.close();
			
		}catch(Exception e) {
			if (e instanceof FileNotFoundException) {
				s = new Settings(App.defaultUnit, App.defaultTMZ);
			}else {
				e.printStackTrace();
			}
		}
		return s;
	}
	
}
