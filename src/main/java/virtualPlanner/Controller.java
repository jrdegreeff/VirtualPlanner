package virtualPlanner;

import virtualPlanner.backend.User;
import virtualPlanner.gui.GUIController;
import virtualPlanner.io.DatabaseController;

/**
 * Central Controller for the VirtualPlanner project.
 * 
 * @author JeremiahDeGreeff
 */
public class Controller {
	
	/**
	 * The {@code DatabaseController} instance for the VirtualPlanner.
	 */
	private DatabaseController dbController;
	/**
	 * The {@code GUIController} instance for the VirtualPlanner.
	 */
	private GUIController guiController;
	/**
	 * The {@code User} instance for the VirtualPlanner.
	 */
	private User user;
	
	public Controller() {
		dbController = new DatabaseController();
	}
	
}
