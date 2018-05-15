package virtualPlanner.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import virtualPlanner.util.Date;

/**
 * The Course class maps dates to a TreeSet of Assignment objects.
 * @author aldai
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
	private Map<Date, Set<Assignment>> assigned;
	
	/**
	 * maps Dates to a set of all Assignments due that day
	 */
	private Map<Date, Set<Assignment>> due;
	
	/**
	 * Constructor for Course class. assigned and due maps are set to empty HashMaps.
	 * @param name
	 * @param teacher
	 */
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
		assigned = new HashMap<Date, Set<Assignment>>();
		due = new HashMap<Date, Set<Assignment>>();
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
		return due.get(dateDue);
	}
	
	/** 
	 * @param dateAssigned
	 * @return TreeSet of Assignments assigned on a given date.
	 */
	public Set<Assignment> getAssigned(Date dateAssigned) {
		return assigned.get(dateAssigned);
	}
	
	/**
	 * addAssignment adds the given Assignment object to the Assigned HashMap
	 * and the Due HashMap by respective dates.
	 * @param hw
	 */
	public void addAssignment(Assignment hw) {
		Date dateDue = hw.getDue();
		Date dateAssigned = hw.getAssignedDate();
		
		if (!due.containsKey(dateDue)) {
			due.put(dateDue, new TreeSet<Assignment>());
		}
		due.get(dateDue).add(hw);
		
		if (!assigned.containsKey(dateAssigned)) {
			assigned.put(dateAssigned, new TreeSet<Assignment>());
		}
		assigned.get(dateAssigned).add(hw);
	}
	
	/**
	 * @return true if two courses have the same course name and teacher.
	 */
	public boolean equals(Course other) {
		return name.equals(other.getCourseName()) && teacher.equals(other.getTeacher());
	}
	
	/**
	 * @return course name taught by teacher
	 */
	public String toString() {
		return name + " taught by " + teacher;
	}
}
