package virtualPlanner.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.backend.User;

public class DatabaseController {
	
	private static final String HOST = "com62-virtualplanner.cxhgmqjablki.us-east-1.rds.amazonaws.com";
	private static final String PORT = "3306";
	private static final String DATABASE = "virtualplanner";
	private static final String USER = "jrdegreeff";
	private static final String PASSWORD = "supersecretpassword";
	
	/**
	 * Connects to the database.
	 * 
	 * @return The {@code Connection} instance.
	 */
	public static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER, PASSWORD);
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
	private static Statement createStatement() {
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
	private static ResultSet query(String sql) {
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
	private static boolean update(String sql) {
		try {
			createStatement().executeUpdate(sql);
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Creates the account for a new user and adds them to the database.
	 * 
	 * @param username The username for the new user - must not already exist for operation to be successful.
	 * @param password The password for the new user.
	 * @param name The name for the new user
	 * @return 0 if operation was successful, -1 if the username already exists.
	 */
	public static int registerUser(String username, String password, String name) {
		try {
			if(query(String.format("SELECT username FROM user WHERE username = \'%s\';", username)).next())
				return -1; // Username already exists.
		} catch (SQLException e) {e.printStackTrace();}
		update(String.format("INSERT INTO user (username, password, name) VALUES (\'%s\', \'%s\', \'%s\');", username, password, name));
		return 0;
	}
	
	public static Course addCourse() {
		return null;
	}
	
	public static Assignment addAssignemnt() {
		return null;
	}
	
	public static boolean update(User user) {
		return false;
	}
	
	public static boolean update(Course course) {
		return false;
	}
	
	public static boolean update(Assignment assignment) {
		return false;
	}
	
	public static boolean remove(Course course) {
		return false;
	}
	
	public static boolean remove(Assignment assignment) {
		return false;
	}
	
}
