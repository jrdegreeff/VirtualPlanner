package virtualPlanner.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import virtualPlanner.util.Date;

/**
 * 
 * @author aldai
 * The Course class maps dates to an ArrayList of assignments.
 */
public class Course {
	
	private String name, teacher;
	private Map<Date, PriorityQueue<Assignment>> assigned;
	private Map<Date, PriorityQueue<Assignment>> due;
	
	// maps dates to an arraylist of assignments, order of arrival
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
		assigned = new HashMap<Date, PriorityQueue<Assignment>>();
		due = new HashMap<Date, PriorityQueue<Assignment>>();
	}
	
	// return course name
	public String getCourseName() {
		return name;
	}
	
	// return teacher name
	public String getTeacher() {
		return teacher;
	}
	
	// returns ArrayList of assignments due on a specific date
	public PriorityQueue<Assignment> getDue(Date dateDue) {
		return due.get(dateDue);
	}
	
	// returns ArrayList of assignments assigned on a specific date
	public PriorityQueue<Assignment> getAssigned(Date dateAssigned) {
		return assigned.get(dateAssigned);
	}
	
	// add assignments by due date
	public void addAssignment(Assignment hw) {
		
		Date dateDue = hw.getDue();
		Date dateAssigned = hw.getAssignedDate();
		
		if (!due.containsKey(dateDue)) {
			PriorityQueue<Assignment> homework = new PriorityQueue<Assignment>();
			homework.add(hw);
			due.put(dateDue, homework);
		}
		else {
			due.get(dateDue).add(hw);
		}
		
		if (!assigned.containsKey(dateAssigned)) {
			PriorityQueue<Assignment> homework = new PriorityQueue<Assignment>();
			homework.add(hw);
			assigned.put(dateAssigned, homework);
		}
		else {
			assigned.get(dateAssigned).add(hw);
		}
	}
}
