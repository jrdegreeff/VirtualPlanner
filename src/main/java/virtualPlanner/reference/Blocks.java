package virtualPlanner.reference;

/**
 * An enum which holds all the blocks in the weekly Middlesex Schedule.
 * 
 * @author JeremiahDeGreeff
 */
public enum Blocks {
	
	A("A Block", "A", true),
	B("B Block", "B", true),
	C("C Block", "C", true),
	D("D Block", "D", true),
	E("E Block", "E", true),
	F("F Block", "F", true),
	G("G Block", "G", true),
	H("H Block", "H", true),
	L("L Block", "L", true),
	MEETING("Meeting Block", "MTNG", false),
	ASSEMBLY("Assembly", "ASSEM", false),
	CHAPEL("Chapel", "CHAPEL", false),
	SENATE("Senate", "SENATE", false),
	LUNCH("Lunch", "LUNCH", false);
	
	/**
	 * The full name of the block.
	 */
	private final String fullName;
	/**
	 * An abbreviation for the block.
	 */
	private final String abbreviation;
	/**
	 * Whether the block is a class.
	 */
	private final boolean isClass;
	
	/**
	 * @param fullName The full name of the block.
	 * @param abbreviation An abbreviation for the block.
	 * @param isClass Whether the block is a class.
	 */
	Blocks(String fullName, String abbreviation, boolean isClass) {
		this.fullName = fullName;
		this.abbreviation = abbreviation;
		this.isClass = isClass;
	}
	
	/**
	 * @return The full name of the block.
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * @return An abbreviation for the block.
	 */
	public String getAbbreviation() {
		return abbreviation;
	}
	
	/**
	 * @return Whether the block is a class.
	 */
	public boolean isClass() {
		return isClass;
	}
	
}
