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
<<<<<<< HEAD
	private Map<Date, TreeSet<Assignment>> assnDateMap;

	/**
	 * maps Dates to a set of all Assignments due that day
	 */
	private Map<Date, TreeSet<Assignment>> dueDateMap;

=======
	private Map<Date, Set<Assignment>> assigned;
	
	/**
	 * maps Dates to a set of all Assignments due that day
	 */
	private Map<Date, Set<Assignment>> due;
	
>>>>>>> 2fd0d907b4ad450f360ceed7f61050ff6dc34273
	/**
	 * Constructor for Course class. assigned and due maps are set to empty HashMaps.
	 * @param name
	 * @param teacher
	 */
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
<<<<<<< HEAD
		assnDateMap = new HashMap<Date, TreeSet<Assignment>>();
		dueDateMap = new HashMap<Date, TreeSet<Assignment>>();
=======
		assigned = new HashMap<Date, Set<Assignment>>();
		due = new HashMap<Date, Set<Assignment>>();
>>>>>>> 2fd0d907b4ad450f360ceed7f61050ff6dc34273
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
<<<<<<< HEAD
	public TreeSet<Assignment> getDue(Date dateDue) {
		return dueDateMap.get(dateDue);
=======
	public Set<Assignment> getDue(Date dateDue) {
		return due.get(dateDue);
>>>>>>> 2fd0d907b4ad450f360ceed7f61050ff6dc34273
	}

	/** 
	 * @param dateAssigned
	 * @return TreeSet of Assignments assigned on a given date.
	 */
<<<<<<< HEAD
	public TreeSet<Assignment> getAssigned(Date dateAssigned) {
		return assnDateMap.get(dateAssigned);
=======
	public Set<Assignment> getAssigned(Date dateAssigned) {
		return assigned.get(dateAssigned);
>>>>>>> 2fd0d907b4ad450f360ceed7f61050ff6dc34273
	}

	/**
	 * addAssignment adds the given Assignment object to the Assigned HashMap
	 * and the Due HashMap by respective dates.
	 * @param hw
	 */
	public void addAssignment(Assignment hw) {
<<<<<<< HEAD

		Date dateDue = hw.getDue();
		Date dateAssigned = hw.getAssignedDate();

		if (!dueDateMap.containsKey(dateDue)) {
			TreeSet<Assignment> homework = new TreeSet<Assignment>();
			homework.add(hw);
			dueDateMap.put(dateDue, homework);
		}
		else {
			dueDateMap.get(dateDue).add(hw);
		}

		if (!assnDateMap.containsKey(dateAssigned)) {
			TreeSet<Assignment> homework = new TreeSet<Assignment>();
			homework.add(hw);
			assnDateMap.put(dateAssigned, homework);
		}
		else {
			assnDateMap.get(dateAssigned).add(hw);
=======
		Date dateDue = hw.getDue();
		Date dateAssigned = hw.getAssignedDate();
		
		if (!due.containsKey(dateDue)) {
			due.put(dateDue, new TreeSet<Assignment>());
		}
		due.get(dateDue).add(hw);
		
		if (!assigned.containsKey(dateAssigned)) {
			assigned.put(dateAssigned, new TreeSet<Assignment>());
>>>>>>> 2fd0d907b4ad450f360ceed7f61050ff6dc34273
		}
		assigned.get(dateAssigned).add(hw);
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
		TreeSet<Assignment> oldAssignments = assnDateMap.get(oldAssnDate); // old assignments set for old date
		oldAssignments.remove(assn);
		TreeSet<Assignment> newAssignments = assnDateMap.get(newAssnDate); // new assignments set for new date
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
		TreeSet<Assignment> oldAssignments = assnDateMap.get(oldDueDate); // old assignments set for old date
		oldAssignments.remove(assn);
		TreeSet<Assignment> newAssignments = assnDateMap.get(newDueDate); // new assignments set for new date
		newAssignments.add(assn);
		
		assnDateMap.put(newDueDate, newAssignments);
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
