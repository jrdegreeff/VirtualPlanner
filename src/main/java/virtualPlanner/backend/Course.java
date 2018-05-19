package virtualPlanner.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import virtualPlanner.util.Date;

/**
 * The Course class maps dates to a TreeSet of Assignment objects.
 * @author aldai
 * @author Leo (changeAssignedDate and changeDueDate)
 * 
 */
public class Course {

	/**
	 * course name and teacher
	 */
	private String name, teacher;

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
	 * @param name
	 * @param teacher
	 */
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
		assnDateMap = new HashMap<Date, Set<Assignment>>();
		dueDateMap = new HashMap<Date, Set<Assignment>>();
	}

	/**
	 * @return course name
	 */
	public String getCourseName() {
		return name;
	}

	/**
	 * @return teacher name
	 */
	public String getTeacher() {
		return teacher;
	}

	/**
	 * @param dateDue
	 * @return TreeSet of Assignments due on a given date.
	 */
	public Set<Assignment> getDue(Date dateDue) {
		return dueDateMap.get(dateDue);
	}

	/** 
	 * @param dateAssigned
	 * @return TreeSet of Assignments assigned on a given date.
	 */
	public Set<Assignment> getAssigned(Date dateAssigned) {
		return assnDateMap.get(dateAssigned);
	}

	/**
	 * addAssignment adds the given Assignment object to the Assigned HashMap
	 * and the Due HashMap by respective dates.
	 * @param hw
	 */
	public void addAssignment(Assignment hw) {
		Date dateDue = hw.getDue();
		Date dateAssigned = hw.getAssignedDate();
		
		if (!dueDateMap.containsKey(dateDue)) {
			dueDateMap.put(dateDue, new TreeSet<Assignment>());
		}
		dueDateMap.get(dateDue).add(hw);
		
		if (!assnDateMap.containsKey(dateAssigned)) {
			assnDateMap.put(dateAssigned, new TreeSet<Assignment>());
		}
		assnDateMap.get(dateAssigned).add(hw);
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
	 * @return true if two courses have the same course name and teacher.
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(!(other instanceof Course))
			return false;
		return name.equals(((Course) other).getCourseName()) && teacher.equals(((Course) other).getTeacher());
	}

	/**
	 * @return course name taught by teacher
	 */
	@Override
	public String toString() {
		return name + " taught by " + teacher;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
