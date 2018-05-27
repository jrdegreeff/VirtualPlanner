package virtualPlanner.util;

import virtualPlanner.reference.Blocks;

/**
 * Represents a block with start and end time.
 * 
 * @author JeremiahDeGreeff
 */
public class Block {
	
	/**
	 * The type of this {@code Block} from {@code Blocks}.
	 */
	private Blocks block;
	/**
	 * The start time of this {@code Block}.
	 */
	private Time start;
	/**
	 * The end time of this {@code Block}.
	 */
	private Time end;
	/**
	 * Used for identifying the block for integration with the database and for special schedules.
	 */
	private int id;
	
	/**
	 * @param block The type of this {@code Block} from {@code Blocks}.
	 * @param start The start time of this {@code Block}.
	 * @param end The end time of this {@code Block}.
	 * @param id Used for identifying the block for integration with the database and for special schedules.
	 */
	public Block(Blocks block, Time start, Time end, int id) {
		this.block = block;
		this.start = start;
		this.end = end;
		this.id = id;
	}
	
	/**
	 * @return The type of this {@code Block} from {@code Blocks}.
	 */
	public Blocks getBlock() {
		return block;
	}
	
	/**
	 * @return The start time of this {@code Block}.
	 */
	public Time getStartTime() {
		return start;
	}
	
	/**
	 * @return The end time of this {@code Block}.
	 */
	public Time getEndTime() {
		return end;
	}
	
	/**
	 * @return The id of this block which is used for integration with the database and for special schedules.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns a String representation of this {@code Block}.
	 */
	@Override
	public String toString() {
		return block.getFullName() + " [" + start + "-" + end + "] {id = " + id + "}";
	}
	
	/**
	 * Indicates whether another {@code Object} is equal to this one.
	 * 
	 * The {@code Object}s are considered equal if they are both instances of {@code Block} and satisfy either of the following:
	 * <ul>
	 * <li>both references point to the same {@code Object}</li>
	 * <li>both {@code Object}s have the same block, start, and end fields</li>
	 * </ul>
	 */
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Block && (this == o || this.block == ((Block) o).block && this.start == ((Block) o).start && this.end == ((Block) o).end);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
}
