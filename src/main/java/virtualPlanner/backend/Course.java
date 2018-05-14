package virtualPlanner.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import virtualPlanner.util.Date;

/**
 * 
 * @author aldai
 * The Course class maps dates to an ArrayList of assignments.
 */
public class Course {
	String name, teacher;
	Map<Date, ArrayList<Assignment>> study;
	
	// maps dates to an arraylist of assignments, order of arrival
	public Course(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
		study = new HashMap<Date, ArrayList<Assignment>>();
	}
	
	// return course name
	public String getCourseName() {
		return name;
	}
	
	// return teacher name
	public String getTeacher() {
		return teacher;
	}
	
	// add assignments by date
	public void addAssignment(Date date, Assignment hw) {
		if (!study.containsKey(date)) {
			ArrayList<Assignment> homework = new ArrayList<Assignment>();
			homework.add(hw);
			study.put(date, homework);
			return;
		}
		study.get(date).add(hw);
	}
}
