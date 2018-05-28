package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import virtualPlanner.backend.Course;
import virtualPlanner.reference.Days;
import virtualPlanner.reference.Fonts;
import virtualPlanner.reference.Images;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * This class represents the main JFrame which holds the Calendar and all its immediate features
 * 
 * @author KevinGao
 * @author JeremiahDeGreeff
 */
public class MainCalendarWindow implements ActionListener {

	/**The controller for this JFrame.*/
	private GUIController controller;

	/**Today's date.*/
	private static Date currentDate;

	/**The start date of the week which is being displayed currently.*/
	private static Date weekStartDate;

	/**The JFrame of this MainCalendarWindow instance*/
	private JFrame frame;

	private SettingsWindow settings;

	private AddCourseWindow addCourse;

	private static final Dimension MAIN_SIZE = new Dimension(1280, 720);

	/**Outermost of all nested JPanels which is directly added into the JFrame*/
	private JPanel mainPanel;
	/**JPanel which holds the main calendar for the window */
	private JPanel panelCalendar;
	/**JPanel which holds all of the extra features accessible from MainCalendarWindow*/
	private JPanel infoPanel;

	/**Width of each individual column (day) in the calendar*/
	private static final int CALENDAR_COLUMN_WIDTH = 70;
	/**Height of the day of week labels at the top of each column*/
	private static final int CALENDAR_LABEL_HEIGHT = 20;

	//JMenuBar and sub-components
	/**The JMenuBar for this MainCalendarWindow*/
	private JMenuBar menuBar;
	/**Drop-down JMenu "Options" in the menuBar*/
	private JMenu menuOptions;
	/**Selectable JMenuItem which allows a user to add a course*/
	private JMenuItem menuItemAddCourse;
	/**Selectable JMenuItem which allows a user to return to the current week*/
	private JMenuItem menuItemCurrentWeek;

	//JButtons
	/**JButton which allows the user to show previous weeks*/
	private JButton buttonLeft;
	/**JButton which allows the user to show upcoming weeks*/
	private JButton buttonRight;
	/**JButton which accesses the user's gradebook NOTE: NOT IMPLEMENTED*/
	private JButton buttonGradebook;
	/**JButton which allows the user to customize features*/
	private JButton buttonSettings;

	//JLabels
	/**JLabel which shows the range of Dates that the week displayed represents*/
	private JLabel labelWeek;
	/**JLabel which displays the current date*/
	private JLabel labelDate;

	/**2D-Array representation of the Calendar GUIButtons*/
	private GUIButton[][] buttons;

	/**JList of UpcomingEvents*/
	private JList<String> events;
	/**JScrollPane which enables scrollable features for the events JList*/
	private JScrollPane eventsScrollPane;

	/**ArrayList of all the GUIButtons that are day of week labels (simply show the current day)*/
	private ArrayList<GUIButton> dayOfWeekButtons;

	/**Size for labelDate*/
	private static final Dimension DATE_SIZE = new Dimension(400, 100);

	/**
	 * Size of the upcoming events JList
	 * Note: Height is always overriden by JList's setVisibleRowCount method, hence height being 0
	 */
	private static final Dimension UPCOMING_EVENTS_SIZE = new Dimension(365, 0);

	/**Size of each individual block*/
	private static final Dimension BLOCK_SIZE = new Dimension(CALENDAR_COLUMN_WIDTH, 30);

	/**Array which holds all the months - used as reference when displaying dates*/
	private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	/**
	 * Constructor: Initializes the MainCalendarWindow
	 */
	public MainCalendarWindow(GUIController controller) {

		//Name - personalization
		frame = new JFrame("Virtual Planner - " + controller.getUserName());

		//GUIController
		this.controller = controller;

		//Date
		currentDate = new Date();
		weekStartDate = currentDate.getWeekStartDate();

		//Size
		frame.setSize(MAIN_SIZE);

		//Color
		frame.setBackground(Color.WHITE);

		//Main Panel
		mainPanel = new JPanel();

		//JMenuBar
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		//Option JMenu and Current Week feature
		menuOptions = new JMenu("Options");
		menuItemCurrentWeek = new JMenuItem("Go to Current Week");
		menuItemCurrentWeek.addActionListener(this);
		menuOptions.add(menuItemCurrentWeek);

		//Add Course JMenuItem
		menuItemAddCourse = new JMenuItem("Add Course");
		menuItemAddCourse.addActionListener(this);

		//Add components to menuBar
		menuBar.add(menuOptions);
		menuBar.add(menuItemAddCourse);

		//Add the base components of the GUI
		buttons = new GUIButton[7][];
		addComponents();

		//Refresh current date, week label, and all calendar buttons
		updateWeek();

		//Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Adds the main base components of the MainCalendarWindow 
	 */
	public void addComponents() {
		//Clear frame
		frame.getContentPane().removeAll();

		//Calendar 

		//Left Button to show previous week
		buttonLeft = new JButton();
		buttonLeft.setIcon(new ImageIcon(Images.getPrevious()));
		buttonLeft.setOpaque(true);
		buttonLeft.setContentAreaFilled(false);
		buttonLeft.setBorderPainted(false);
		buttonLeft.addActionListener(this);
		buttonLeft.setFocusable(false);		

		//Right Button to show next week
		buttonRight = new JButton();
		buttonRight.setIcon(new ImageIcon(Images.getNext()));
		buttonRight.setOpaque(true);
		buttonRight.setContentAreaFilled(false);
		buttonRight.setBorderPainted(false);
		buttonRight.addActionListener(this);
		buttonRight.setFocusable(false);

		//JPanel for the calendar itself
		panelCalendar = new JPanel();
		labelWeek = new JLabel();
		labelWeek.setOpaque(true);
		labelWeek.setBackground(Color.WHITE);
		labelWeek.setFont(Fonts.CALENDAR_WEEK);

		//First row above the Calendar
		//Displaying of labelWeek and Prev + Next buttons
		Box weekBox = Box.createHorizontalBox();
		weekBox.add(buttonLeft);
		weekBox.add(Box.createHorizontalGlue());
		weekBox.add(labelWeek);
		weekBox.add(Box.createHorizontalGlue());
		weekBox.add(buttonRight);

		//Main Panel for Calendar
		panelCalendar = new JPanel();
		panelCalendar.setLayout(new GridBagLayout());
		panelCalendar.setBackground(Color.WHITE);

		//JPanel for the right of the calendar - the 'info' section
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		//Set the infoPanel to show the current day
		String currentDayOfWeek = currentDate.getDayOfWeek().toString();
		currentDayOfWeek = currentDayOfWeek.charAt(0) + currentDayOfWeek.substring(1, currentDayOfWeek.length()).toLowerCase();
		labelDate = new JLabel("<html>" + currentDayOfWeek + "<br/>" + MONTHS[currentDate.getMonth()] + " " + currentDate.getDay() + ", " + currentDate.getYear() + "</html>");
		labelDate.setFont(Fonts.CALENDAR_DATE);
		labelDate.setOpaque(true);
		labelDate.setForeground(Color.BLACK);
		labelDate.setFont(Fonts.CALENDAR_DATE);
		labelDate.setPreferredSize(DATE_SIZE);
		JPanel panelLabelDate = new JPanel();
		panelLabelDate.add(labelDate);

		//Upcoming Events JLabel and JList
		JLabel labelEvents = new JLabel("Upcoming Events");
		labelEvents.setOpaque(true);
		labelEvents.setForeground(Color.BLACK);
		labelEvents.setFont(Fonts.CALENDAR_LIST);
		JPanel panelLabelEvents = new JPanel();
		panelLabelEvents.add(labelEvents);

		events = new JList<String>();
		events.setFont(Fonts.CALENDAR_LIST);
		events.setForeground(Color.BLACK);
		events.setPreferredSize(UPCOMING_EVENTS_SIZE);
		String[] test = {"History Paper", "Chemistry Test", "Math Homework", "Writing Workshop Packet"};
		events.setListData(test);
		eventsScrollPane = new JScrollPane(events);
		JPanel panelEventsScrollPane = new JPanel();
		panelEventsScrollPane.add(eventsScrollPane);

		//Gradebook and Settings Buttons
		buttonGradebook = new JButton();
		buttonGradebook.setIcon(new ImageIcon(Images.getGradebook()));
		buttonGradebook.setOpaque(true);
		buttonGradebook.setContentAreaFilled(false);
		buttonGradebook.setBorderPainted(false);
		buttonGradebook.addActionListener(this);
		buttonGradebook.setFocusable(false);
		buttonGradebook.setBackground(Color.cyan);

		buttonSettings = new JButton();
		buttonSettings.setIcon(new ImageIcon(Images.getSettings()));
		buttonSettings.setOpaque(true);
		buttonSettings.setContentAreaFilled(false);
		buttonSettings.setBorderPainted(false);
		buttonSettings.addActionListener(this);
		buttonSettings.setFocusable(false);

		//Organizing structore for the Gradebook and Settings Buttons
		Box infoButtons = Box.createHorizontalBox();
		infoButtons.add(buttonGradebook);
		infoButtons.add(buttonSettings);

		//Final Organizing JPanel of the calendar portion of MainCalendarWindow
		JPanel calendarVertical = new JPanel();
		calendarVertical.add(weekBox);
		calendarVertical.add(panelCalendar);

		calendarVertical.setOpaque(true);
		calendarVertical.setBackground(Color.white);
		calendarVertical.setLayout(new BoxLayout(calendarVertical, BoxLayout.Y_AXIS));

		//Adding components and features to the infoPanel
		infoPanel.add(panelLabelDate);
		for(int i = 0; i < 5; i ++)
			infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(panelLabelEvents);
		infoPanel.add(panelEventsScrollPane);
		for(int i = 0; i < 35; i ++)
			infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(infoButtons);

		//Final organizing panel
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(calendarVertical);
		mainPanel.add(infoPanel);

		frame.add(mainPanel);
	}

	/**
	 * This method adds the weekly JButtons to the calendarPanel
	 */
	public void updateButtons() {
		//Initial default values for GridBagLayout constraints
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 50;
		c.ipady = -26;

		//x-coordinate of GridBagLayout
		c.gridx = 0;
		//y-coordinate of GridBagLayout
		c.gridy = 0;
		//Vertical Padding
		c.ipady = 10;
		//Dimension to keep all sizes constant
		Dimension labelSize = new Dimension(CALENDAR_COLUMN_WIDTH, CALENDAR_LABEL_HEIGHT);

		//Add Weekday Labels
		dayOfWeekButtons = new ArrayList<GUIButton>(7);
		for (int i = 0; i < Days.values().length; i++) {
			c.gridx = i;
			GUIButton newButton = new GUIButton(Days.values()[i].getName(), labelSize, Fonts.CALENDAR_DAY);
			panelCalendar.add(newButton, c);
			dayOfWeekButtons.add(newButton);
		}

		//Add Blocks
		for(int i = 0; i < Days.values().length; i++) {
			c.ipady = 25;
			c.gridx = i;
			Days blockOrder = Days.values()[i];
			buttons[i] = new GUIButton[blockOrder.getBlockCount()];
			for(int j = 0; j < blockOrder.getBlockCount(); j++) {
				c.gridy = j + 1;
				Block block = blockOrder.getBlock(j);
				Course course = controller.getCourse(block);
				GUIButton button = new GUIButton(block.getBlock().getAbbreviation(), block, course == null ? -1 : course.getID(), controller.getAssignments(weekStartDate.getUpcomingDate(j), block), BLOCK_SIZE, Fonts.CALENDAR_BLOCK, controller, controller.getCourse(block), this, weekStartDate.getUpcomingDate(j));
				panelCalendar.add(button, c);
				buttons[i][j] = button;
			}
		}

		//Sunday Large Blank Block
		c.gridx = Days.values().length-1;
		c.gridy = 1;
		c.gridheight = 10;
		c.fill = GridBagConstraints.VERTICAL;
		GUIButton newButton = new GUIButton("", BLOCK_SIZE, Fonts.CALENDAR_DAY);
		panelCalendar.add(newButton, c);

		panelCalendar.repaint();
	}

	/**
	 * This method finds and highlights the day of week GUIButton that corresponds to the current day
	 */
	public void highlightCurDay() {
		if (currentDate.compareTo(weekStartDate) < 0 || currentDate.compareTo(weekStartDate.getUpcomingDate(7)) > 0)
			return;

		String curDay = currentDate.getDayOfWeek().toString();
		for(GUIButton b : dayOfWeekButtons) {
			if(b.getText().equalsIgnoreCase(curDay)) {
				b.setBackground(Color.YELLOW);
			}
		}
	}

	protected void closeSettings() {
		settings = null;
		updateWeek();
	}

	protected void closeAddAccount() {
		addCourse = null;
		updateWeek();
	}

	/**
	 * Sets the contents of the upcoming Events JList
	 * @param eventList
	 */
	public void updateUpcomingEvents() {
		//ArrayList of the upcoming events - to be converted back to normal array later
		ArrayList<String> arrayListEvents = new ArrayList<String>();
		//Number of days to include in coming events
		int numDaysUpcoming = Preferences.numDaysUpcoming();

		//For each day within the numDaysUpcoming range
		for(int i = 0; i < numDaysUpcoming; i++) {
			//Get the corresponding date and add its list of assignments
			Date upcomingDate = currentDate.getUpcomingDate(i);
			Block[] blocks = Days.getBlocksOnDay(upcomingDate);
			for(Block b : blocks)
				arrayListEvents.addAll(controller.getAssignmentNames(upcomingDate, b));
		}

		//Turn ArrayList back into array
		String[] arrayEvents = arrayListEvents.toArray(new String[0]);
		//Set "Upcoming Events" to display the array
		events.setListData(arrayEvents);
	}

	/**
	 * Refreshes the current date, week label, and all the calendar buttons.
	 */
	public void updateWeek() {
		currentDate = new Date();
		labelWeek.setText(weekStartDate.toString(DateFormat.MEDIUM) + " - " + weekStartDate.getUpcomingDate(6).toString(DateFormat.MEDIUM));
		updateButtons();
		highlightCurDay();
		updateUpcomingEvents();
	}

	/**
	 * @return the Date object that represents the current day
	 */
	public static Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * Method which handles of of the ActionEvents recorded by ActionListeners
	 */
	@Override
	public void actionPerformed(ActionEvent e)  {
		Object src = e.getSource();

		//Left button on the calendar
		if (src.equals(buttonLeft)) {
			GUIButton.deselect();
			weekStartDate = weekStartDate.getUpcomingDate(-7);
			updateWeek();
		}

		//Right button on the calendar
		else if (src.equals(buttonRight)) {
			GUIButton.deselect();
			weekStartDate = weekStartDate.getUpcomingDate(7);
			updateWeek();
		}

		//User wants to add a Class
		else if (src.equals(menuItemAddCourse)) {
			if(addCourse == null)
				addCourse = new AddCourseWindow(this, controller);
		}


		else if (src.equals(buttonGradebook)) {
			System.out.println("Opened Gradebook");
		}

		else if (src.equals(buttonSettings)) {
			if(settings == null)
				settings = new SettingsWindow(this, controller);
		}

		else if (src.equals(menuItemCurrentWeek)) {
			weekStartDate = currentDate.getWeekStartDate();
			updateWeek();
		}
	}
}
