package virtualPlanner.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

import virtualPlanner.util.Date;

/**
 * The Course class maps dates to a TreeSet of Assignment objects.
 * @author aldai
 * 
 */
public class Course {
	
	private String name, teacher;
	private Map<Date, TreeSet<Assignment>> assigned;
	private Map<Date, TreeSet<Assignment>> due;
	
	/**
	 * Constructor for Course class. assigned and due maps are set to empty HashMaps.
	 * @param name
	 * @param teacher
	 */
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
		assigned = new HashMap<Date, TreeSet<Assignment>>();
		due = new HashMap<Date, TreeSet<Assignment>>();
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
	public TreeSet<Assignment> getDue(Date dateDue) {
		return due.get(dateDue);
	}
	
	/** 
	 * @param dateAssigned
	 * @return TreeSet of Assignments assigned on a given date.
	 */
	public TreeSet<Assignment> getAssigned(Date dateAssigned) {
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
			TreeSet<Assignment> homework = new TreeSet<Assignment>();
			homework.add(hw);
			due.put(dateDue, homework);
		}
		else {
			due.get(dateDue).add(hw);
		}
		
		if (!assigned.containsKey(dateAssigned)) {
			TreeSet<Assignment> homework = new TreeSet<Assignment>();
			homework.add(hw);
			assigned.put(dateAssigned, homework);
		}
		else {
			assigned.get(dateAssigned).add(hw);
		}
	}
	
	public boolean equals() {
		return name.equals(name) && teacher.equals(teacher);
	}
	
	public String toString() {
		return name + " taught by " + teacher;
	}
	
	public int hashCode() {
		
	}
}
