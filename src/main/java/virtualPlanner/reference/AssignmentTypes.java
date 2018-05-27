package virtualPlanner.reference;

/**
 * An enum which holds all possible types of assignments.
 * 
 * @author JeremiahDeGreeff
 */
public enum AssignmentTypes {
	
	TEST("Test", 0, 1),
	QUIZ("Quiz", 1, 2),
	PROJECT("Project", 2, 3),
	ESSAY("Essay", 3, 4),
	HOMEWORK("Homework", 4, 5);
	
	/**
	 * The name of this type of assignment.
	 */
	private final String name;
	/**
	 * The priority of this type of assignment.
	 */
	private final int priority;
	/**
	 * The unique id of this type of assignment for reference in the database.
	 */
	private final int id;
	
	/**
	 * @param name The name of this type of assignment.
	 * @param priority The priority of this type of assignment.
	 */
	AssignmentTypes(String name, int priority, int id) {
		this.name = name;
		this.priority = priority;
		this.id = id;
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
	
	/**
	 * @return The unique id of this type of assignment for reference in the database.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Retrieves the assignment type with a particular id.
	 * 
	 * @param id The id to search for.
	 * @return The specified type or {@code null} no such type exists.
	 */
	public static AssignmentTypes getTypeFromID(int id) {
		for(AssignmentTypes type : values())
			if(type.id == id)
				return type;
		return null;
	}
	
}
