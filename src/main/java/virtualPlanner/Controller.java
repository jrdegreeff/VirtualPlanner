package virtualPlanner;

import java.util.ArrayList;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.backend.User;
import virtualPlanner.gui.GUIController;
import virtualPlanner.io.DatabaseController;
import virtualPlanner.io.LoginException;
import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * Central controller for the VirtualPlanner project.
 * All communication between the GUI, backend, and IO packages must go through an instance of this class.
 * 
 * @author JeremiahDeGreeff
 */
public class Controller {
	
	/**
	 * The {@code DatabaseController} instance for the VirtualPlanner.
	 */
	private DatabaseController dbController;
	/**
	 * The {@code GUIController} instance for the VirtualPlanner.
	 */
	private GUIController guiController;
	/**
	 * The {@code User} instance for the VirtualPlanner.
	 */
	private User user;
	
	public Controller() {
		dbController = new DatabaseController();
		guiController = new GUIController(this);
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
		try {dbController.createUser(username, password, name);}
		catch (LoginException e) {return e.getErrorCode();}
		return 0;
	}
	
	/**
	 * Attempts to login and load a user's data.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @return 0 if successful or a negative error code as specified in {@link LoginException}.
	 */
	public int login(String username, String password) {
		try {user = dbController.login(username, password);}
		catch (LoginException e) {return e.getErrorCode();}
		return 0;
	}
	
	/**
	 * @return The name of the user.
	 */
	public String getUserName() {
		return user.getName();
	}
	
	/**
	 * Retrieves string representations of all of the user's courses.
	 * 
	 * @return String representations of the user's courses.
	 */
	public String[] getCourseNames() {
		return user.getCourseNames();
	}
	
	/**
	 * Retrieves all of the {@code User}'s {@code Courses}.
	 * 
	 * @return All of the {@code User}'s {@code Courses}.
	 */
	public Course[] getAllCourses() {
		return user.getAllCourses();
	}
	
	/**
	 * Retrieves the {@code Course} from the user's schedule in the specified {@code Block}.
	 * 
	 * @param block The {@code Block} to find the {@code Course} for.
	 * @return The {@code Course} associated with the specified {@code Block}, or {@code null} if the user has no {@code Course} for that {@code Block}.
	 */
	public Course getCourse(Block block) {
		return user.getCourse(block);
	}
	
	/**
	 * Retrieves the user's {@code Assignment}s for a particular {@code Block} on a particular {@code Date}.
	 * 
	 * @param date The {@code Date} to query.
	 * @param block The {@code Block} to query.
	 * @return The {@code Assignment}s which the user has for the specified {@code Date} and {@code Block} or {@code null} if there are no such {@code Assignment}s.
	 */
	public ArrayList<Assignment> getAssignments(Date date, Block block) {
		return user.getAssignments(date, block, Preferences.displayOnDue());
	}
	
	/**
	 * Changes the name of the {@code User}.
	 * 
	 * @param newName The new name for the {@code User}.
	 */
	public void setUserName(String newName) {
		user.setName(newName);
		dbController.update(user);
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
		Course course = dbController.createCourse(name, abbreviation, teacher);
		return user.addCourse(blocks, course) && dbController.link(user, course, blocks);
	}
	
	/**
	 * Removes a {@code Course} from the user's schedule entirely.
	 * 
	 * @param course The {@code Course} to remove.
	 */
	public void removeCourse(Course course) {
		user.removeCourse(course);
		dbController.delete(course);
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
		return dbController.unlink(user, course) && user.updateCourse(newBlocks, course) && dbController.link(user, course, newBlocks);
	}
	
	/**
	 * Changes the name of a {@code Course}.
	 * 
	 * @param course The {@code Course} to update.
	 * @param newName The new name for the {@code Course}.
	 */
	public void setCourseName(Course course, String newName) {
		course.setName(newName);
		dbController.update(course);
	}
	
	/**
	 * Changes the name of a {@code Course}.
	 * 
	 * @param course The {@code Course} to update.
	 * @param newAbbreviation The new abbreviation for the {@code Course}.
	 */
	public void setCourseAbbreviation(Course course, String newAbbreviation) {
		course.setAbbreviation(newAbbreviation);
		dbController.update(course);
	}
	
	/**
	 * Changes the name of a {@code Course}.
	 * 
	 * @param course The {@code Course} to update.
	 * @param newTeacher The new teacher for the {@code Course}.
	 */
	public void setCourseTeacher(Course course, String newTeacher) {
		course.setTeacher(newTeacher);
		dbController.update(course);
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
		Assignment assignment = dbController.createAssignemnt(name, description, type, assigned, due);
		course.addAssignment(assignment);
		dbController.link(course, assignment);
	}
	
	/**
	 * Removes an {@code Assignment} entirely.
	 * 
	 * @param course The course to which the {@code Assignment} is assigned.
	 * @param assignment The {@code Assignment} to be removed.
	 */
	public void removeAssignment(Course course, Assignment assignment) {
		course.removeAssignment(assignment);
		dbController.delete(assignment);
	}
	
	/**
	 * Changes the {@code Course} to which an {@code Assignment} is assigned.
	 * 
	 * @param oldCourse The {@code Course} to remove the {@code Assignment} from.
	 * @param newCourse The {@code Course} to add the {@code Assignment} to.
	 * @param assignment The {@code Assignment} to manipulate.
	 */
	public void changeAssignmentCourse(Course oldCourse, Course newCourse, Assignment assignment) {
		oldCourse.removeAssignment(assignment);
		dbController.unlink(oldCourse, assignment);
		newCourse.addAssignment(assignment);
		dbController.link(newCourse, assignment);
	}
	
	/**
	 * Changes the assigned date of an {@code Assignment}.
	 * 
	 * @param course The {@code Course} to which the {@code Assignment} is assigned.
	 * @param assignment The {@code Assignment} to update.
	 * @param newDate The new assigned date.
	 */
	public void changeAssignedDate(Course course, Assignment assignment, Date newDate) {
		course.changeAssignedDate(assignment, newDate);
		dbController.update(course);
		dbController.update(assignment);
	}
	
	/**
	 * Changes the due date of an {@code Assignment}.
	 * 
	 * @param course The {@code Course} to which the {@code Assignment} is assigned.
	 * @param assignment The {@code Assignment} to update.
	 * @param newDate The new due date.
	 */
	public void changeDueDate(Course course, Assignment assignment, Date newDate) {
		course.changeDueDate(assignment, newDate);
		dbController.update(course);
		dbController.update(assignment);
	}
	
	/**
	 * Changes the name of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose name will be changed.
	 * @param newName The name to change to.
	 */
	public void setAssignmentName(Assignment assignment, String newName) {
		assignment.setName(newName);
		dbController.update(assignment);
	}
	
	/**
	 * Changes the description of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose description will be changed.
	 * @param newDescription The description to change to.
	 */
	public void setAssignmentDescription(Assignment assignment, String newDescription) {
		assignment.setDescrip(newDescription);
		dbController.update(assignment);
	}
	
	/**
	 * Changes the type of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose type will be changed.
	 * @param newType The type to change to.
	 */
	public void setAssignmentType(Assignment assignment, AssignmentTypes newType) {
		assignment.setType(newType);
		dbController.update(assignment);
	}
	
	/**
	 * Changes the completeness of an {@code Assignment}.
	 * 
	 * @param assignment The {@code Assignment} whose completeness will be changed.
	 * @param isComplete {@code true} if complete, {@code false} otherwise.
	 */
	public void setAssignmentComplete(Assignment assignment, boolean isComplete) {
		assignment.setComplete(isComplete);
		dbController.update(assignment);
	}
	
}
