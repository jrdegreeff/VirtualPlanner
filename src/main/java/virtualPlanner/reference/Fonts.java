package virtualPlanner.reference;

import java.awt.Font;

/**
 * All {@code Font}s used in the project.
 * 
 * @author JeremiahDeGreeff
 * @author KevinGao
 */
public class Fonts {
	
	/**Font used in the JLabel which displays the current week*/
	public static final Font CALENDAR_WEEK = new Font("SansSerif", Font.BOLD, 30); 
	/**Font used in the JLabel which displays the current day*/
	public static final Font CALENDAR_DATE = new Font("SansSerif", Font.BOLD, 40);
	/**Font used in the day of week JLabels in the Calendar*/
	public static final Font CALENDAR_DAY = new Font("Dialog", Font.BOLD, 18);
	/**Font used in the individual blocks*/
	public static final Font CALENDAR_BLOCK = new Font("Dialog", Font.BOLD, 12);
	/**Font used in the Upcoming Events JList*/
	public static final Font CALENDAR_LIST = new Font("Dialog", Font.BOLD, 22);
	/**Font used in the Add Class Window's Fields*/
	public static final Font CALENDAR_ADD_CLASS = new Font("Dialog", Font.BOLD, 16);
	/**Font used in the Add Settings Window's Fields*/
	public static final Font CALENDAR_SETTINGS = new Font("Dialog", Font.BOLD, 16);
	
	/**Font for title JButtons in the Add Class Window*/
	public static final Font BUTTON_TITLE = new Font("Dialog", Font.BOLD, 26);
	/**Font for assignment JButtons in the Add Class Window*/
	public static final Font BUTTON_ASSIGNMENT = new Font("Dialog", Font.BOLD, 20);
	/**Font for normal JButtons in the Add Class Window*/
	public static final Font BUTTON_BUTTON = new Font("SansSerif", Font.BOLD, 20);
	/**Font for the New Assignment JLabel in the Add Class Window*/
	public static final Font BUTTON_NEW_ASSIGNMENT = new Font("Dialog", Font.BOLD, 18);
	
	/**Font for the Login Window's uppermost, welcome JLabel*/
	public static final Font LOGIN_TITLE = new Font("Dialog", Font.BOLD, 28);
	/**Font for the Login Window's JLabel which informs the user*/
	public static final Font LOGIN_INFO = new Font("Dialog", Font.BOLD, 18);
	/**Font for the Login Window's JTextFields*/
	public static final Font LOGIN_DEFAULT = new Font("Dialog", Font.PLAIN, 18);
	/**Font for the Login Window's submit button*/
	public static final Font LOGIN_BUTTON = new Font("Dialog", Font.BOLD, 22);
	
}
