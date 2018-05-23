package virtualPlanner.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import virtualPlanner.util.Block;

/**
 * The Student class represents a student and his or her schedule.
 * 
 * @author aldai
 * @author Leo
 */

public class Student implements User {
	
	/**
	 * student name
	 */
	private String name;
	/**
	 * maps a students' blocks to their courses
	 */
	private Map<Block, Course> schedule;
	
	/**
	 * Constructor for Student class. schedule is set to an empty HashMap mapping a students' blocks to their courses.
	 * @param name
	 */
	public Student(String name) {
		this.name = name;
		schedule = new HashMap<Block, Course>();
	}
	
	/**
	 * @return student name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return course associated with a specific block
	 */
	@Override
	public Course getCourse(Block block) {
		return schedule.get(block);
	}

	/**
	 * addCourse adds a course and its associated array of blocks to a student's schedule.
	 * @param array of blocks associated with course
	 * @param course
	 * @return true if method successfully adds a course to the students' schedule
	 */
	@Override
	public boolean addCourse(Block[] blocks, Course course) {
		for (Block block : blocks) {
			if (schedule.containsKey(block)) {
				return false;
			}
		}
		for (Block block : blocks) {
			schedule.put(block, course);
		}
		return true;
	}
	
	/**
	 * removeCourse removes a course from a students' schedule by removing the associated blocks.
	 * @param course to be removed
	 */
	public void removeCourse(Course course) {
		for (Block block : schedule.keySet()) {
			if (schedule.get(block).equals(course)) {
				schedule.remove(block);
			}
		}
	}
	
	
	/**
	 * @return all the course names of this student 
	 */
	@Override
	public String[] getCourseNames() {
		// get the courses of this student
		Collection<Course> courses = schedule.values();
		
		// record all the course names
		String[] courseNames = new String[courses.size()];
		int idx = 0;
		
		for(Course course : courses)
		{
			courseNames[idx] = course.getCourseName();
			idx++;
		}
		
		return courseNames;
	}

}
