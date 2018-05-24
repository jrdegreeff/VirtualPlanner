package virtualPlanner.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
	
	private static final int PORT = 3306;
	private static final String DATABASE = "virtual_planner";
	private static final String USER = "root";
	private static final String PASSWORD = "supersecurepassword";
	
	/**
	 * Connects to the database.
	 * 
	 * @return The {@code Connection} instance.
	 */
	public static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			return DriverManager.getConnection("jdbc:mysql://localhost:" + PORT + "/" + DATABASE + "?serverTimezone=UTC&useSSL=false", USER, PASSWORD);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates a statement.
	 * 
	 * @return The {@code Statement} instance.
	 */
	public static Statement createStatement() {
		try {return connect().createStatement();}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Queries the database.
	 * 
	 * @param sql An sql statement to query with. (SELECT)
	 * @return The {@code ResultSet} of the query.
	 */
	public static ResultSet query(String sql) {
		try {return createStatement().executeQuery(sql);}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Updates a row in the database.
	 * 
	 * @param sql An sql statement to update with. (INSERT, UPDATE, DELETE)
	 * @return {@code true} if successful, {@code false} otherwise
	 */
	public static boolean update(String sql) {
		try {
			createStatement().executeUpdate(sql);
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
