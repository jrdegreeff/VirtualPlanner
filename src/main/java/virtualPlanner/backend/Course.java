package virtualPlanner.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import virtualPlanner.util.Date;

/**
 * The Course class maps dates to a TreeSet of Assignment objects.
 * 
 * @author aldai
 * @author Leo
 * @author JeremiahDeGreeff
 */
public class Course {
	
	/**
	 * The unique id of this {@code Course} as it is identified in the database.
	 */
	private final int id;
	
	/**
	 * course name, abbreviation, and teacher
	 */
	private String name, abbrev, teacher;

	/**
	 * maps Dates to a set of all Assignments assigned that day
	 */
	private Map<Date, Set<Assignment>> assnDateMap;

	/**
	 * maps Dates to a set of all Assignments due that day
	 */
	private Map<Date, Set<Assignment>> dueDateMap;
	
	/**
	 * Constructor for Course class. assigned and due maps are set to empty HashMaps.
	 * 
	 * @param id The id of the new {@code Course}. Must be an id given from the database to avoid overwritting records.
	 * @param name
	 * @param teacher
	 */
	public Course(int id, String name, String abbreviation, String teacher) {
		this.id = id;
		this.name = name;
		this.abbrev = abbreviation;
		this.teacher = teacher;
		assnDateMap = new HashMap<Date, Set<Assignment>>();
		dueDateMap = new HashMap<Date, Set<Assignment>>();
	}

	/**
	 * @return unique id for this course object
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * @return course name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return teacher name
	 */
	public String getTeacher() {
		return teacher;
	}
	
	/**
	 * @return abbreviation
	 */
	public String getAbbreviation() {
		return abbrev;
	}
	
	/**
	 * Changes the name of this {@code Course}.
	 * 
	 * @param newName The new name.
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Changes the teacher for this {@code Course}.
	 * 
	 * @param newTeacher The new teacher.
	 */
	public void setTeacher(String newTeacher) {
		this.teacher = newTeacher;
	}
	
	/**
	 * Changes the abbreviation for this {@code Course}.
	 * 
	 * @param newAbbrev The new abbreviation.
	 */
	public void setAbbreviation(String newAbbrev) {
		this.abbrev = newAbbrev;
	}

	/**
	 * @param dateDue
	 * @return TreeSet of Assignments due on a given date.
	 */
	public Set<Assignment> getDue(Date dateDue) {
		Set<Assignment> assignments = dueDateMap.get(dateDue);
		return assignments == null ? new TreeSet<Assignment>() : assignments;
	}

	/** 
	 * @param dateAssigned
	 * @return TreeSet of Assignments assigned on a given date.
	 */
	public Set<Assignment> getAssigned(Date dateAssigned) {
		Set<Assignment> assignments = assnDateMap.get(dateAssigned);
		return assignments == null ? new TreeSet<Assignment>() : assignments;
	}

	/**
	 * Adds the given Assignment object to both dueDateMap and assignedDateMap.
	 * @param assn assignment to be added
	 */
	public void addAssignment(Assignment assn) {
		Date dateDue = assn.getDue();
		Date dateAssigned = assn.getAssignedDate();
		
		// add to due date map
		if (!dueDateMap.containsKey(dateDue)) {
			dueDateMap.put(dateDue, new TreeSet<Assignment>());
		}
		dueDateMap.get(dateDue).add(assn);
		
		// add to assigned date map
		if (!assnDateMap.containsKey(dateAssigned)) {
			assnDateMap.put(dateAssigned, new TreeSet<Assignment>());
		}
		assnDateMap.get(dateAssigned).add(assn);
	}
	
	/**
	 * Removes the given Assignment object in both dueDateMap and assignedDateMap.
	 * @param assn assignment to be removed
	 */
	public void removeAssignment(Assignment assn) {
		Date dateDue = assn.getDue();
		Date dateAssigned = assn.getAssignedDate();
		
		dueDateMap.get(dateDue).remove(assn);
		assnDateMap.get(dateAssigned).remove(assn);
	}

	/**
	 * Change the assigned date for a particular assignment. 
	 * @param assn assignment
	 * @param newAssnDate new assigned date
	 */
	public void changeAssignedDate(Assignment assn, Date newAssnDate) {
		// update assigned date in assignment
		Date oldAssnDate = assn.changeAssignedDate(newAssnDate);

		// ArrayList of assignment on old assignment date
		Set<Assignment> oldAssignments = assnDateMap.get(oldAssnDate); // old assignments set for old date
		oldAssignments.remove(assn);
		Set<Assignment> newAssignments = assnDateMap.get(newAssnDate); // new assignments set for new date
		newAssignments.add(assn);
		
		assnDateMap.put(newAssnDate, newAssignments);
	}
	
	
	/**
	 * Change the due date for a particular assignment. 
	 * @param assn assignment
	 * @param newDueDate new due date
	 */
	public void changeDueDate(Assignment assn, Date newDueDate) {
		// update assigned date in assignment
		Date oldDueDate = assn.changeDueDate(newDueDate);

		// ArrayList of assignment on old assignment date
		Set<Assignment> oldAssignments = assnDateMap.get(oldDueDate); // old assignments set for old date
		oldAssignments.remove(assn);
		Set<Assignment> newAssignments = assnDateMap.get(newDueDate); // new assignments set for new date
		newAssignments.add(assn);
		
		assnDateMap.put(newDueDate, newAssignments);
	}
	
	/**
	 * Returns a String representation of this {@code Course}.
	 */
	@Override
	public String toString() {
		return name + " (" + abbrev + ") taught by " + teacher + " [number of assignments: " + dueDateMap.size() + "] {id = " + id + "}";
	}

	/**
	 * Returns {@code true} if {@code other} is a {@code Course} with the same id as this {@code Course}, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof Course && this.id == ((Course) other).id;
	}
	
	@Override
	public int hashCode() {
		return ("" + id).hashCode();
	}
}
