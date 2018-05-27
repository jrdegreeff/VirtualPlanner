package virtualPlanner.gui;

import java.util.ArrayList;

import virtualPlanner.Controller;
import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.io.LoginException;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * Controller for the GUI classes which provides an interface to the backend.
 * 
 * @author JeremiahDeGreeff
 */
public class GUIController {
	
	/**
	 * The {@code Controller} for this instance.
	 */
	private Controller controller;
	
	/**
	 * Initializes the GUI.
	 * 
	 * @param controller The {@code Controller} for this instance.
	 */
	public GUIController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Attempts to login a user.
	 * If successful creates a CalendarWindow.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @return 0 if successful or a negative error code as specified in {@link LoginException}.
	 */
	public int login(String username, String password) {
		int result = controller.login(username, password);
		if(result == 0)
			new MainCalendarWindow(this);
		return result;
	}
	
	/**
	 * @return The name of the user.
	 */
	public String getUserName() {
		return controller.getUserName();
	}
	
	/**
	 * Retrieves string representations of all of the user's courses.
	 * 
	 * @return String representations of the user's courses.
	 */
	public String[] getCourseNames() {
		return controller.getCourseNames();
	}
	
	/**
	 * Retrieves the id of the {@code Course} from the user's schedule in the specified {@code Block}.
	 * 
	 * @param block The {@code Block} to find the {@code Course} for.
	 * @return The id of the {@code Course} associated with the specified {@code Block}, or a value of -1 if the user has no {@code Course} for that {@code Block}.
	 */
	public int getCourseID(Block block) {
		return controller.getCourseID(block);
	}
	
	/**
	 * Creates a new {@code Course} and adds it to the user's schedule in the specified {@code Block}s.
	 * 
	 * @param blocks The {@code Block}s to add the {@code Course} to.
	 * @param name The name of the new {@code Course}.
	 * @param abbreviation The abbrevation of the new {@code Course}.
	 * @param teacher The name the teacher of the new {@code Course}.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in the user's schedule.
	 */
	public boolean addCourse(Block[] blocks, String name, String abbreviation, String teacher) {
		return controller.addCourse(blocks, name, abbreviation, teacher);
	}
	
	/**
	 * Removes a {@code Course} from the user's schedule entirely.
	 * 
	 * @param course The {@code Course} to remove.
	 */
	public void removeCourse(Course course) {
		controller.removeCourse(course);
	}
	
	/**
	 * Updates a {@code Course} in the user's schedule.
	 * Adds the {@code Course} to the specified {@code Block}s and removes the {@code Course} from any {@code Block} that is not specified.
	 * 
	 * @param newBlocks The updated {@code Block}s for the {@code Course}.
	 * @param course The {@code Course} to update.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in the user's schedule.
	 */
	public boolean changeCourseBlocks(Block[] newBlocks, Course course) {
		return controller.changeCourseBlocks(newBlocks, course);
	}
	
	/**
	 * Retrieves the user's {@code Assignment}s for a particular {@code Block} on a particular {@code Date}.
	 * 
	 * @param date The {@code Date} to query.
	 * @param block The {@code Block} to query.
	 * @return The {@code Assignment}s which the user has for the specified {@code Date} and {@code Block} or {@code null} if there are no such {@code Assignment}s.
	 */
	public ArrayList<Assignment> getAssignments(Date date, Block block) {
		return controller.getAssignments(date, block);
	}
	
	/**
	 * Creates a new {@code Assignment} and adds it to the specified {@code Course} in the user's schedule.
	 * 
	 * @param course The {@code Course} to add the new {@code Assignment} to.
	 * @param assigned The assigned date for the new {@code Assignment}.
	 * @param due The due date for the new {@code Assignment}.
	 * @param type The type for the new {@code Assignment}.
	 * @param name The name for the new {@code Assignment}.
	 * @param description The description for the new {@code Assignment}.
	 */
	public void addAssignment(Course course, Date assigned, Date due, AssignmentTypes type, String name, String description) {
		controller.addAssignment(course, assigned, due, type, name, description);
	}
	
}
