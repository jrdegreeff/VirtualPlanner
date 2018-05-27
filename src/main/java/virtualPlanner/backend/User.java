package virtualPlanner.backend;

import java.util.ArrayList;

import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * Defines a user with a block schedule.
 * 
 * @author JeremiahDeGreeff
 */
public interface User {
	
	/**
	 * @return The id of this {@code User}.
	 */
	public int getID();
	
	/**
	 * @return The name of the this {@code User}.
	 */
	public String getName();
	
	/**
	 * Changes the name of this {@code User}.
	 * 
	 * @param name The new name.
	 */
	public void setName(String name);
	
	/**
	 * Retrieves string representations of all of this {@code User}'s courses.
	 * 
	 * @return String representations of the {@code User}'s courses.
	 */
	public String[] getCourseNames();
	
	/**
	 * Retrieves the {@code Course} from this {@code User}'s schedule in a particular {@code Block}.
	 * 
	 * @param block The {@code Block} to find the course for.
	 * @return The {@code Course} associated with the specified {@code Block}, or {@code null} if this {@code User} has no {@code Course} for that {@code Block}.
	 */
	public Course getCourse(Block block);
	
	/**
	 * Adds a {@code Course} to this {@code User}'s schedule in the specified {@code Block}s.
	 * 
	 * @param blocks The {@code Block}s to add the {@code Course} to.
	 * @param course The {@code Course} to add.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in this {@code User}'s schedule.
	 */
	public boolean addCourse(Block[] blocks, Course course);
	
	/**
	 * Adds a {@code Course} to this {@code User}'s schedule in {@code Block}s with the specified blockids.
	 * 
	 * @param blocks The blockids to add the {@code Course} to.
	 * @param course The {@code Course} to add.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified blockids is already filled with another {@code Course} in this {@code User}'s schedule.
	 */
	public boolean addCourse(Integer[] blocks, Course course);
	
	/**
	 * Removes a {@code Course} from this {@code User}'s schedule entirely.
	 * 
	 * @param course The {@code Course} to remove.
	 */
	public void removeCourse(Course course);
	
	/**
	 * Updates a {@code Course} in this {@code User}'s schedule.
	 * Adds the {@code Course} to the specified {@code Block}s and removes the {@code Course} from any {@code Block} that is not specified.
	 * 
	 * @param newBlocks The updated {@code Block}s for the {@code Course}.
	 * @param course The {@code Course} to update.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in this {@code User}'s schedule.
	 */
	public default boolean updateCourse(Block[] newBlocks, Course course) {
		removeCourse(course);
		return addCourse(newBlocks, course);
	}
	
	/**
	 * Retrieves the user's {@code Assignment}s for a particular {@code Block} on a particular {@code Date}.
	 * 
	 * @param date The {@code Date} to query.
	 * @param block The {@code Block} to query.
	 * @param onDue {@code true} if querying by due date, {@code false} if querying by assigned date.
	 * @return The {@code Assignment}s which the user has for the specified {@code Date} and {@code Block} or {@code null} if there are no such {@code Assignment}s.
	 */
	public default ArrayList<Assignment> getAssignments(Date date, Block block, boolean onDue) {
		Course course = getCourse(block);
		return course == null ? null : new ArrayList<Assignment>(onDue ? course.getDue(date) : course.getAssigned(date));
	}
	
	/**
	 * Adds an {@code Assignment} to the specified {@code Course} in this {@code User}'s schedule.
	 * 
	 * @param course The {@code Course} to add the {@code Assignment} to.
	 * @param assignment The {@code Assignment} to add.
	 */
	public default void addAssignment(Course course, Assignment assignment) {
		course.addAssignment(assignment);
	}
	
}
