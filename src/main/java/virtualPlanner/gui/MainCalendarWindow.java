package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import virtualPlanner.backend.Course;
import virtualPlanner.reference.Days;
import virtualPlanner.reference.Fonts;
import virtualPlanner.reference.Images;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * This class represents a Prototype with no actionListeners/keyListeners.
 * 
 * @author Kevin
 * @author JeremiahDeGreeff
 */
public class MainCalendarWindow implements ActionListener {

	/**
	 * The controller for this JFrame.
	 */
	private GUIController controller;
	/**
	 * Today's date.
	 */
	private static Date currentDate;
	/**
	 * The start date of the week which is being displayed currently.
	 */
	private static Date weekStartDate;

	//JFrame
	private JFrame frame;

	//JPanels
	private JPanel mainPanel, panelCalendar, infoPanel;

	private final static int CALENDAR_COLUMN_WIDTH = 70;
	private final static int CALENDAR_LABEL_HEIGHT = 20;

	//JMenuBar
	private JMenuBar menuBar;
	private JMenu menuFile, menuOptions;
	private JMenuItem menuItemAddCourse, menuItemCurrentWeek;

	//JButtons
	private JButton buttonLeft, buttonRight, buttonGradebook, buttonSettings;

	//JLabels
	private JLabel labelWeek, labelDate;

	//2D-Array representation of the Calendar GUIButtons
	private GUIButton[][] buttons;

	//JList and JScrollPane for upcomingEvents
	private JList<String> events;
	private JScrollPane eventsScrollPane;

	//ArrayList of all the GUIButtons that show the day
	private static ArrayList<GUIButton> dayOfWeekButtons = new ArrayList<GUIButton>();

	//Dimension for the date
	private static final Dimension DATE_SIZE = new Dimension(400, 125);

	//Dimension for the upcoming events list 
	private static final Dimension UPCOMING_EVENTS_SIZE = new Dimension(365, 0);

	//Dimension for each individual block
	private static final Dimension BLOCK_SIZE = new Dimension(CALENDAR_COLUMN_WIDTH, 30);

	//Dimension for the settings window
	private static final Dimension SETTINGS_SIZE = new Dimension(400, 500);

	//Dimension for the numUpcomingDays JTextField
	private static final Dimension UPCOMING_DAYS_SIZE = new Dimension(30, 25);

	private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	//Add new Course Window Settings
	private boolean hasAddCourseWindow;
	private static final Dimension COURSE_WINDOW_SIZE = new Dimension(320, 600);
	private static final Dimension FIELD_SIZE = new Dimension(80, 35);
	private JButton buttonAddCourse;
	private JTextField nameField, teacherField, abbreviationField;

	//Settings Window
	private JRadioButton buttonShowDateAssigned;
	private JRadioButton buttonShowDateDue;
	private JButton buttonChooseBlocks;
	private boolean hasSettingsWindow;
	private JTextField upcomingDaysField;


	/**
	 * Constructor: Creates the main GUI
	 */
	public MainCalendarWindow(GUIController controller) {

		//Name
		frame = new JFrame("Virtual Planner - " + controller.getUserName());

		//GUIController
		this.controller = controller;

		//Date
		currentDate = new Date();
		weekStartDate = currentDate.getWeekStartDate();

		//Variables to prevent multiple window instantiations
		hasAddCourseWindow = false;

		//Size
		frame.setSize(1280, 720);

		//Color
		frame.setBackground(Color.WHITE);

		//Main Panel
		mainPanel = new JPanel();

		//JMenuBar
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menuFile = new JMenu("File");

		menuOptions = new JMenu("Options");
		menuItemCurrentWeek = new JMenuItem("Go to Current Week");
		menuItemCurrentWeek.addActionListener(this);
		menuOptions.add(menuItemCurrentWeek);

		menuItemAddCourse = new JMenuItem("Add Course");
		menuItemAddCourse.addActionListener(this);

		menuBar.add(menuFile);
		menuBar.add(menuOptions);
		menuBar.add(menuItemAddCourse);

		//Add components
		buttons = new GUIButton[7][];
		addComponents();

		updateWeek();
		hasSettingsWindow = false;

		//Initialize Settings Components

		//Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Adds the main base components of the GUI 
	 */
	public void addComponents() {
		//Clear frame
		frame.getContentPane().removeAll();

		//Calendar 

		//Left and Right Calendar buttons
		buttonLeft = new JButton();
		buttonLeft.setIcon(new ImageIcon(Images.getPrevious()));
		buttonLeft.setOpaque(true);
		buttonLeft.setContentAreaFilled(false);
		buttonLeft.setBorderPainted(false);
		buttonLeft.addActionListener(this);
		buttonLeft.setFocusable(false);		

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

		//Level 1 -> labelWeek and Prev + Next buttons
		Box level1 = Box.createHorizontalBox();
		level1.add(buttonLeft);
		level1.add(Box.createHorizontalGlue());
		level1.add(labelWeek);
		level1.add(Box.createHorizontalGlue());
		level1.add(buttonRight);

		//Main Panel for Calendar
		panelCalendar = new JPanel();
		panelCalendar.setLayout(new GridBagLayout());
		panelCalendar.setBackground(Color.WHITE);

		//JPanel for the right of the calendar - the 'info' section
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

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

		//Upcoming Events
		JLabel labelEvents = new JLabel("Upcoming Events");
		labelEvents.setOpaque(true);
		labelEvents.setForeground(Color.BLACK);
		labelEvents.setFont(Fonts.CALENDAR_LIST);
		JPanel panelLabelEvents = new JPanel();
		panelLabelEvents.add(labelEvents);

		//JList
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
		buttonSettings.setOpaque(false);
		buttonSettings.setContentAreaFilled(false);
		buttonSettings.setBorderPainted(false);
		buttonSettings.addActionListener(this);
		buttonSettings.setFocusable(false);

		Box infoButtons = Box.createHorizontalBox();
		infoButtons.add(buttonGradebook);
		infoButtons.add(buttonSettings);

		//Final Organizing Structures
		JPanel calendarVertical = new JPanel();
		calendarVertical.add(level1);
		calendarVertical.add(panelCalendar);

		calendarVertical.setOpaque(true);
		calendarVertical.setBackground(Color.white);
		calendarVertical.setLayout(new BoxLayout(calendarVertical, BoxLayout.Y_AXIS));

		infoPanel.add(panelLabelDate);
		for(int i = 0; i < 5; i ++)
			infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(panelLabelEvents);
		infoPanel.add(panelEventsScrollPane);
		for(int i = 0; i < 35; i ++)
			infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(infoButtons);

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

		//Weekday Labels
		for (int i = 0; i < Days.values().length; i++) {
			c.gridx = i;
			GUIButton newButton = new GUIButton(Days.values()[i].getName(), labelSize, Fonts.CALENDAR_DAY);
			panelCalendar.add(newButton, c);
			dayOfWeekButtons.add(newButton);
		}

		//Blocks
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
	}

	/**
	 * This method finds and highlights the day of week GUIButton that corresponds to the current day
	 */
	public static void highlightCurDay() {	
		if (currentDate.compareTo(weekStartDate) < 0 || currentDate.compareTo(weekStartDate.getUpcomingDate(7)) > 0)
			return;

		String curDay = currentDate.getDayOfWeek().toString();
		for(GUIButton b : dayOfWeekButtons) {
			if(b.getText().equalsIgnoreCase(curDay)){
				b.setBackground(Color.YELLOW);
				b.revalidate();
				b.repaint();
				break;
			}
		}
	}

	/**
	 * This method creates a pop-up window which allows a user to add a Course
	 */
	private void showAddCourseWindow() {
		if (hasAddCourseWindow)
			return;

		hasAddCourseWindow = true;

		//Create new Window
		JFrame addCourseWindow = new JFrame("Add Course");

		//Override default close operation
		addCourseWindow.setSize(COURSE_WINDOW_SIZE);
		addCourseWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addCourseWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				hasAddCourseWindow = false;
				addCourseWindow.dispose();
			}
		});

		//Panel with organizing structure
		JPanel panelAddCourse = new JPanel();
		panelAddCourse.setLayout(new GridLayout(5,1));

		JLabel nameLabel = new JLabel("Course Name:");
		nameLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		nameLabel.setForeground(Color.BLACK);
		JPanel panelNameLabel = new JPanel();
		panelNameLabel.add(nameLabel);

		nameField = new JTextField(20);
		nameField.setFont(Fonts.CALENDAR_ADD_CLASS);
		nameField.setPreferredSize(FIELD_SIZE);
		nameField.setMinimumSize(FIELD_SIZE);
		nameField.setMaximumSize(FIELD_SIZE);

		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		JLabel abbreviationLabel = new JLabel("Course Abbreviation:");
		abbreviationLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		abbreviationLabel.setForeground(Color.BLACK);
		JPanel panelAbbreviationLabel = new JPanel();
		panelAbbreviationLabel.add(abbreviationLabel);

		abbreviationField = new JTextField(20);
		abbreviationField.setFont(Fonts.CALENDAR_ADD_CLASS);
		abbreviationField.setPreferredSize(FIELD_SIZE);
		abbreviationField.setMinimumSize(FIELD_SIZE);
		abbreviationField.setMaximumSize(FIELD_SIZE);

		JPanel panelAbbreviationField = new JPanel();
		panelAbbreviationField.add(abbreviationField);

		JLabel teacherLabel = new JLabel("Teacher:");
		teacherLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		teacherLabel.setForeground(Color.BLACK);
		JPanel panelTeacherLabel = new JPanel();
		panelTeacherLabel.add(teacherLabel);

		teacherField = new JTextField(20);
		teacherField.setFont(Fonts.CALENDAR_ADD_CLASS);
		teacherField.setPreferredSize(FIELD_SIZE);
		teacherField.setMinimumSize(FIELD_SIZE);
		teacherField.setMaximumSize(FIELD_SIZE);

		JPanel panelTeacherField = new JPanel();
		panelTeacherField.add(teacherField);

		buttonChooseBlocks = new JButton("Choose Meeting Blocks");
		buttonChooseBlocks.setFont(Fonts.CALENDAR_ADD_CLASS);
		buttonChooseBlocks.setFocusPainted(false);
		buttonChooseBlocks.setBackground(Color.WHITE);
		buttonChooseBlocks.addActionListener(this);
		JPanel panelButtonChooseBlocks = new JPanel();
		panelButtonChooseBlocks.add(buttonChooseBlocks);

		buttonAddCourse = new JButton("Submit");
		buttonAddCourse.setFont(Fonts.CALENDAR_ADD_CLASS);
		buttonAddCourse.addActionListener(this);
		buttonAddCourse.setBackground(Color.GREEN);
		buttonAddCourse.setForeground(Color.BLACK);
		addCourseWindow.getRootPane().setDefaultButton(buttonAddCourse);
		JPanel panelButtonAddCourse = new JPanel();
		panelButtonAddCourse.add(buttonAddCourse);

		JLabel coursesLabel = new JLabel("Added Courses:");
		coursesLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		coursesLabel.setForeground(Color.BLACK);
		coursesLabel.setOpaque(true);
		JPanel panelCoursesLabel = new JPanel();
		panelCoursesLabel.add(coursesLabel);

		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelNameLabel);
		mainVertical.add(panelNameField);
		mainVertical.add(panelAbbreviationLabel);
		mainVertical.add(panelAbbreviationField);
		mainVertical.add(panelTeacherLabel);
		mainVertical.add(panelTeacherField);
		mainVertical.add(panelButtonChooseBlocks);
		mainVertical.add(panelButtonAddCourse);
		mainVertical.add(panelCoursesLabel);

		//TODO: NULL POINTER
		//		JList<String> existingCourses = new JList<String>(controller.getCourseNames());
		JList<String> existingCourses = new JList<String>();

		existingCourses.setFont(Fonts.CALENDAR_ADD_CLASS);
		existingCourses.setVisibleRowCount(6);

		JScrollPane coursesPane = new JScrollPane(existingCourses);

		JPanel panelCoursesPane = new JPanel();
		panelCoursesPane.add(coursesPane);

		mainVertical.add(panelCoursesPane);

		addCourseWindow.add(mainVertical);

		addCourseWindow.setVisible(true);
	}

	/**
	 * This method creates a window which allows the user to set preferences 
	 */
	private void showSettingsWindow() {

		if(hasSettingsWindow)
			return;

		hasSettingsWindow = true;

		JFrame settingsFrame = new JFrame();
		settingsFrame.setSize(SETTINGS_SIZE);
		settingsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		settingsFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try{
					Preferences.setNumDaysUpcoming(Integer.parseInt(upcomingDaysField.getText()));
				} catch (Exception e){
					JOptionPane.showMessageDialog(null, "Invalid Input for Upcoming Days", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ArrayList<GUISampleColorButton> buttons = GUISampleColorButton.getButtons();
				for(GUISampleColorButton b: buttons)
					Preferences.addColor(b.getCourse().getID(), b.getBackground());
				hasSettingsWindow = false;
				settingsFrame.dispose();
			}
		});

		JLabel blockLabel = new JLabel("Course Colors:");
		blockLabel.setFont(Fonts.CALENDAR_SETTINGS);
		blockLabel.setForeground(Color.BLACK);
		JPanel panelBlockLabel = new JPanel();
		panelBlockLabel.add(blockLabel);

		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelBlockLabel);

		ArrayList<GUISampleColorButton> buttons = GUISampleColorButton.getButtons();
		for (GUISampleColorButton b : buttons) {
			JPanel tempPanel = new JPanel();
			tempPanel.add(b);
			mainVertical.add(tempPanel);
		}

		buttonShowDateAssigned = new JRadioButton("Show Assignment on Assigned Date");
		buttonShowDateDue = new JRadioButton("Show Assignment on Due Date");
		buttonShowDateAssigned.setFont(Fonts.CALENDAR_SETTINGS);
		buttonShowDateDue.setFont(Fonts.CALENDAR_SETTINGS);
		JPanel panelButtonShowDateAssigned = new JPanel();
		panelButtonShowDateAssigned.add(buttonShowDateAssigned);
		JPanel panelButtonShowDateDue = new JPanel();
		panelButtonShowDateDue.add(buttonShowDateDue);
		ButtonGroup group = new ButtonGroup();
		group.add(buttonShowDateAssigned);
		group.add(buttonShowDateDue);

		mainVertical.add(panelButtonShowDateAssigned);
		mainVertical.add(panelButtonShowDateDue);

		//Number of days of upcoming events
		JLabel numDaysLabel = new JLabel("Number of Days 'Upcoming Events' shows");
		numDaysLabel.setFont(Fonts.CALENDAR_SETTINGS);
		JPanel panelNumDaysLabel = new JPanel();
		panelNumDaysLabel.add(numDaysLabel);

		upcomingDaysField = new JTextField("" + Preferences.numDaysUpcoming());
		upcomingDaysField.setPreferredSize(UPCOMING_DAYS_SIZE);
		upcomingDaysField.setFont(Fonts.CALENDAR_SETTINGS);
		upcomingDaysField.setHorizontalAlignment(JTextField.CENTER);
		JPanel panelUpcomingDaysField = new JPanel();
		panelUpcomingDaysField.add(upcomingDaysField);
		mainVertical.add(panelNumDaysLabel);
		mainVertical.add(panelUpcomingDaysField);

		settingsFrame.add(mainVertical);
		settingsFrame.setVisible(true);
	}

	/**
	 * 
	 */
	private void addCourse() {
		controller.addCourse(null, nameField.getText(), abbreviationField.getText(), teacherField.getText());
		//TODO JUST THIS COURSE
		//		new GUISampleColorButton(abbreviationField.getText(), course);
	}

	/**
	 * Sets the contents of the upcoming Events JList
	 * @param eventList
	 */
	public void setEventsList(String[] eventList) {
		events.setListData(eventList);
	}

	/**
	 * Refreshes the current date, week label, and all the calendar buttons.
	 */
	public void updateWeek() {
		currentDate = new Date();
		labelWeek.setText(weekStartDate.toString(DateFormat.MEDIUM) + " - " + weekStartDate.getUpcomingDate(6).toString(DateFormat.MEDIUM));
		updateButtons();
		highlightCurDay();
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
			showAddCourseWindow();
		}

		else if (src.equals(buttonAddCourse)) {
			addCourse();
		}

		else if (src.equals(buttonGradebook)) {
			System.out.println("Opened Gradebook");
		}

		else if (src.equals(buttonSettings)) {
			showSettingsWindow();
		}

		else if (src.equals(buttonChooseBlocks)){
			new GUIBlockPicker("Choose Meeting Blocks");
		}

		else if (src.equals(menuItemCurrentWeek)) {
			weekStartDate = currentDate.getWeekStartDate();
			updateWeek();
		}
	}
}
