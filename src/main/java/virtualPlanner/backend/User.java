package virtualPlanner.backend;

import virtualPlanner.util.Block;

/**
 * Defines a user with a block schedule.
 * 
 * @author JeremiahDeGreeff
 */
public interface User {
	
	/**
	 * @return The name of the this {@code User}.
	 */
	public String getName();
	
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
	 * Removes a {@code Course} from this {@code User}'s schedule entirely.
	 * 
	 * @param course The {@code Course} to remove.
	 */
	public void removeCourse(Course course);
	
	/**
	 * Updates a {@code Course} in this {@code User}'s schedule.
	 * Adds the {@code Course} to the specified {@code Block}s and removes the {@code Course} from any {@code Block} that is not specified.
	 * 
	 * @param blocks The updated {@code Block}s for the {@code Course}.
	 * @param course The {@code Course} to update.
	 * @return {@code true} if the operation was successful; {@code false} if a conflict occurs because one or more of the specified {@code Block}s is already filled with another {@code Course} in this {@code User}'s schedule.
	 */
	public default boolean update(Block[] blocks, Course course) {
		removeCourse(course);
		return addCourse(blocks, course);
	}
	
}
