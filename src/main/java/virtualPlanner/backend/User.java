package virtualPlanner.backend;

import virtualPlanner.util.Block;

/**
 * Defines a user with a block schedule.
 * 
 * @author JeremiahDeGreeff
 */
public interface User {
	
	/**
	 * @return The name of the user.
	 */
	public String getName();
	
	/**
	 * Retrieves the course which this {@code User} has in a particular {@code Block}.
	 * 
	 * @param block The {@code Block} to find the course for.
	 * @return The {@code Course} associated with the specified {@code Block}, or {@code null} if this {@code User} has no {@code Course} for that {@code Block}.
	 */
	public Course getCourse(Block block);
	
}
