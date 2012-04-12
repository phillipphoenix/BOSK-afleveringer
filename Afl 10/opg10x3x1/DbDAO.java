package opg10x3x1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbDAO {
	
	public static void saveName(String name) throws SQLException, ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		String url = "jdbc:derby:NameDB;create=true";
		Connection con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();

		// Check if the table exists and create it if it does not.
		boolean personTableIsCreated = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("NAME"))
				personTableIsCreated = true;
		}

		if (!personTableIsCreated) {
			try {
				String sql = "";

				sql += "CREATE TABLE NAME";
				sql += "(";
				sql += "NAME VARCHAR(50) NOT NULL";
				sql += ")";

				stmt.executeUpdate(sql);
				System.out.println("Created table NAME");
				
			} catch (SQLException e) {
				System.out.println("Failed to create table NAME");
				e.printStackTrace();
				
				stmt.close();
				con.close();
			}
		}

		// Insert the person into the table
		try {
			String sql = "";

			sql += "INSERT INTO NAME VALUES";
			sql += "(";
			sql += "'" + name + "'";
			sql += ")";

			stmt.executeUpdate(sql);
			System.out.println("Inserted " + name + " into table NAME");
			
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Failed to insert " + name + " into table NAME");
			
			stmt.close();
			con.close();
			e.printStackTrace();
		}
	}
	
	// Loads all the names from the database
	public static ArrayList<String> loadAllNames() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		String url = "jdbc:derby:NameDB;create=true";
		Connection con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();

		// Check if the table exists.
		boolean personTableExists = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("NAME"))
				personTableExists = true;
		}

		ArrayList<String> al = null;
		if (personTableExists) {
			al = new ArrayList<String>();
			try {
				String sql = "";

				sql += "SELECT * FROM NAME";
				ResultSet rs = stmt.executeQuery(sql); 		// TODO HER SKAL QUERY LAVES OM, TIL DET NYE DER!
				
				while(rs.next())
					al.add(rs.getString(1));

				rs.close();
				stmt.close();
				con.close();
				
				System.out.println("Fetched " + al.size() + " entries from table NAME");

				return al;

			} catch (SQLException e) {
				System.out.println("Failed to fetch data from table NAME");
				e.printStackTrace();
				
				stmt.close();
				con.close();
				
				System.exit(1);
				return null;
			}
		} else {
			System.out.println("No NAME table exists");
			
			stmt.close();
			con.close();
			
			return null;
		}
	}
	
}
