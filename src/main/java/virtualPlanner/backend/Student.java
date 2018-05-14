package virtualPlanner.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student implements User {
	
	String name;
	Map<jdk.nashorn.internal.ir.Block, Course> schedule;
	
	public Student(String name) {
		this.name = name;
		schedule = new HashMap<jdk.nashorn.internal.ir.Block, Course>();
	}

	public String getName() {
		return name;
	}

	public Course getCourse(jdk.nashorn.internal.ir.Block block) {
		return schedule.get(block);
	}
	
	public void addBlock(ArrayList<jdk.nashorn.internal.ir.Block> blocks, Course course) {
		for (jdk.nashorn.internal.ir.Block block : blocks) {
			schedule.put(block, course);
		}
	}
	
	public void dropBlock(Course course) {
		for (jdk.nashorn.internal.ir.Block block : schedule.keySet()) {
			if (schedule.get(block).equals(course)) {
				schedule.remove(block);
			}
		}
	}

}
