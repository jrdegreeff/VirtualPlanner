package virtualPlanner.gui;

import java.util.ArrayList;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.backend.User;
import virtualPlanner.reference.Blocks;
import virtualPlanner.reference.Days;

/**
 * Controller for the GUI classes which provides an interface to the backend.
 * 
 * @author JeremiahDeGreeff
 */
public class GUIController {
	
	/**
	 * The {@code User} whose schedule is represented by the GUI.
	 */
	private User user;
	
	/**
	 * Retrieves the user's {@code Assignment}s for a particular block on a particular day.
	 * 
	 * @param day The day to query.
	 * @param block The block to query.
	 * @return The {@code Assignment}s which the user has for the specified day and block or {@code null} if there are no such {@code Assignment}s.
	 */
	public ArrayList<Assignment> getAssignments(Days day, Blocks block) {
		Course course = user.getCourse(block);
		return null; //course == null ? null : course.getAssignments(day);
	}
	
}
