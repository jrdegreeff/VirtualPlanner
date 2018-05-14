package virtualPlanner.backend;

import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.util.Date;

/**
 * Abstract class defining all assignments
 * @author Leo Dong
 *
 */
public class Assignment implements Comparable<Assignment> {
	private Date assigned;
	private Date due;
	private AssignmentTypes type;
	private boolean isComplete;
	private String name;
	private String descrip; 
	
	/**
	 * Constructor for Assignment class. isComplete is set to false by default.
	 * @param assigned date assigned
	 * @param due date due
	 * @param type type of assignment
	 * @param name short name of the assignment
	 * @param description detailed description of the assignment
	 */
	public Assignment(Date assigned, Date due, AssignmentTypes type, String name, String description) {
		this.assigned = assigned;
		this.due = due; 
		this.name = name;
		this.descrip = description; 
		isComplete = false;
	}
	
	/**
	 * @return assignment date
	 */
	public Date getAssignedDate() {
		return assigned;
	}
	
	/**
	 * @return due date of an assignment
	 */
	public Date getDue() {
		return due;
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
	 * Compare this assignment to another assignment in terms of priority. 
	 * @param assn another assignment to compare to 
	 * @return a negative number if this is smaller than assn, positive number if this is greater
	 * than assn, and 0 if this is equal to assn
	 */
	public int compareTo(Assignment assn) {
		return this.type.getPriority() - assn.type.getPriority();
	}
	
	
	
}
