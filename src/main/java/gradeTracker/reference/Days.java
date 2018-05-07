package gradeTracker.reference;

import gradeTracker.util.Time;

/**
 * An enum which holds the block information for each day in a normal weekly Middlesex schedule.
 * 
 * @author JeremiahDeGreeff
 */
public enum Days {
	
	MONDAY(new Block[] {
			new Block(Blocks.C, new Time(8, 00), new Time(8, 40)),
			new Block(Blocks.F, new Time(8, 45), new Time(9, 25)),
			new Block(Blocks.MEETING, new Time(9, 30), new Time(10, 00)),
			new Block(Blocks.D, new Time(10, 00), new Time(10, 40)),
			new Block(Blocks.E, new Time(10, 45), new Time(11, 25)),
			new Block(Blocks.G, new Time(11, 30), new Time(12, 10)),
			new Block(Blocks.LUNCH, new Time(12, 10), new Time(12, 55)),
			new Block(Blocks.B, new Time(12, 55), new Time(13, 35)),
			new Block(Blocks.A, new Time(13, 40), new Time(14, 20)),
			new Block(Blocks.L, new Time(14, 20), new Time(15, 00))
		}),
	TUESDAY(new Block[] {
			new Block(Blocks.D, new Time(8, 00), new Time(8, 40)),
			new Block(Blocks.A, new Time(8, 45), new Time(9, 25)),
			new Block(Blocks.ASSEMBLY, new Time(9, 30), new Time(10, 10)),
			new Block(Blocks.C, new Time(10, 15), new Time(10, 55)),
			new Block(Blocks.B, new Time(11, 00), new Time(11, 40)),
			new Block(Blocks.H, new Time(11, 45), new Time(12, 25)),
			new Block(Blocks.LUNCH, new Time(12, 25), new Time(13, 05)),
			new Block(Blocks.F, new Time(13, 05), new Time(13, 45)),
			new Block(Blocks.E, new Time(13, 50), new Time(14, 30)),
			new Block(Blocks.L, new Time(14, 30), new Time(15, 10))
		}),
	WEDNESDAY(new Block[] {
			new Block(Blocks.B, new Time(8, 00), new Time(8, 40)),
			new Block(Blocks.C, new Time(8, 45), new Time(9, 25)),
			new Block(Blocks.CHAPEL, new Time(9, 30), new Time(10, 10)),
			new Block(Blocks.A, new Time(10, 15), new Time(10, 55)),
			new Block(Blocks.F, new Time(11, 00), new Time(11, 40)),
			new Block(Blocks.G, new Time(11, 45), new Time(12, 25)),
			new Block(Blocks.LUNCH, new Time(12, 25), new Time(13, 00))
		}),
	THURSDAY(new Block[] {
			new Block(Blocks.E, new Time(9, 15), new Time(9, 55)),
			new Block(Blocks.D, new Time(10, 00), new Time(10, 40)),
			new Block(Blocks.H, new Time(10, 45), new Time(11, 25)),
			new Block(Blocks.F, new Time(11, 30), new Time(12, 10)),
			new Block(Blocks.LUNCH, new Time(12, 10), new Time(12, 55)),
			new Block(Blocks.B, new Time(12, 55), new Time(13, 35)),
			new Block(Blocks.C, new Time(13, 40), new Time(14, 20)),
			new Block(Blocks.L, new Time(14, 20), new Time(15, 00))
		}),
	FRIDAY(new Block[] {
			new Block(Blocks.F, new Time(8, 00), new Time(8, 40)),
			new Block(Blocks.E, new Time(8, 45), new Time(9, 25)),
			new Block(Blocks.SENATE, new Time(9, 30), new Time(10, 00)),
			new Block(Blocks.B, new Time(10, 00), new Time(10, 40)),
			new Block(Blocks.C, new Time(10, 45), new Time(11, 25)),
			new Block(Blocks.G, new Time(11, 30), new Time(12, 10)),
			new Block(Blocks.LUNCH, new Time(12, 10), new Time(12, 55)),
			new Block(Blocks.A, new Time(12, 55), new Time(13, 35)),
			new Block(Blocks.D, new Time(13, 40), new Time(14, 20)),
			new Block(Blocks.L, new Time(14, 20), new Time(15, 00))
		}),
	SATURDAY(new Block[] {
			new Block(Blocks.A, new Time(8, 00), new Time(8, 40)),
			new Block(Blocks.H, new Time(8, 45), new Time(9, 25)),
			new Block(Blocks.ASSEMBLY, new Time(9, 30), new Time(10, 00)),
			new Block(Blocks.E, new Time(10, 00), new Time(10, 40)),
			new Block(Blocks.D, new Time(10, 45), new Time(11, 25)),
			new Block(Blocks.LUNCH, new Time(11, 30), new Time(12, 10))
		}),
	SUNDAY(new Block[] {});
	
	/**
	 * The blocks which occur on this day in a normal Middlesex Schedule.
	 */
	private final Block[] blocks;
	
	/**
	 * @param blocks The blocks which occur on this day in a normal Middlesex Schedule.
	 */
	Days(Block[] blocks) {
		this.blocks = blocks;
	}
	
	/**
	 * @return The number of blocks in this day.
	 */
	public int getBlockCount() {
		return blocks.length;
	}
	
	/**
	 * Retrieves a particular block in this day.
	 * 
	 * @param n The index of the block (0-indexed).
	 * @return The element of {@code Blocks} which the {@code n}-th block of this day corresponds to.
	 * @throws ArrayIndexOutOfBoundsException If {@code n} exceeds the valid indices of blocks for this day.
	 */
	public Blocks getBlock(int n) {
		return blocks[n].getBlock();
	}
	
	/**
	 * Retrieves the start time of a particular block in this day.
	 * 
	 * @param n The index of the block (0-indexed).
	 * @return The start time of the {@code n}-th block of this day.
	 * @throws ArrayIndexOutOfBoundsException If {@code n} exceeds the valid indices of blocks for this day.
	 */
	public Time getBlockStartTime(int n) {
		return blocks[n].getStartTime();
	}
	
	/**
	 * Retrieves the end time of a particular block in this day.
	 * 
	 * @param n The index of the block (0-indexed).
	 * @return The end time of the {@code n}-th block of this day.
	 * @throws ArrayIndexOutOfBoundsException If {@code n} exceeds the valid indices of blocks for this day.
	 */
	public Time getBlockEndTime(int n) {
		return blocks[n].getEndTime();
	}
	
	/**
	 * Retrieves a day based on its ordinality where Sunday is given ordinality 1 and Saturday is given ordinality 7.
	 * 
	 * @param ordinal The desired day's ordinality [1, 7].
	 * @return The specified day.
	 * @throws ArrayIndexOutOfBoundsException If {@code ordinal} is not in the range [1, 7].
	 */
	public static Days getDay(int ordinal) {
		return Days.values()[(ordinal + 5) % 7];
	}
	
	/**
	 * Internal class for storing each block with its start and end times.
	 * 
	 * @author JeremiahDeGreeff
	 */
	private static class Block {
		
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
	
}
