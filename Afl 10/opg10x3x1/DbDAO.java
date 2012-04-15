package opg10x3x1;

import java.sql.Connection;
import java.sql.DriverManager;
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

	public static void saveName(String name) throws SQLException, ClassNotFoundException {


		String url = "jdbc:derby:NameDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();

		// Check if the table exists and create it if it does not.
		boolean nameTableIsCreated = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("NAME"))
				nameTableIsCreated = true;
		}

		if (!nameTableIsCreated) {
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

		String url = "jdbc:derby:NameDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();

		// Check if the table exists.
		boolean nameTableExists = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("NAME"))
				nameTableExists = true;
		}

		final ArrayList<String> al = new ArrayList<String>();
		if (nameTableExists) {
			try {
				String sql = "";

				sql += "SELECT * FROM NAME";

				executeQuery("select * from NAME", new QueryCallBack() {
					public void processRecord(ResultSet rs) throws SQLException {
						al.add(rs.getString(1));
					}
				});

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
	
	// Drops the table
	public static void dropTable() throws SQLException {
		String url = "jdbc:derby:NameDB;create=true";
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();
		
		stmt.executeUpdate("DROP TABLE NAME");
		
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
