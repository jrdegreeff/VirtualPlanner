package virtualPlanner;

import virtualPlanner.reference.Images;
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
		init();
		new Controller();
	}
	
	/**
	 * Performs static initialization.
	 */
	public static void init() {
		Preferences.loadPreferences();
		Images.loadImages();
	}
	
}
