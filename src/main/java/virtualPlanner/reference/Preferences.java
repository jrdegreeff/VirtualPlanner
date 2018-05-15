package virtualPlanner.reference;

import java.awt.Color;
import java.util.Map;

import virtualPlanner.backend.Course;

/**
 * Loads and stores various user preferences.
 * 
 * @author JeremiahDeGreeff
 */
public class Preferences {
	
	/**
	 * The {@code Color}s selected for each {@code Course}.
	 */
	private static Map<Course, Color> colors;
	/**
	 * If true, assignments should be displayed on the day when they are due.
	 * If false, assignments should be displayed on the day when they are assigned.
	 */
	private static boolean displayOnDue;
	/**
	 * If true, times should be displayed in the 12 hour format.
	 * If false, times should be displayed in the 24 hour format.
	 */
	private static boolean display12Hour;
	
	/**
	 * Retrieves the {@code Color} selected for a particular {@code Course}.
	 * 
	 * @param course The {@code Course} to find.
	 * @return The {@code Color} selected for the specified {@code Course}.
	 */
	public static Color getColor(Course course) {
		return colors.get(course);
	}
	
	/**
	 * @return {@code true} if assignments should be displayed on the day when they are due; {@code false} if assignments should be displayed on the day when they are assigned.
	 */
	public static boolean displayOnDue() {
		return displayOnDue;
	}
	
	/**
	 * @return {@code true} if times should be displayed in the 12 hour format; {@code false} if times should be displayed in the 24 hour format.
	 */
	public static boolean display12Hour() {
		return display12Hour;
	}
	
}
