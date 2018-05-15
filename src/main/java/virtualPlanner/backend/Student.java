package virtualPlanner.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import virtualPlanner.util.Block;

public class Student implements User {
	
	String name;
	Map<Block, Course> schedule;
	
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
	
	public void addBlock(ArrayList<Block> blocks, Course course) {
		for (Block block : blocks) {
			schedule.put(block, course);
		}
	}
	
	public void dropBlock(Course course) {
		for (Block block : schedule.keySet()) {
			if (schedule.get(block).equals(course)) {
				schedule.remove(block);
			}
		}
	}

}
