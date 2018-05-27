package virtualPlanner.reference;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Loads and stores various user preferences.
 * 
 * @author JeremiahDeGreeff
 * @see Paths
 */
public class Preferences {
	
	/**
	 * The {@code Properties} instance for managing preferences.
	 */
	private static Properties properties;
	
	/**
	 * Loads the default and user preferences.
	 * @see Paths
	 */
	public static void loadPreferences() {
		InputStream in;
		Properties defaults = new Properties();
		try {
			in = new FileInputStream(Paths.PREFERENCES_DEFAULT);
			defaults.load(in);
			in.close();
		} catch (IOException e) {e.printStackTrace();}
		properties = new Properties(defaults);
		try {
			in = new FileInputStream(Paths.PREFERENCES);
			properties.load(in);
			in.close();
		} catch (IOException e) {savePreferences();}
		System.out.println("Preferences Loaded.");
	}
	
	/**
	 * Saves the user preferences.
	 * @see Paths
	 */
	private static void savePreferences() {
		OutputStream out;
		try {
			out = new FileOutputStream(Paths.PREFERENCES);
			properties.store(out, "Prefernces for VirtualPlanner.");
			out.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Retrieves the {@code Color} selected for a particular {@code Course}.
	 * 
	 * @param courseID The id of the {@code Course} to find.
	 * @return The {@code Color} selected for the specified {@code Course}.
	 */
	public static Color getColor(int courseID) {
		return new Color(Integer.parseUnsignedInt(properties.getProperty("course-" + courseID, Integer.toHexString(Colors.DEFAULT.getRGB())), 16));
	}
	
	/**
	 * Adds a color preference for a particular {@code Course} and saves the changes to the preferences file.
	 * If a color preference has already been specified for this particular {@code Course}, it will be overwritten.
	 * 
	 * @param courseID The id of the {@code Course} to add.
	 * @param color The {@code Color} to be selected for the specified {@code Course}.
	 */
	public static void addColor(int courseID, Color color) {
		properties.setProperty("course-" + courseID, Integer.toHexString(color.getRGB()));
		savePreferences();
	}
	
	/**
	 * Removes the color preference for a particular {@code Course} if it exists and saves the changes to the preferences file.
	 * 
	 * @param courseID The id of the {@code Course} whose color preference should be removed.
	 */
	public static void removeColor(int courseID) {
		properties.remove("course-" + courseID);
		savePreferences();
	}
	
	/**
	 * @return {@code true} if assignments should be displayed on the day when they are due; {@code false} if assignments should be displayed on the day when they are assigned.
	 */
	public static boolean displayOnDue() {
		return getBooleanProperty("displayOnDue");
	}
	
	/**
	 * Sets the displayOnDue preference to a specified value.
	 * 
	 * @param value The value to set.
	 */
	public static void setDisplayOnDue(boolean value) {
		setBooleanProperty("displayOnDue", value);
	}
	
	/**
	 * Restores the default displayOnDue preference.
	 */
	public static void removeDislayOnDue() {
		removeBooleanProperty("displayOnDue");
	}
	
	/**
	 * @return {@code true} if times should be displayed in the 12 hour format; {@code false} if times should be displayed in the 24 hour format.
	 */
	public static boolean display12Hour() {
		return getBooleanProperty("display12Hour");
	}
	
	/**
	 * Sets the display12Hour preference to a specified value.
	 * 
	 * @param value The value to set.
	 */
	public static void setDisplay12Hour(boolean value) {
		setBooleanProperty("display12Hour", value);
	}
	
	/**
	 * Restores the default display12Hour preference.
	 */
	public static void removeDisplay12Hour() {
		removeBooleanProperty("display12Hour");
	}
	
	/**
	 * Retrieves a boolean property.
	 * 
	 * @param property The property to retrieve.
	 * @return The value associated with the specified property.
	 */
	private static boolean getBooleanProperty(String property) {
		return Boolean.parseBoolean(properties.getProperty(property));
	}
	
	/**
	 * Sets a boolean property to a specified value.
	 * 
	 * @param property The property to set.
	 * @param value The value to set.
	 */
	private static void setBooleanProperty(String property, boolean value) {
		properties.setProperty(property, "" + value);
		savePreferences();
	}
	
	/**
	 * Restores the default value of a boolean property.
	 * 
	 * @param property The property to remove.
	 */
	private static void removeBooleanProperty(String property) {
		properties.remove(property);
		savePreferences();
	}
	
}
