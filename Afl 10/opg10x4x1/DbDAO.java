package opg10x4x1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbDAO {

	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Connection con;
	private static Statement stmt;

	public static void createTable() throws SQLException {

		String url = "jdbc:derby:EntryDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();

		// Check if the table exists and create it if it does not.
		boolean nameTableIsCreated = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("ENTRY"))
				nameTableIsCreated = true;
		}

		if (!nameTableIsCreated) {
			try {
				String sql = "";

				sql += "CREATE TABLE ENTRY";
				sql += "(";
				sql += "Entry VARCHAR(50) NOT NULL";
				sql += ")";

				stmt.executeUpdate(sql);
				System.out.println("Created table ENTRY");

			} catch (SQLException e) {
				System.err.println("Failed to create table ENTRY");
				e.printStackTrace();

				stmt.close();
				con.close();
			}
		}
	}

	public static void saveNameNonPrepared(String name) throws SQLException, ClassNotFoundException {

		String url = "jdbc:derby:EntryDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();

		// Insert the person into the table
		try {
			String sql = "";

			sql += "INSERT INTO ENTRY VALUES";
			sql += "(";
			sql += "'" + name + "'";
			sql += ")";

			stmt.executeUpdate(sql);
			//			System.out.println("Inserted " + name + " into table ENTRY");

			stmt.close();
			con.close();

		} catch (SQLException e) {
			System.err.println("Failed to insert " + name + " into table ENTRY");

			stmt.close();
			con.close();
			e.printStackTrace();
		}
	}

	public static void saveNamePrepared(ArrayList<String> names) throws SQLException, ClassNotFoundException {

		String url = "jdbc:derby:EntryDB;create=true";
		con = DriverManager.getConnection(url);
		PreparedStatement ppstmt = con.prepareStatement("INSERT INTO ENTRY VALUES(?)");

		// Insert the person into the table
		try {

			for ( String s : names ) {

				ppstmt.setString(1, s);
				ppstmt.executeUpdate();
//				System.out.println("Inserted " + s + " into table ENTRY");
			}

			stmt.close();
			con.close();

		} catch (SQLException e) {
			System.err.println("Failed to insert an entry into table ENTRY");

			stmt.close();
			con.close();
			e.printStackTrace();
		}
	}

	// Loads all the names from the database
	public static ArrayList<String> loadAllNamesNonPrepared() throws ClassNotFoundException, SQLException {

		String url = "jdbc:derby:EntryDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();

		final ArrayList<String> al = new ArrayList<String>();

		try {

			executeQuery("select * from ENTRY", new QueryCallBack() {
				public void processRecord(ResultSet rs) throws SQLException {
					al.add(rs.getString(1));
				}
			});

			stmt.close();
			con.close();

//			System.out.println("Fetched " + al.size() + " entries from table ENTRY");

			return al;

		} catch (SQLException e) {
			System.err.println("Failed to fetch data from table ENTRY");
			e.printStackTrace();

			stmt.close();
			con.close();

			System.exit(1);
			return null;
		}
	}
	
	// Loads all the names from the database prepared
		public static String loadNameNonPrepared(String name) throws ClassNotFoundException, SQLException {

			String url = "jdbc:derby:EntryDB;create=true";
			con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			String result = "";

			try {
				
				ResultSet rs = stmt.executeQuery("SELECT * from ENTRY WHERE Entry='" + name + "'");
				while (rs.next())
					result = rs.getString(1);
				rs.close();
				stmt.close();
				con.close();

//				System.out.println("Fetched " + al.size() + " entries from table ENTRY");

				return result;

			} catch (SQLException e) {
				System.err.println("Failed to fetch data from table ENTRY");
				e.printStackTrace();

				stmt.close();
				con.close();

				System.exit(1);
				return null;
			}
		}
	
	// Loads all the names from the database prepared
	public static ArrayList<String> loadNamesPrepared(ArrayList<String> names) throws ClassNotFoundException, SQLException {

		String url = "jdbc:derby:EntryDB;create=true";
		con = DriverManager.getConnection(url);
		PreparedStatement ppstmt = con.prepareStatement("SELECT * from ENTRY WHERE Entry=?");

		ArrayList<String> al = new ArrayList<String>();

		try {

			for ( String s : names ) {
				ppstmt.setString(1, s);
				ResultSet rs = ppstmt.executeQuery();
				while (rs.next())
					al.add(rs.getString(1));
				rs.close();
			}
			
			stmt.close();
			con.close();

//			System.out.println("Fetched " + al.size() + " entries from table ENTRY");

			return al;

		} catch (SQLException e) {
			System.err.println("Failed to fetch data from table ENTRY");
			e.printStackTrace();

			stmt.close();
			con.close();

			System.exit(1);
			return null;
		}
	}


	// Drops the table
	public static void dropTable() throws SQLException {
		String url = "jdbc:derby:EntryDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();

		stmt.executeUpdate("DROP TABLE ENTRY");

		stmt.close();
		con.close();
	}

	// Executes a query with callback so that we make sure, that the result set is closed afterwards
	private static void executeQuery(String query, QueryCallBack qcb) {
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				qcb.processRecord(rs);
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
