package virtualPlanner;

import virtualPlanner.io.DatabaseController;
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
		System.out.println(DatabaseController.registerUser("jrdegreeff", "1234", "Jeremiah DeGreeff"));
	}
	
}
