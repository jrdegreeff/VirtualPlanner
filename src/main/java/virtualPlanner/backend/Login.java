package virtualPlanner.backend;

import java.util.HashMap;
import java.util.Map;
/**
 * the Login class that stores login information and registers/logs in students
 * 
 * @author aldai
 * @author Leo
 */
public class Login {
	/**
	 * maps student name to password
	 */
	private static Map<Student, String> loginInfo = new HashMap<Student, String>();
	
	/**
	 * Registers new Student
	 * @param name student name (always use student name as username)
	 * @param pswd password
	 */
	public static void register(String name, String pswd) {
		Student newStudent = new Student(name);
		loginInfo.put(newStudent, pswd);
	}
	
	/**
	 * Logs a student already registered in
	 * @param username student name
	 * @param pswd password
	 * @return Student object if registered, null if not registered or incorrect password
	 */
	public static Student login(String username, String pswd) {
		for (Student s : loginInfo.keySet()) {
			if (s.getName().equals(username) && loginInfo.get(s).equals(pswd)) return s;
		}
		return null;
	}
}
