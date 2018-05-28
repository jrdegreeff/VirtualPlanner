package virtualPlanner.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import virtualPlanner.util.Block;

/**
 * The Student class represents a student and his or her schedule.
 * 
 * @author aldai
 * @author Leo
 * @author JeremiahDeGreeff
 */

public class Student implements User {
	
	/**
	 * The unique id of this {@code Student} as it is identified in the database.
	 */
	private final int id;
	
	/**
	 * student name
	 */
	private String name;
	
	/**
	 * maps a students' blocks to their courses
	 */
	private Map<Integer, Course> schedule;
	
	/**
	 * Constructor for Student class. schedule is set to an empty HashMap mapping a students' blocks to their courses.
	 * 
	 * @param id The id of the new {@code Student}. Must be an id given from the database to avoid overwritting records.
	 * @param name student name
	 */
	public Student(int id, String name) {
		this.id = id;
		this.name = name;
		schedule = new HashMap<Integer, Course>();
	}
	
	/**
	 * @return The id of this {@code Student}.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * @return student name
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Changes the name of this {@code Student}.
	 * 
	 * @param name The new name.
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return course associated with a specific block
	 */
	@Override
	public Course getCourse(Block block) {
		return schedule.get(block.getID());
	}

	/**
	 * Adds a course to a student's schedule according to its {@code Block}s.
	 * Should only be called once the availability of the blocks has been checked.
	 * 
	 * @param blocks {@code Block}s to add the {@code Course} to.
	 * @param course The {@code Course} to add.
	 */
	@Override
	public void addCourse(Block[] blocks, Course course) {
		for (Block block : blocks)
			schedule.put(block.getID(), course);
	}
	
	/**
	 * Tests whether the {@code Student}'s schedule conflicts with certain blocks.
	 * Optionally ignores the {@code Block}s occupied by a particular {@code Course}.
	 * 
	 * @param blocks The {@code Block}s to test.
	 * @param ignore An optional {@code Course} to ignore.
	 * @return {@code true} if there is no conflict, {@code false} otherwise.
	 */
	@Override
	public boolean checkAvailability(Block[] blocks, Course ignore) {
		for (Block block : blocks)
			if (schedule.containsKey(block.getID()) && !schedule.get(block.getID()).equals(ignore))
				return false;
		return true;
	}
	
	/**
	 * Adds a course to a student's schedule according to its blocksids.
	 * 
	 * @param blocks blockids to add the {@code Course} to.
	 * @param course The {@code Course} to add.
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	@Override
	public boolean addCourse(Integer[] blocks, Course course) {
		for (int blockid : blocks)
			if (schedule.containsKey(blockid))
				return false;
		for (int blockid : blocks)
			schedule.put(blockid, course);
		return true;
	}
	
	/**
	 * Removes a {@code Course} from this {@code Student}'s schedule.
	 * 
	 * @param course The {@code Course}to be removed.
	 */
	@Override
	public void removeCourse(Course course) {
		Iterator<Course> iter = schedule.values().iterator();
		while(iter.hasNext())
			if(iter.next().equals(course))
				iter.remove();
	}
	
	/**
	 * Retrieves all of this {@code Student}'s {@code Courses}.
	 * 
	 * @return All of this {@code Student}'s {@code Courses}.
	 */
	public Course[] getAllCourses() {
		Set<Course> courses = new HashSet<Course>();
		for(Course course : schedule.values())
			courses.add(course);
		return courses.toArray(new Course[0]);
	}
	
	/**
	 * Retrieves string representations of all of this {@code Student}'s courses.
	 * 
	 * @return String representations of the {@code Student}'s courses.
	 */
	@Override
	public String[] getCourseNames() {
		Course[] courses = getAllCourses();
		String[] courseNames = new String[courses.length];
		for(int i = 0; i < courses.length; i++)
			courseNames[i] = courses[i].getName();
		return courseNames;
	}
	
	/**
	 * Returns a String representation of this {@code Student}.
	 */
	@Override
	public String toString() {
		return name + " [number of blocks assigned: " + schedule.size() + "] {id = " + id + "}";
	}

	/**
	 * Returns {@code true} if {@code other} is a {@code Student} with the same id as this {@code Student}, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof Student && this.id == ((Student) other).id;
	}
	
	@Override
	public int hashCode() {
		return ("" + id).hashCode();
	}

}
