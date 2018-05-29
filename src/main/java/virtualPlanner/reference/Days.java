package virtualPlanner.reference;

import virtualPlanner.util.Block;
import virtualPlanner.util.Date;
import virtualPlanner.util.Time;

/**
 * An enum which holds the block information for each day in a normal weekly Middlesex schedule.
 * 
 * @author JeremiahDeGreeff
 */
public enum Days {
	
	MONDAY("Monday", new Block[] {
			new Block(Blocks.C, new Time(8, 00), new Time(8, 40), 0x20),
			new Block(Blocks.F, new Time(8, 45), new Time(9, 25), 0x50),
			new Block(Blocks.MEETING, new Time(9, 30), new Time(10, 00), 0xA0),
			new Block(Blocks.D, new Time(10, 00), new Time(10, 40), 0x30),
			new Block(Blocks.E, new Time(10, 45), new Time(11, 25), 0x40),
			new Block(Blocks.G, new Time(11, 30), new Time(12, 10), 0x60),
			new Block(Blocks.LUNCH, new Time(12, 10), new Time(12, 55), 0x90),
			new Block(Blocks.B, new Time(12, 55), new Time(13, 35), 0x10),
			new Block(Blocks.A, new Time(13, 40), new Time(14, 20), 0x00),
			new Block(Blocks.L, new Time(14, 20), new Time(15, 00), 0x80)
		}),
	TUESDAY("Tuesday", new Block[] {
			new Block(Blocks.D, new Time(8, 00), new Time(8, 40), 0x31),
			new Block(Blocks.A, new Time(8, 45), new Time(9, 25), 0x01),
			new Block(Blocks.ASSEMBLY, new Time(9, 30), new Time(10, 10), 0xB1),
			new Block(Blocks.C, new Time(10, 15), new Time(10, 55), 0x21),
			new Block(Blocks.B, new Time(11, 00), new Time(11, 40), 0x11),
			new Block(Blocks.H, new Time(11, 45), new Time(12, 25), 0x71),
			new Block(Blocks.LUNCH, new Time(12, 25), new Time(13, 05), 0x91),
			new Block(Blocks.F, new Time(13, 05), new Time(13, 45), 0x51),
			new Block(Blocks.E, new Time(13, 50), new Time(14, 30), 0x41),
			new Block(Blocks.L, new Time(14, 30), new Time(15, 10), 0x81)
		}),
	WEDNESDAY("Wednesday", new Block[] {
			new Block(Blocks.B, new Time(8, 00), new Time(8, 40), 0x12),
			new Block(Blocks.C, new Time(8, 45), new Time(9, 25), 0x22),
			new Block(Blocks.CHAPEL, new Time(9, 30), new Time(10, 10), 0xC2),
			new Block(Blocks.A, new Time(10, 15), new Time(10, 55), 0x02),
			new Block(Blocks.F, new Time(11, 00), new Time(11, 40), 0x52),
			new Block(Blocks.G, new Time(11, 45), new Time(12, 25), 0x62),
			new Block(Blocks.LUNCH, new Time(12, 25), new Time(13, 00), 0x92)
		}),
	THURSDAY("Thursday", new Block[] {
			new Block(Blocks.FACULTY, new Time(8, 00), new Time(8, 55), 0xD3),
			new Block(Blocks.E, new Time(9, 15), new Time(9, 55), 0x43),
			new Block(Blocks.D, new Time(10, 00), new Time(10, 40), 0x33),
			new Block(Blocks.H, new Time(10, 45), new Time(11, 25), 0x73),
			new Block(Blocks.F, new Time(11, 30), new Time(12, 10), 0x53),
			new Block(Blocks.LUNCH, new Time(12, 10), new Time(12, 55), 0x93),
			new Block(Blocks.B, new Time(12, 55), new Time(13, 35), 0x13),
			new Block(Blocks.C, new Time(13, 40), new Time(14, 20), 0x23),
			new Block(Blocks.L, new Time(14, 20), new Time(15, 00), 0x83)
		}),
	FRIDAY("Friday", new Block[] {
			new Block(Blocks.F, new Time(8, 00), new Time(8, 40), 0x54),
			new Block(Blocks.E, new Time(8, 45), new Time(9, 25), 0x44),
			new Block(Blocks.SENATE, new Time(9, 30), new Time(10, 00), 0xE4),
			new Block(Blocks.B, new Time(10, 00), new Time(10, 40), 0x14),
			new Block(Blocks.C, new Time(10, 45), new Time(11, 25), 0x24),
			new Block(Blocks.G, new Time(11, 30), new Time(12, 10), 0x64),
			new Block(Blocks.LUNCH, new Time(12, 10), new Time(12, 55), 0x94),
			new Block(Blocks.A, new Time(12, 55), new Time(13, 35), 0x04),
			new Block(Blocks.D, new Time(13, 40), new Time(14, 20), 0x34),
			new Block(Blocks.L, new Time(14, 20), new Time(15, 00), 0x84)
		}),
	SATURDAY("Saturday", new Block[] {
			new Block(Blocks.A, new Time(8, 00), new Time(8, 40), 0x05),
			new Block(Blocks.H, new Time(8, 45), new Time(9, 25), 0x75),
			new Block(Blocks.ASSEMBLY, new Time(9, 30), new Time(10, 00), 0xB5),
			new Block(Blocks.E, new Time(10, 00), new Time(10, 40), 0x45),
			new Block(Blocks.D, new Time(10, 45), new Time(11, 25), 0x35),
			new Block(Blocks.LUNCH, new Time(11, 30), new Time(12, 10), 0x95)
		}),
	SUNDAY("Sunday", new Block[] {});
	
	/**
	 * The name of this day.
	 */
	private final String name;
	
	/**
	 * The blocks which occur on this day in a normal Middlesex Schedule.
	 */
	private final Block[] blocks;
	
	/**
	 * @param name The name of this day.
	 * @param blocks The blocks which occur on this day in a normal Middlesex Schedule.
	 */
	Days(String name, Block[] blocks) {
		this.name = name;
		this.blocks = blocks;
	}
	
	/**
	 * @return The number of blocks in this day.
	 */
	public int getBlockCount() {
		return blocks.length;
	}
	
	/**
	 * @return The name of this day.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retrieves a particular {@code Block} in this day.
	 * 
	 * @param n The index of the {@code Block} (0-indexed).
	 * @return The {@code n}-th {@code Block} of this day.
	 * @throws ArrayIndexOutOfBoundsException If {@code n} exceeds the valid indices of blocks for this day.
	 */
	public Block getBlock(int n) {
		return blocks[n];
	}
	
	/**
	 * Retrieves the all the {@code Block}s which occur on a particular {@code Date}.
	 * 
	 * @param date The {@code Date} to find {@code Block}s for.
	 * @return All the {@code Block}s which occur on the specified {@code Date}.
	 */
	public static Block[] getBlocksOnDay(Date date) {
		return date.getDayOfWeek().blocks;
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
	
}
