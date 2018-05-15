package virtualPlanner.gui;

import java.util.ArrayList;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.backend.User;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

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
	 * Creates a new {@code Course} and adds it to the user's schedule in the specified {@code Block}s.
	 * 
	 * @param blocks The {@code Block}s to add the {@code Course} to.
	 * @param name The name of the new {@code Course}.
	 * @param teacher The name the teacher of the new {@code Course}.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in the user's schedule.
	 */
	public boolean addCourse(Block[] blocks, String name, String teacher) {
		return user.addCourse(blocks, new Course(name, teacher));
	}
	
	/**
	 * Removes a {@code Course} from the user's schedule entirely.
	 * 
	 * @param course The {@code Course} to remove.
	 */
	public void removeCourse(Course course) {
		user.removeCourse(course);
	}
	
	/**
	 * Updates a {@code Course} in the user's schedule.
	 * Adds the {@code Course} to the specified {@code Block}s and removes the {@code Course} from any {@code Block} that is not specified.
	 * 
	 * @param newBlocks The updated {@code Block}s for the {@code Course}.
	 * @param course The {@code Course} to update.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in the user's schedule.
	 */
	public boolean updateCourse(Block[] newBlocks, Course course) {
		return user.updateCourse(newBlocks, course);
	}
	
	/**
	 * Retrieves the user's {@code Assignment}s for a particular {@code Block} on a particular {@code Date}.
	 * 
	 * @param day The {@code Date} to query.
	 * @param block The {@code Block} to query.
	 * @return The {@code Assignment}s which the user has for the specified {@code Date} and {@code Block} or {@code null} if there are no such {@code Assignment}s.
	 */
	public ArrayList<Assignment> getAssignments(Date date, Block block) {
		return user.getAssignments(date, block, Preferences.displayOnDue());
	}
	
}
