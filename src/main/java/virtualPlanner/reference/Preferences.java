package virtualPlanner.reference;

import java.awt.Color;
import java.util.Map;

/**
 * Loads and stores various user preferences.
 * 
 * @author JeremiahDeGreeff
 */
public class Preferences {
	
	/**
	 * The colors selected for each block.
	 */
	private Map<Blocks, Color> colors;
	/**
	 * If true, assignments should be displayed on the day when they are due.
	 * If false, assignments should be displayed on the day when they are assigned.
	 */
	private boolean displayOnDue;
	/**
	 * If true, times should be displayed in the 12 hour format.
	 * If false, times should be displayed in the 24 hour format.
	 */
	private boolean display12Hour;
	
	/**
	 * Retrieves the color selected for a particular block.
	 * 
	 * @param Block the block to find.
	 * @return The color selected for the specified block.
	 */
	public Color getColor(Blocks block) {
		return colors.get(block);
	}
	
	/**
	 * @return True if assignments should be displayed on the day when they are due; false if assignments should be displayed on the day when they are assigned.
	 */
	public boolean displayOnDue() {
		return displayOnDue;
	}
	
	/**
	 * @return True if times should be displayed in the 12 hour format; false if times should be displayed in the 24 hour format.
	 */
	public boolean display12Hour() {
		return display12Hour;
	}
	
}
