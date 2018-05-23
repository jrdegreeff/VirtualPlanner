package virtualPlanner.reference;

/**
 * An enum which holds all possible types of assignments.
 * 
 * @author JeremiahDeGreeff
 */
public enum AssignmentTypes {
	
	HOMEWORK("Homework", 4),
	TEST("Test", 0),
	QUIZ("Quiz", 1),
	ESSAY("Essay", 3),
	PROJECT("Project", 2);
	
	/**
	 * The name of this type of assignment.
	 */
	private final String name;
	/**
	 * The priority of this type of assignment.
	 */
	private final int priority;
	
	/**
	 * @param name The name of this type of assignment.
	 * @param priority The priority of this type of assignment.
	 */
	AssignmentTypes(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}
	
	/**
	 * @return The name of this type of assignment.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The priority of this type of assignment.
	 */
	public int getPriority() {
		return priority;
	}
	
}
