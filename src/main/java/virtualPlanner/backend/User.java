package virtualPlanner.backend;

import virtualPlanner.reference.Blocks;

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
	 * Retrieves the course which this {@code User} has in a particular block.
	 * 
	 * @param block The block to find the course for.
	 * @return The {@code Course} associated with the specified block, or {@code null} if this {@code User} has no {@code Course} for that block.
	 */
	public Course getCourse(Blocks block);
	
}
