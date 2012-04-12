package opg10x2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbDataAccessObject {

	public static void savePerson(Person person) throws SQLException, ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		String url = "jdbc:derby:PersonDB;create=true";
		Connection con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();

		// Check if the table exists and create it if it does not.
		boolean personTableIsCreated = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("PERSON"))
				personTableIsCreated = true;
		}

		if (!personTableIsCreated) {
			try {
				String sql = "";

				sql += "CREATE TABLE PERSON";
				sql += "(";
				sql += "PERSON_PK INTEGER NOT NULL PRIMARY KEY,";
				sql += "FIRST_NAME VARCHAR(50) NOT NULL,";
				sql += "LAST_NAME VARCHAR(50) NOT NULL";
				sql += ")";

				stmt.executeUpdate(sql);
				System.out.println("Created table PERSON");
				
			} catch (SQLException e) {
				System.out.println("Failed to create table PERSON");
				e.printStackTrace();
				
				stmt.close();
				con.close();
			}
		}

		// Insert the person into the table
		try {
			String sql = "";

			sql += "INSERT INTO PERSON VALUES";
			sql += "(";
			sql += person.getId() + ",";
			sql += "'" + person.getFirstName() + "',";
			sql += "'" + person.getLastName() + "'";
			sql += ")";

			stmt.executeUpdate(sql);
			System.out.println("Inserted " + person.getFirstName() + " into table PERSON");
			
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Failed to insert " + person.getFirstName() + " into table PERSON");
			
			stmt.close();
			con.close();
			e.printStackTrace();
		}

	}

	public static Person loadPerson(int id) throws SQLException, ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		String url = "jdbc:derby:PersonDB;create=true";
		Connection con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();

		// Check if the table exists.
		boolean personTableIsCreated = false;
		ResultSet cmd = con.getMetaData().getTables(null, null, null, 
				new String[] {"TABLE"});
		while (cmd.next()) {
			if (cmd.getString("TABLE_NAME").equals("PERSON"))
				personTableIsCreated = true;
		}

		if (personTableIsCreated) {
			try {
				String sql = "";

				sql += "SELECT * FROM PERSON WHERE PERSON_PK=" + id;
				ResultSet rs = stmt.executeQuery(sql);

				Person person = null;
				while(rs.next())
					person = new Person(id, rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"));

				rs.close();
				stmt.close();
				con.close();
				
				System.out.println("Fetched " + person.getFirstName() + " from table PERSON");

				return person;

			} catch (SQLException e) {
				System.out.println("Failed to fetch data from table PERSON");
				e.printStackTrace();
				
				stmt.close();
				con.close();
				
				System.exit(1);
				return null;
			}
		} else {
			System.out.println("No PERSON table exists");
			
			stmt.close();
			con.close();
			
			return null;
		}
	}
}
