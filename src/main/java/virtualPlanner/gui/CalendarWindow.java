package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.reference.Colors;
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
public class CalendarWindow implements ActionListener {

	private static final Dimension MAIN_SIZE = new Dimension(1280, 720);

	/**Width of each individual column (day) in the calendar*/
	private static final int CALENDAR_COLUMN_WIDTH = 70;
	/**Height of the day of week labels at the top of each column*/
	private static final int CALENDAR_LABEL_HEIGHT = 20;

	/**Default Border which all buttons are instantiated with*/
	private static final Border DEFAULT_BORDER = BorderFactory.createEtchedBorder(1);
	/**Highlight Border used to indicate the last edited GUIButton which represents a block*/
	private static final Border HIGHLIGHTED_BORDER = BorderFactory.createEtchedBorder(1, Color.RED, Color.WHITE);

	/**Size for labelDate*/
	private static final Dimension DATE_SIZE = new Dimension(400, 100);

	/**
	 * Size of the upcoming events JList
	 * Note: Height is always overriden by JList's setVisibleRowCount method, hence height being 0
	 */
	private static final Dimension UPCOMING_EVENTS_SIZE = new Dimension(365, 0);

	/**Size of each individual block*/
	private static final Dimension BLOCK_SIZE = new Dimension(CALENDAR_COLUMN_WIDTH, 30);
	
	private static final Dimension LABEL_SIZE = new Dimension(CALENDAR_COLUMN_WIDTH, CALENDAR_LABEL_HEIGHT);

	/**Array which holds all the months - used as reference when displaying dates*/
	private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private static final String NO_UPCOMING_EVENTS_TEXT = "No Upcoming Events";
	
	/**The JFrame of this MainCalendarWindow instance*/
	private JFrame frame;
	
	/**Outermost of all nested JPanels which is directly added into the JFrame*/
	private JPanel mainPanel;
	/**JPanel which holds the main calendar for the window */
	private JPanel panelCalendar;
	/**JPanel which holds all of the extra features accessible from MainCalendarWindow*/
	private JPanel infoPanel;
	
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
	private CalendarButton[][] buttons;

	/**JList of UpcomingEvents*/
	private JList<String> events;
	/**JScrollPane which enables scrollable features for the events JList*/
	private JScrollPane eventsScrollPane;

	/**ArrayList of all the GUIButtons that are day of week labels (simply show the current day)*/
	private ArrayList<CalendarButton> dayOfWeekButtons;

	/**The controller for this JFrame.*/
	private GUIController controller;

	/**Today's date.*/
	private Date currentDate;

	/**The start date of the week which is being displayed currently.*/
	private Date weekStartDate;
	
	/**Reference to the last highlighted button so that it can be easily reset to normal*/
	private CalendarButton highlightedButton;
	
	/**
	 * Constructor: Initializes the MainCalendarWindow
	 */
	protected CalendarWindow(GUIController controller) {
		//GUIController
		this.controller = controller;
		
		//Date
		currentDate = new Date();
		weekStartDate = currentDate.getWeekStartDate();
		
		//Name - personalization
		frame = new JFrame("Virtual Planner - " + controller.getUserName());

		//Size
		frame.setSize(MAIN_SIZE);
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;

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
		buttons = new CalendarButton[7][];
		addComponents();

		//Refresh current date, week label, and all calendar buttons
		update();

		frame.setVisible(true);
	}

	/**
	 * Adds the main base components of the MainCalendarWindow 
	 */
	private void addComponents() {
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
	private void updateButtons() {
		
		//Remove the previous, outdated components
		panelCalendar.removeAll();
		
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

		//Add Weekday Labels
		dayOfWeekButtons = new ArrayList<CalendarButton>(7);
		for (int i = 0; i < Days.values().length; i++) {
			c.gridx = i;
			CalendarButton newButton = new CalendarButton(Days.values()[i].getName(), LABEL_SIZE);
			panelCalendar.add(newButton, c);
			dayOfWeekButtons.add(newButton);
		}

		//Add Blocks
		for(int i = 0; i < Days.values().length; i++) {
			c.ipady = 25;
			c.gridx = i;
			Days blockOrder = Days.values()[i];
			buttons[i] = new CalendarButton[blockOrder.getBlockCount()];
			for(int j = 0; j < blockOrder.getBlockCount(); j++) {
				c.gridy = j + 1;
				Block block = blockOrder.getBlock(j);
				CalendarButton button = new CalendarButton(BLOCK_SIZE, block, controller.getAssignments(weekStartDate.getUpcomingDate(i), block), controller.getCourse(block), weekStartDate.getUpcomingDate(i));
				panelCalendar.add(button, c);
				buttons[i][j] = button;
			}
		}

		//Sunday Large Blank Block
		c.gridx = Days.values().length-1;
		c.gridy = 1;
		c.gridheight = 10;
		c.fill = GridBagConstraints.VERTICAL;
		CalendarButton newButton = new CalendarButton("", BLOCK_SIZE);
		panelCalendar.add(newButton, c);
		
		panelCalendar.invalidate();
		panelCalendar.revalidate();
	}

	/**
	 * This method finds and highlights the day of week GUIButton that corresponds to the current day
	 */
	private void highlightCurDay() {
		if (currentDate.compareTo(weekStartDate) < 0 || currentDate.compareTo(weekStartDate.getUpcomingDate(7)) > 0)
			return;

		String curDay = currentDate.getDayOfWeek().toString();
		for(CalendarButton b : dayOfWeekButtons) {
			if(b.getText().equalsIgnoreCase(curDay)) {
				b.setBackground(Color.YELLOW);
			}
		}
	}

	/**
	 * Sets the contents of the upcoming Events JList
	 * @param eventList
	 */
	private void updateUpcomingEvents() {
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
		
		if(arrayEvents.length == 0){
			String[] temp = {NO_UPCOMING_EVENTS_TEXT};
			arrayEvents = temp;
		}
		
		//Set "Upcoming Events" to display the array
		events.setListData(arrayEvents);
	}

	/**
	 * Refreshes everything.
	 */
	protected void update() {
		currentDate = new Date();
		labelWeek.setText(weekStartDate.toString(DateFormat.MEDIUM) + " - " + weekStartDate.getUpcomingDate(6).toString(DateFormat.MEDIUM));
		updateButtons();
		highlightCurDay();
		updateUpcomingEvents();
		deselect();
	}
	
	/**
	 * Highlights a particular button.
	 * 
	 * @param button The button to select.
	 */
	private void select(CalendarButton button) {
		highlightedButton = button;
		button.setBorder(HIGHLIGHTED_BORDER);
	}

	/**
	 * De-Highlights the button that is currently highlighted.
	 */
	private void deselect() {
		if(highlightedButton != null) {
			highlightedButton.setBorder(DEFAULT_BORDER);
			highlightedButton = null;
		}
	}

	/**
	 * Method which handles of of the ActionEvents recorded by ActionListeners
	 */
	@Override
	public void actionPerformed(ActionEvent e)  {
		Object src = e.getSource();

		//Left button on the calendar
		if (src.equals(buttonLeft)) {
			weekStartDate = weekStartDate.getUpcomingDate(-7);
			update();
		}

		//Right button on the calendar
		else if (src.equals(buttonRight)) {
			weekStartDate = weekStartDate.getUpcomingDate(7);
			update();
		}

		//User wants to add a Class
		else if (src.equals(menuItemAddCourse)) {
			controller.openAddCourse();
		}


		else if (src.equals(buttonGradebook)) {
			System.out.println("Opened Gradebook");
		}

		else if (src.equals(buttonSettings)) {
			controller.openSettings();
		}

		else if (src.equals(menuItemCurrentWeek)) {
			weekStartDate = currentDate.getWeekStartDate();
			update();
		}
	}
	
	/**
	 * This class is used in place of JButton to provide extra functionality to the JButtons in the Calendar
	 * 
	 * @author KevinGao
	 * @author JeremiahDeGreeff
	 */
	@SuppressWarnings("serial")
	private class CalendarButton extends JButton implements ActionListener {

		/**ArrayList of all uncompleted assignments within this GUIButton*/
		private ArrayList<Assignment> assignments;

		/**The block that corresponds to this GUIButton*/
		private Block block;
		/**The course that corresponds to this GUIButton*/
		private Course course;
		/**The name and text of this GUIButton*/
		private String name;
		/**The Calendar Date on which this GUIButton exists*/
		private Date date;

		/**
		 * If true, this GUIButton represents a label for the day of week
		 * If false, this GUIButton represents a block on the schedule
		 */
		private boolean isDayLabel;

		/**
		 * Base constructor with call to super 
		 * Sets the universal defaults for all GUIButtons
		 */
		private CalendarButton(Dimension size, Color color) {
			super();
			this.setPreferredSize(size);
			this.setBackground(color);
			this.addActionListener(this);
			this.setOpaque(true);
			this.setFocusPainted(false);
			this.setBorder(DEFAULT_BORDER);
			this.setAlignmentX(CENTER_ALIGNMENT);
			this.setPreferredSize(BLOCK_SIZE);
		}

		/**
		 * Constructor for the GUIButtons in the Calendar that represent Blocks
		 * @param block the block that corresponds to the GUIButton
		 * @param courseID the courseID of the course 
		 * @param assignments an ArrayList of type Assignment which holds all existing Assignments for this GUIButton
		 * @param size the preferred size of this GUIButton
		 */
		public CalendarButton(Dimension size, Block block, ArrayList<Assignment> assignments, Course course, Date date) {
			this(size, Preferences.getColor(course == null ? -1 : course.getID()));
			this.name = block.getBlock().getAbbreviation() + (course == null ? "" : " - " + course.getAbbreviation());
			this.isDayLabel = false;
			this.block = block;
			this.assignments = assignments;
			this.course = course;
			this.date = date;
			this.setFont(Fonts.CALENDAR_BLOCK);
			this.setVerticalAlignment(SwingConstants.TOP);
			addAssignmentText();
		}

		/**
		 * Construction for the GUIButtons in the Calendar that are the day of week labels
		 * @param name
		 * @param size
		 * @param font
		 */
		public CalendarButton(String name, Dimension size) {
			this(size, Colors.DEFAULT);
			this.isDayLabel = true;
			this.setText(name);
			this.setFont(Fonts.CALENDAR_DAY);
		}
		
		private void addAssignmentText() {
			String assignmentString = "";
			if(assignments != null)
				for (Assignment a : assignments)
					assignmentString += "\n" + a.getName();
			setMultiLineText(assignmentString);
		}

		/**
		 * Enables multi-line text
		 * PRECONDITION: New lines are denotated by a "\n"
		 * @param text the text which this GUIButton will be set to display
		 */
		private void setMultiLineText(String text) {
			String newText = name + text;
			this.setText("<html><center>" + newText.replaceAll("\\n", "<br>") + "</center></html>");
		}

		/**
		 * actionPerformed method which handles all ActionEvents for GUIButtons
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			if (src instanceof CalendarButton) {	
				CalendarButton button = (CalendarButton)src;
				//Button is a Block
				if(!button.isDayLabel) {
					//Un-highlight the already highlighted block
					deselect();
					//Re-highlight the clicked GUIButton if it represents a class
					if(block.getBlock().isClass() && course != null) {
						select(button);
						//Create the New Assignments window for that GUIButton
						controller.openAddAssignment(name, currentDate, date, block, course);
					}
				}
			}
		}
		
	}
	
}
