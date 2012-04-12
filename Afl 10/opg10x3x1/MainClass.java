package opg10x3x1;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainClass {
	
	public static void main(String[] args) {
		
		// Save some names
		saveName("Phillip");
		saveName("Lauge");
		
		// Load some names
		System.out.println("Display all names in DB:");
		ArrayList<String> names = loadAllNames();
		for ( String s : names )
			System.out.println(s);
		
	}
	
	public static void saveName(String name) {
		try {
			
			DbDAO.saveName(name);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to save name");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Failed to save name");
			System.exit(1);
		}
	}
	
	public static ArrayList<String> loadAllNames() {
		ArrayList<String> al = null;
		
		try {
			
			al = DbDAO.loadAllNames();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Failed to load name");
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to load name");
			System.exit(1);
		}
		
		return al;
		
	}
}
