package virtualPlanner.backend;

import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.util.Date;

/**
 * Abstract class defining all assignments
 * @author Leo Dong
 *
 */
public class Assignment implements Comparable<Assignment> {
	
	/**
	 * date assigned
	 */
	private Date assignedDate;
	
	/**
	 * date due
	 */
	private Date dueDate;
	
	/**
	 * type of assignment (e.g. homework, test, quiz, etc.)
	 */
	private AssignmentTypes type;
	
	/**
	 * whether the assignment is completed
	 */
	private boolean isComplete;
	
	/**
	 * name of the assignment
	 */
	private String name;
	
	/**
	 * description of the assignment
	 */
	private String descrip;
	
	/**
	 * internal id counter for giving unique ids
	 */
	private static int idCounter = 0;
	
	/**
	 * unique id for each assignment object
	 */
	private final int ID;
	
	/**
	 * Constructor for Assignment class. isComplete is set to false by default.
	 * @param assigned date assigned
	 * @param due date due
	 * @param type type of assignment
	 * @param name short name of the assignment
	 * @param description detailed description of the assignment
	 */
	public Assignment(Date assigned, Date due, AssignmentTypes type, String name, String description) {
		this.assignedDate = assigned;
		this.dueDate = due; 
		this.name = name;
		this.descrip = description; 
		isComplete = false;
		ID = idCounter; 
		idCounter++; // update idCounter to ensure unique ids
	}
	
	/**
	 * @return assignment date
	 */
	public Date getAssignedDate() {
		return assignedDate;
	}
	
	/**
	 * Changes the assigned date. 
	 * @param newAssn new assigned date
	 * @return old assigned date
	 */
	protected Date changeAssignedDate(Date newAssn) {
		Date oldAssn = assignedDate; 
		assignedDate = newAssn;
		return oldAssn; 
	}
	
	/**
	 * @return due date of an assignment
	 */
	public Date getDue() {
		return dueDate;
	}
	
	/**
	 * Changes the due date. 
	 * @param newDue new due date
	 * @return old due date
	 */
	protected Date changeDueDate(Date newDue) {
		Date oldDue = assignedDate; 
		assignedDate = newDue;
		return oldDue; 
	}
	
	/**
	 * @return type of the assignment
	 */
	public AssignmentTypes getAssignmentTypes() {
		return type;
	}
	
	/**
	 * @return true if assignment is completed; false if otherwise
	 */
	public boolean isComplete() {
		return isComplete;
	}
	
	/**
	 * Set the assignment to be completed. 
	 */
	public void complete() {
		isComplete = true;
	}
	
	/**
	 * Set the assignment to be not completed. 
	 */
	public void uncomplete() {
		isComplete = false;
	}
	
	/**
	 * @return name of the assignment
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the assignment. 
	 * @param newName name of the assignment
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * @return description of the assignment
	 */
	public String getDescrip() {
		return descrip; 
	}
	
	/**
	 * Set the description of the assignment. 
	 * @param newDescrip new description of the assignment
	 */
	public void setDescrip(String newDescrip) {
		descrip = newDescrip;
	}
	
	/**
	 * @return unique id as hash code for this assignment object
	 */
	public int hashCode() {
		return ID;
	}
	
	/**
	 * @return string representation of the assignment
	 */
	public String toString() {
		String str = "";
		str += "Assigned Date: " + assignedDate + "; " + "Due Date: " + dueDate + "\n"; 
		str += "Type: " + type + "; Name: " + name + "; ID: " + ID + "\nDescription: " + descrip + "\n";
		str += "Completed: " + isComplete; 
		return str;
	}
	
	
	/**
	 * Compare this assignment to another assignment in terms of priority and id.
	 * When two assignments are of different priorities, compare priorities. 
	 * When two assignments are of the same priority, compare ids. 
	 * @param assn another assignment to compare to 
	 * @return a negative number if this assignment is smaller than the other assignment 
	 * (either smaller priority or same priority but is created earlier), positive number if 
	 * this is assignment is greater than the other assignment, and 0 if they are equal. 
	 */
	public int compareTo(Assignment assn) {
		return this.type.getPriority() == assn.type.getPriority() ? this.ID - assn.ID : this.type.getPriority() - assn.type.getPriority();
	}
	
	
	/**
	 * Compare this assignment to another assignment in terms of priority and id.  
	 * @param assn another assignment to compare to
	 * @return true if two assignments have the same priority and id; false if otherwise
	 */
	public boolean equals(Assignment assn) {
		return this.compareTo(assn) == 0;
	}
	
}
