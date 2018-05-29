package virtualPlanner;

import virtualPlanner.backend.Controller;
import virtualPlanner.gui.GUIController;
import virtualPlanner.io.DatabaseController;
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
		DatabaseController dbController = new DatabaseController();
		Controller controller = new Controller(dbController);
		new GUIController(controller);
	}
	
	/**
	 * Performs static initialization.
	 */
	public static void init() {
		Preferences.loadPreferences();
		Images.loadImages();
	}
	
}
