package virtualPlanner.backend;

import virtualPlanner.reference.AssignmentTypes;
import virtualPlanner.util.Date;

/**
 * Class defining all assignments
 * 
 * @author Leo Dong
 * @author JeremiahDeGreeff
 */
public class Assignment implements Comparable<Assignment> {
	
	/**
	 * The unique id of this {@code Course} as it is identified in the database.
	 */
	private final int id;
	
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
	 * Constructor for Assignment class. isComplete is set to false by default.
	 * 
	 * @param assigned date assigned
	 * @param due date due
	 * @param type type of assignment
	 * @param name short name of the assignment
	 * @param description detailed description of the assignment
	 */
	public Assignment(int id, Date assigned, Date due, AssignmentTypes type, String name, String description, boolean isComplete) {
		this.id = id;
		this.assignedDate = assigned;
		this.dueDate = due; 
		this.type = type;
		this.name = name;
		this.descrip = description; 
		this.isComplete = isComplete;
	}
	
	/**
	 * @return unique ID for this assignment object
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * @return assignment date
	 */
	public Date getAssignedDate() {
		return assignedDate;
	}
	
	/**
	 * @return due date of an assignment
	 */
	public Date getDue() {
		return dueDate;
	}
	
	/**
	 * @return type of the assignment
	 */
	public AssignmentTypes getAssignmentType() {
		return type;
	}
	
	/**
	 * @return true if assignment is completed; false if otherwise
	 */
	public boolean isComplete() {
		return isComplete;
	}
	
	/**
	 * @return name of the assignment
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return description of the assignment
	 */
	public String getDescrip() {
		return descrip; 
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
	 * Changes the due date. 
	 * @param newDue new due date
	 * @return old due date
	 */
	protected Date changeDueDate(Date newDue) {
		Date oldDue = dueDate;
		dueDate = newDue;
		return oldDue;
	}
	
	/**
	 * Changes the assignment type of this {@code Assignment}.
	 * 
	 * @param newType The type to change to.
	 */
	protected void setType(AssignmentTypes newType) {
		type = newType;
	}
	
	/**
	 * Sets this {@code Assignment} to be complete or incomplete.
	 * 
	 * @param isComplete {@code true} if complete, {@code false} if incomplete.
	 */
	protected void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	/**
	 * Changes the name of this {@code Assignment}. 
	 * 
	 * @param newName The new name for this {@code Assignment}.
	 */
	protected void setName(String newName) {
		name = newName;
	}
	
	/**
	 * Changes the description of this {@code Assignment}. 
	 * 
	 * @param newDescrip The new description for this {@code Assignment}.
	 */
	protected void setDescrip(String newDescrip) {
		descrip = newDescrip;
	}
	
	/**
	 * Returns a String representation of this {@code Assignment}.
	 */
	@Override
	public String toString() {
		return name + (descrip.equals("") ? "" : " (" + descrip + ")") + "[type = " + type.name() + "] assigned on " + assignedDate + ", due on " + dueDate + (isComplete ? " (complete)" : " " )+ "{id = " + id + "}";
	}
	
	/**
	 * Returns {@code true} if {@code other} is a {@code Assignment} with the same id as this {@code Assignment}, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof Assignment && this.id == ((Assignment) other).id;
	}
	
	@Override
	public int hashCode() {
		return ("" + id).hashCode();
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
	@Override
	public int compareTo(Assignment assn) {
		return this.type.getPriority() == assn.type.getPriority() ? this.id - assn.id : this.type.getPriority() - assn.type.getPriority();
	}
	
}
