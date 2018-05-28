package virtualPlanner.reference;

import java.nio.file.FileSystems;

/**
 * Contains static path constants for various I/O operations.
 * 
 * @author JeremiahDeGreeff
 */
public class Paths {
	
	/**
	 * System-dependent filesystem separator.
	 */
	private static final String SEPARATOR = ""; //FileSystems.getDefault().getSeparator();
	
	/**
	 * Path to the preferences file.
	 */
	public static final String PREFERENCES = "config.properties";
	/**
	 * Path to the default preferences file.
	 */
	public static final String PREFERENCES_DEFAULT = "config_default.properties";
	
	/**
	 * Path to the previous arrow image.
	 */
	public static final String IMAGE_PREVIOUS = SEPARATOR + "previous.png";
	/**
	 * Path to the next arrow image.
	 */
	public static final String IMAGE_NEXT = SEPARATOR + "next.png";
	/**
	 * Path to the gradebook image.
	 */
	public static final String IMAGE_GRADEBOOK = SEPARATOR + "gradebookgreen.png";
	/**
	 * Path to the settings image.
	 */
	public static final String IMAGE_SETTINGS = SEPARATOR + "settingsicon1.png";
	
}
