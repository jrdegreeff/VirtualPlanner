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
//		new LoginWindow(this);
		controller.login("jrdegreeff", "1234");
		new MainCalendarWindow(this);
	}
	
	/**
	 * Registers a user with a username and password.
	 * 
	 * @param username The username for the new user.
	 * @param password The password for the new user.
	 * @param name The name of the new user.
	 * @return 0 if successful or a negative error code as specified in {@link LoginException}.
	 */
	public int signUp(String username, String password, String name) {
		return controller.signUp(username, password, name);
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
	 * Retrieves String representations of all of the user's courses.
	 * 
	 * @return String representations of the user's courses.
	 */
	public String[] getCourseNames() {
		return controller.getCourseNames();
	}
	
	/**
	 * Retrieves all of the user's {@code Courses}.
	 * 
	 * @return All of the user's {@code Courses}.
	 */
	public Course[] getAllCourses() {
		return controller.getAllCourses();
	}
	
	/**
	 * Retrieves the {@code Course} from the user's schedule in the specified {@code Block}.
	 * 
	 * @param block The {@code Block} to find the {@code Course} for.
	 * @return The {@code Course} associated with the specified {@code Block}, or {@code null} if the user has no {@code Course} for that {@code Block}.
	 */
	public Course getCourse(Block block) {
		return controller.getCourse(block);
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
	 * Retrieves the user's {@code Assignment}s' names for a particular {@code Block} on a particular {@code Date}.
	 * 
	 * @param date The {@code Date} to query.
	 * @param block The {@code Block} to query.
	 * @return The {@code Assignment}s' names which the user has for the specified {@code Date} and {@code Block} or {@code null} if there are no such {@code Assignment}s.
	 */
	public ArrayList<String> getAssignmentNames(Date date, Block block) {
		ArrayList<Assignment> assignments = controller.getAssignments(date, block);
		ArrayList<String> result = new ArrayList<String>();
		if(assignments == null)
			return new ArrayList<String>();
		
		for(Assignment a: assignments)
			result.add(a.getName());
		
		return result;
	}
	
	/**
	 * Changes the name of the the user.
	 * 
	 * @param newName The new name for the user.
	 */
	public void setUserName(String newName) {
		controller.setUserName(newName);
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
	 * Changes the name of a {@code Course}.
	 * 
	 * @param course The {@code Course} to update.
	 * @param newName The new name for the {@code Course}.
	 */
	public void setCourseName(Course course, String newName) {
		controller.setCourseName(course, newName);
	}
	
	/**
	 * Changes the name of a {@code Course}.
	 * 
	 * @param course The {@code Course} to update.
	 * @param newAbbreviation The new abbreviation for the {@code Course}.
	 */
	public void setCourseAbbreviation(Course course, String newAbbreviation) {
		controller.setCourseAbbreviation(course, newAbbreviation);
	}
	
	/**
	 * Changes the name of a {@code Course}.
	 * 
	 * @param course The {@code Course} to update.
	 * @param newTeacher The new teacher for the {@code Course}.
	 */
	public void setCourseTeacher(Course course, String newTeacher) {
		controller.setCourseTeacher(course, newTeacher);
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
	
	/**
	 * Removes an {@code Assignment} entirely.
	 * 
	 * @param course The course to which the {@code Assignment} is assigned.
	 * @param assignment The {@code Assignment} to be removed.
	 */
	public void removeAssignment(Course course, Assignment assignment) {
		controller.removeAssignment(course, assignment);
	}
	
	/**
	 * Changes the {@code Course} to which an {@code Assignment} is assigned.
	 * 
	 * @param oldCourse The {@code Course} to remove the {@code Assignment} from.
	 * @param newCourse The {@code Course} to add the {@code Assignment} to.
	 * @param assignment The {@code Assignment} to manipulate.
	 */
	public void changeAssignmentCourse(Course oldCourse, Course newCourse, Assignment assignment) {
		controller.changeAssignmentCourse(oldCourse, newCourse, assignment);
	}
	
	/**
	 * Changes the assigned date of an {@code Assignment}.
	 * 
	 * @param course The {@code Course} to which the {@code Assignment} is assigned.
	 * @param assignment The {@code Assignment} to update.
	 * @param newDate The new assigned date.
	 */
	public void changeAssignedDate(Course course, Assignment assignment, Date newDate) {
		controller.changeAssignedDate(course, assignment, newDate);
	}
	
	/**
	 * Changes the due date of an {@code Assignment}.
	 * 
	 * @param course The {@code Course} to which the {@code Assignment} is assigned.
	 * @param assignment The {@code Assignment} to update.
	 * @param newDate The new due date.
	 */
	public void changeDueDate(Course course, Assignment assignment, Date newDate) {
		controller.changeDueDate(course, assignment, newDate);
	}
	
	/**
	 * Changes the name of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose name will be changed.
	 * @param newName The name to change to.
	 */
	public void setAssignmentName(Assignment assignment, String newName) {
		controller.setAssignmentName(assignment, newName);
	}
	
	/**
	 * Changes the description of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose description will be changed.
	 * @param newDescription The description to change to.
	 */
	public void setAssignmentDescription(Assignment assignment, String newDescription) {
		controller.setAssignmentDescription(assignment, newDescription);
	}
	
	/**
	 * Changes the type of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose type will be changed.
	 * @param newType The type to change to.
	 */
	public void setAssignmentType(Assignment assignment, AssignmentTypes newType) {
		controller.setAssignmentType(assignment, newType);
	}
	
	/**
	 * Changes the completeness of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose completeness will be changed.
	 * @param isComplete {@code true} if complete, {@code false} otherwise.
	 */
	public void setAssignmentComplete(Assignment assignment, boolean isComplete) {
		controller.setAssignmentComplete(assignment, isComplete);
	}
	
}
