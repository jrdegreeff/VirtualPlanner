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
	 * @param block The type of this {@code Block} from {@code Blocks}.
	 * @param start The start time of this {@code Block}.
	 * @param end The end time of this {@code Block}.
	 */
	public Block(Blocks block, Time start, Time end) {
		this.block = block;
		this.start = start;
		this.end = end;
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
	
}
