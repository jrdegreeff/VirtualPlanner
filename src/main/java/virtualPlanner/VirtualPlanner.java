package virtualPlanner;

import virtualPlanner.backend.Student;
import virtualPlanner.backend.User;
import virtualPlanner.gui.GUIController;
import virtualPlanner.reference.Preferences;

/**
 * Main class for the VirtualPlanner project.
 * 
 * @author AnnaDai
 * @author JeremiahDeGreeff
 * @author LeoDong
 * @author KevinGao
 */
public class VirtualPlanner {
	
	public static void main(String[] args) {
		Preferences.loadPreferences();
		User user = new Student(1, "Jeremiah DeGreeff");
		new GUIController(user); 
	}
	
}
