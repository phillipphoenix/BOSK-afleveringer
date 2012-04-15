package opg10x4x1;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) {

		try {
			// Create the table
			DbDAO.createTable();

			// Save 1000 names with nonprepared statements
			double nonPreparedSave;
			StopWatch sw = new StopWatch();
			for ( int i = 0; i < 1000; i++ )
				saveNameNonPrepared("person" + i);
			nonPreparedSave = sw.elapsedTime();

			// Reset the table
			dropTable();
			DbDAO.createTable();

			// Save 1000 names with prepared statements
			double preparedSave;
			ArrayList<String> names = new ArrayList<String>();
			for ( int i = 0; i < 1000; i++ )
				names.add("person" + i);
			sw = new StopWatch();
			saveNamePrepared(names);
			preparedSave = sw.elapsedTime();

			// Print af test af 1000 saves
			System.out.println("1000 names saved! \nNonprepared: " + nonPreparedSave + "\nPrepared: " + preparedSave);

			// Load 1000 names nonprepared ( We use that the table consists of the 1000 names from the arraylist names)
			double nonPreparedLoad;
			sw = new StopWatch();
			for ( String s : names )
				loadNameNonPrepared(s);
			nonPreparedLoad = sw.elapsedTime();

			// Load 1000 names prepared ( We use the arraylist names again )
			double preparedLoad;
			sw = new StopWatch();
			loadNamesPrepared(names);
			preparedLoad = sw.elapsedTime();

			// Print af test af 1000 saves
			System.out.println("1000 names loaded! \nNonprepared: " + nonPreparedLoad + "\nPrepared: " + preparedLoad);

			// Reset table again
			dropTable();

		} catch (SQLException e) {
			e.printStackTrace();
		}




	}

	public static void saveNameNonPrepared(String name) {
		try {

			DbDAO.saveNameNonPrepared(name);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to save name");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Failed to save name");
			System.exit(1);
		}
	}

	public static void saveNamePrepared(ArrayList<String> names) {
		try {

			DbDAO.saveNamePrepared(names);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to save names");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Failed to save names");
			System.exit(1);
		}
	}

	public static ArrayList<String> loadAllNamesNonPrepared() {
		ArrayList<String> al = null;

		try {

			al = DbDAO.loadAllNamesNonPrepared();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Failed to load name");
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to load name");
			System.exit(1);
		}

		return al;
	}

	public static String loadNameNonPrepared(String name) {
		String result = null;

		try {

			result = DbDAO.loadNameNonPrepared(name);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Failed to load name");
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to load name");
			System.exit(1);
		}

		return result;
	}

	public static ArrayList<String> loadNamesPrepared(ArrayList<String> names) {
		ArrayList<String> al = null;

		try {

			al = DbDAO.loadNamesPrepared(names);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Failed to load name");
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to load name");
			System.exit(1);
		}

		return al;
	}

	public static void dropTable() {
		try {
			DbDAO.dropTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
