package virtualPlanner.backend;

import java.util.HashMap;
import java.util.Map;

import virtualPlanner.util.Block;

// don't forget to comment later anna !!

public class Student implements User {
	
	private String name;
	private Map<Block, Course> schedule;
	
	public Student(String name) {
		this.name = name;
		schedule = new HashMap<Block, Course>();
	}

	public String getName() {
		return name;
	}

	public Course getCourse(Block block) {
		return schedule.get(block);
	}

	public boolean addCourse(Block[] blocks, Course course) {
		for (Block block : blocks) {
			if (schedule.containsKey(block)) {
				return false;
			}
			schedule.put(block, course);
		}
		return true;
	}

	public void removeCourse(Course course) {
		for (Block block : schedule.keySet()) {
			if (schedule.get(block).equals(course)) {
				schedule.remove(block);
			}
		}
	}

}
