package virtualPlanner.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.backend.Student;
import virtualPlanner.backend.User;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * A controller for all database operations.
 * 
 * @author JeremiahDeGreeff
 */
public class DatabaseController {
	
	private static final String HOST = "com62-virtualplanner.cxhgmqjablki.us-east-1.rds.amazonaws.com";
	private static final String PORT = "3306";
	private static final String DATABASE = "virtualplanner";
	private static final String USER = "jrdegreeff";
	private static final String PASSWORD = "supersecretpassword";
	
	/**
	 * This instance's connection to the database.
	 */
	private Connection connection;
	
	/**
	 * Instantiates a {@code DatabaseController} and tests the connection.
	 */
	public DatabaseController() {
		try {
			connection = connect();
			System.out.println("Database Connection Succesful.");
		}
		catch (SQLException e) {
			System.out.println("Failed to connnect to database - aborting.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Connects to the database.
	 * 
	 * @return The {@code Connection} instance.
	 * @throws SQLException if the {@code Connection} cannot be successfully formed.
	 */
	public Connection connect() throws SQLException {
		try {Class.forName("com.mysql.cj.jdbc.Driver").newInstance();}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {e.printStackTrace();}
		return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER, PASSWORD);
	}
	
	/**
	 * Queries the database.
	 * 
	 * @param sql An sql statement to query with. (SELECT)
	 * @return The {@code ResultSet} of the query.
	 */
	private ResultSet query(String sql) {
		try {return connection.createStatement().executeQuery(sql);}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Updates a row in the database.
	 * 
	 * @param sql An sql statement to update with. (INSERT, UPDATE, DELETE)
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	private boolean update(String sql) {
		try (Statement s = connection.createStatement()) {
			s.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Inserts a record into the database and returns its id.
	 * 
	 * @param sql An sql statement to execute - must be an INSERT statement.
	 * @return The id of the record inserted or -1 if an error occurs.
	 */
	private int insertGetID(String sql) {
		try (PreparedStatement s = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			s.executeUpdate();
			try (ResultSet r = s.getGeneratedKeys();) {
				if(r.next())
					return r.getInt(1);
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Loads a {@code User} from the database if the specified username password pair is correct.
	 * 
	 * @param username The {@code User}'s username.
	 * @param password The {@code User}'s password.
	 * @return The {@code User} with all appropriate {@code Course}s and {@code Assignment}s.
	 * @throw LoginException if the username is not registered or the password is invalid.
	 */
	public User login(String username, String password) throws LoginException {
		try (ResultSet u = query(String.format("SELECT id, username, password, name FROM user WHERE username = \'%s\';", username))) {
			if(!u.next()) // Username doesn't exist.
				throw new LoginException(LoginException.USER_NOT_REGISTERED);
			if(!u.getString("password").equals(password)) // Password is incorrect.
				throw new LoginException(LoginException.INVALID_PASSWORD);
			User user = new Student(u.getInt("id"), u.getString("name"));
			try (ResultSet uc = query(String.format("SELECT courseid, blockid FROM user_course WHERE userid = %d", user.getID()))) {
				Map<Integer, Course> courses = new TreeMap<Integer, Course>();
				Map<Integer, List<Integer>> blocks = new TreeMap<Integer, List<Integer>>();
				while(uc.next()) {
					int courseid = uc.getInt("courseid");
					if(!courses.containsKey(courseid)) {
						courses.put(courseid, loadCourse(courseid));
						blocks.put(courseid, new ArrayList<Integer>());
					}
					blocks.get(courseid).add(uc.getInt("blockid"));
				}
				for(int courseid : courses.keySet())
					user.addCourse(blocks.get(courseid).toArray(new Integer[0]), courses.get(courseid));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException(LoginException.SQL_ERROR);
		}
	}
	
	/**
	 * Loads a {@code Course} from the database and any {@code Assignment}s which are associated with it.
	 * 
	 * @param id The id of the {@code Course}.
	 * @return The {@code Course}, or null if no {@code Course} with the specified id exists.
	 */
	public Course loadCourse(int id) {
		try (ResultSet c = query(String.format("SELECT name, abbreviation, teacher FROM course WHERE id = %d", id))) {
			if(!c.next())
				return null;
			Course course = new Course(id, c.getString("name"), c.getString("abbreviation"), c.getString("teacher"));
			try (ResultSet ca = query(String.format("SELECT assignmentid FROM course_assignment WHERE courseid = %d", course.getID()))) {
				while(ca.next())
					course.addAssignment(loadAssignment(ca.getInt("assignmentid")));
			}
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Loads an {@code Assignment} from the database.
	 * 
	 * @param id The id of the {@code Assignment}.
	 * @return The {@code Assignment}, or null if no {@code Assignment} with the specified id exists.
	 */
	public Assignment loadAssignment(int id) {
		try (ResultSet a = query(String.format("SELECT name, description, isComplete, type, assigned, due FROM assignment WHERE id = %d", id))) {
			return a.next() ? new Assignment(id, new Date(a.getString("assigned")), new Date(a.getString("due")), AssignmentTypes.getTypeFromID(a.getInt("type")), a.getString("name"), a.getString("description"), a.getInt("isComplete") == 1) : null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates the account for a new user and adds it to the database.
	 * 
	 * @param username The username for the new user - must not already exist for operation to be successful.
	 * @param password The password for the new user.
	 * @param name The name for the new user
	 * @return {@code true} if successful, {@code false} otherwise.
	 * @throws LoginException if a user with the same username already exists.
	 */
	public boolean createUser(String username, String password, String name) throws LoginException {
		try (ResultSet r = query(String.format("SELECT username FROM user WHERE username = \'%s\';", username))) {
			if(r.next()) // Username already exists.
				throw new LoginException(LoginException.USERNAME_ALREADY_EXISTS);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException(LoginException.SQL_ERROR);
		}
		return update(String.format("INSERT INTO user (username, password, name) VALUES (\'%s\', \'%s\', \'%s\');", username, password, name));
	}
	
	/**
	 * Creates a {@code Course} Object and adds it to the database.
	 * 
	 * @param name The name of the {@code Course}.
	 * @param abbreviation An abbreviation for the {@code Course}.
	 * @param teacher The teacher of the {@code Course}.
	 * @return The new {@code Course} object.
	 */
	public Course createCourse(String name, String abbreviation, String teacher) {
		int id = insertGetID(String.format("INSERT INTO course (name, abbreviation, teacher) VALUES (\'%s\', \'%s\', \'%s\');", name, abbreviation, teacher));
		return loadCourse(id);
	}
	
	/**
	 * Creates a {@code Assignment} Object and adds it to the database.
	 * 
	 * @param name The name of the {@code Assignment}.
	 * @param description A description of the {@code Assignment}.
	 * @param type The type of the {@code Assignment}.
	 * @param assigned The {@code Date} when the {@code Assignment} is assigned.
	 * @param due The {@code Date} when the {@code Assignment} is due.
	 * @return The new {@code Assignment} object.
	 */
	public Assignment createAssignemnt(String name, String description, AssignmentTypes type, Date assigned, Date due) {
		int id = insertGetID(String.format("INSERT INTO assignment (name, description, isComplete, type, assigned, due) VALUES (\'%s\', \'%s\', 0, %d, \'%s\', \'%s\');", name, description, type.getID(), assigned.toStringSQL(), due.toStringSQL()));
		return loadAssignment(id);
	}
	
	/**
	 * Associates a {@code User} with a {@code Course} in a particular {@code Block}.
	 * 
	 * @param user The {@code User} to link.
	 * @param course The {@code Course} to link.
	 * @param block An array of {@code Block}s to link.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean link(User user, Course course, Block[] blocks) {
		boolean result = true;
		for(Block block : blocks)
			result = update(String.format("INSERT INTO user_course (userid, courseid, blockid) VALUES (%d, %d, %d)", user.getID(), course.getID(), block.getID())) && result;
		return result;
	}
	
	/**
	 * Associates a {@code Course} with an {@code Assignment}.
	 * 
	 * @param course The {@code Course} to link.
	 * @param assignment The {@code Assignment} to link.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean link(Course course, Assignment assignment) {
		return update(String.format("INSERT INTO course_assignment (courseid, assignmentid) VALUES (%d, %d)", course.getID(), assignment.getID()));
	}
	
	/**
	 * Removes all associations between a {@code User} and a {@code Course}.
	 * 
	 * @param user The {@code User} to unlink.
	 * @param course The {@code Course} to unlink.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean unlink(User user, Course course) {
		return update(String.format("DELETE FROM user_course WHERE userid = %d AND courseid = %d", user.getID(), course.getID()));
	}
	
	/**
	 * Removes all associations between a {@code Course} and an {@code Assignment}.
	 * 
	 * @param course The {@code Course} to unlink.
	 * @param assignment The {@code Assignment} to unlink.
	 * @return @code true} if successful, {@code false} otherwise.
	 */
	public boolean unlink(Course course, Assignment assignment) {
		return update(String.format("DELETE FROM course_assignment WHERE courseid = %d AND assignmentid = %d", course.getID(), assignment.getID()));
	}
	
	/**
	 * Updates a {@code User}'s record in the database.
	 * 
	 * @param user The {@code User} whose record should be updated.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean update(User user) {
		try (ResultSet r = query(String.format("SELECT id FROM user WHERE id = \'%d\';", user.getID()))) {
			if(!r.next()) // User doesn't exist.
				return false;
		} catch (SQLException e) {e.printStackTrace();}
		update(String.format("Update user SET name = \'%s\' WHERE id = %d;", user.getName(), user.getID()));
		return true;
	}
	
	/**
	 * Updates a {@code Course}'s record in the database.
	 * 
	 * @param course The {@code Course} whose record should be updated.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean update(Course course) {
		try (ResultSet r = query(String.format("SELECT id FROM course WHERE id = \'%d\';", course.getID()))) {
			if(!r.next()) // Course doesn't exist.
				return false;
		} catch (SQLException e) {e.printStackTrace();}
		update(String.format("Update course SET name = \'%s\', abbreviation = \'%s\', teacher = \'%s\' WHERE id = %d;", course.getName(), course.getAbbreviation(), course.getTeacher(), course.getID()));
		return true;
	}
	
	/**
	 * Updates an {@code Assignment}'s record in the database.
	 * 
	 * @param assignment The {@code Assignment} whose record should be updated.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean update(Assignment assignment) {
		try (ResultSet r = query(String.format("SELECT id FROM assignment WHERE id = \'%d\';", assignment.getID()))) {
			if(!r.next()) // Assignment doesn't exist.
				return false;
		} catch (SQLException e) {e.printStackTrace();}
		update(String.format("Update assignment SET name = \'%s\', description = \'%s\', isComplete = %d, type = %d, assigned = \'%s\', due = \'%s\' WHERE id = %d;", assignment.getName(), assignment.getDescrip(), assignment.isComplete() ? 1 : 0, assignment.getAssignmentType().getID(), assignment.getAssignedDate().toStringSQL(), assignment.getDue().toStringSQL(), assignment.getID()));
		return true;
	}
	
	/**
	 * Deletes all records of a user.
	 * 
	 * @param user The user to delete.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean delete(User user) {
		return update(String.format("DELETE FROM user WHERE id = %d", user.getID())) && update(String.format("DELETE FROM user_course WHERE userid = %d", user.getID()));
	}
	
	/**
	 * Removes all links from a {@code Course}.
	 * 
	 * @param course The {@code Course} to remove.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean delete(Course course) {
		return update(String.format("DELETE FROM course_assignment WHERE courseid = %d", course.getID())) && update(String.format("DELETE FROM user_course WHERE courseid = %d", course.getID()));
	}
	
	/**
	 * Removes all links from a {@code Assignment assignment}.
	 * 
	 * @param assignment The {@code Assignment assignment} to remove.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean delete(Assignment assignment) {
		return update(String.format("DELETE FROM course_assignment WHERE assignmentid = %d", assignment.getID()));
	}
	
}
