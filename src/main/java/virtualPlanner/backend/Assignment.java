package virtualPlanner.backend;

import virtualPlanner.util.Date;

/**
 * Abstract class defining all assignments
 * @author Leo Dong
 *
 */
public class Assignment {
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
	
	public Date getDue() {
		return due;
	}
	
	public getAssignmentTypes() {
		return type;
	}
	
	public boolean isComplete() {
		return isComplete;
	}
	
	public void complete() {
		isComplete = true;
	}
	
	public void uncomplete() {
		isComplete = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getDescrip() {
		return descrip; 
	}
	
	public void setDescrip(String newDescrip) {
		descrip = newDescrip;
	}
	
	
	
}
