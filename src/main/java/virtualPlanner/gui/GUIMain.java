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
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import virtualPlanner.reference.Days;
import virtualPlanner.reference.Fonts;
import virtualPlanner.util.Block;
import virtualPlanner.util.Date;

/**
 * This class represents a Prototype with no actionListeners/keyListeners.
 * 
 * @author Kevin
 * @author JeremiahDeGreeff
 */
@SuppressWarnings("serial")
public class GUIMain extends JFrame implements ActionListener {

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
	private static JFrame frame;

	//JPanels
	private static JPanel mainPanel, panelCalendar, infoPanel;

	//TODO: Resolution
	//	private final static int resolutionMultiplier = 2;
	private final static int calendarColumnWidth = 70;
	private final static int calendarLabelHeight = 20;

	//JMenuBar
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemFile, menuItemAddCourse;

	//JButtons
	private JButton buttonLeft, buttonRight, buttonGradebook, buttonSettings;

	//JLabels
	private JLabel labelWeek, labelDate;

	//Icons and Images
	private static BufferedImage imagePrev, imageNext, imageGradebook, imageSettings;

	//2D-Array representation of the Calendar GUIButtons
	private GUIButton[][] buttons;

	//JList and JScrollPane for upcomingEvents
	private JList<String> events;
	private JScrollPane eventsScrollPane;

	//Variable to keep track of they highlighted Day
	private static GUIButton highlightedDay;

	//ArrayList of all the GUIButtons that show the day
	private static ArrayList<GUIButton> dayOfWeekButtons = new ArrayList<GUIButton>();

	//Borders for the GUIButtons
	private static final Border defaultBorder = BorderFactory.createEtchedBorder(1);
	private static final Border highlightedBorder = BorderFactory.createEtchedBorder(1, Color.YELLOW, Color.WHITE);

	//Dimension for the date
	private static final Dimension dateSize = new Dimension(375, 95);

	//Dimension for the upcoming events list 
	private static final Dimension upcomingEventsSize = new Dimension(365, 0);

	//Dimension for each individual block
	private static final Dimension blockSize = new Dimension(calendarColumnWidth, 30);


	//Add new Course Window Settings
	private boolean hasAddCourseWindow;
	private static final Dimension courseWindowSize = new Dimension(320, 600);
	private static final Dimension fieldSize = new Dimension(80, 35);
	private JButton buttonAddCourse;
	private JTextField nameField, teacherField, abbreviationField;
	private JComboBox<String> blockComboBox;


	/**
	 * Constructor: Creates the main GUI
	 */
	public GUIMain(GUIController controller) {

		//Name
		super("Virtual Planner");

		//GUIController
		this.controller = controller;

		//Date
		currentDate = new Date();
		weekStartDate = currentDate.getWeekStartDate();

		//Variable to prevent multiple window instantiations
		hasAddCourseWindow = false;

		//Reference
		frame = this;

		//Size
		frame.setSize(1280, 720);

		//Color
		frame.setBackground(Color.WHITE);

		//Main Panel
		mainPanel = new JPanel();

		//Load Images
		loadImages();

		//JMenuBar
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menuFile = new JMenu("File");
		menuItemFile = new JMenuItem("There's nothing here ):");
		menuFile.add(menuItemFile);

		menuItemAddCourse = new JMenuItem("Add Course");
		menuItemAddCourse.addActionListener(this);

		menuBar.add(menuFile);
		menuBar.add(menuItemAddCourse);
		//Add components
		buttons = new GUIButton[7][];
		addComponents();

		updateWeek();

		//Frame
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Loads the images from the source folder
	 */
	private void loadImages() {
		try {
			imagePrev = ImageIO.read(getClass().getResource("previous.png"));
			imageNext = ImageIO.read(getClass().getResource("next.png"));
			imageGradebook = ImageIO.read(getClass().getResource("gradebookgreen.png"));
			imageSettings = ImageIO.read(getClass().getResource("settingsicon1.png"));
			System.out.println("Images loaded");
		} catch (Exception e) {
			System.out.println("Error loading images: " + e.getMessage());
		}
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
		buttonLeft.setIcon(new ImageIcon(imagePrev));
		buttonLeft.setOpaque(true);
		buttonLeft.setContentAreaFilled(false);
		buttonLeft.setBorderPainted(false);
		buttonLeft.addActionListener(this);
		buttonLeft.setFocusable(false);		

		buttonRight = new JButton();
		buttonRight.setIcon(new ImageIcon(imageNext));
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
		//		labelWeek.setForeground(Color.MAGENTA);
		labelWeek.setFont(Fonts.WEEK);

		//Level 1 -> labelWeek and Prev + Next buttons
		Box level1 = Box.createHorizontalBox();
		//		level1.add(Box.createHorizontalGlue());
		//		level1.add(Box.createHorizontalGlue());
		level1.add(buttonLeft);
		level1.add(Box.createHorizontalGlue());
		level1.add(labelWeek);
		level1.add(Box.createHorizontalGlue());
		level1.add(buttonRight);
		//		level1.add(Box.createHorizontalGlue());
		//		level1.add(Box.createHorizontalGlue());

		//Main Panel for Calendar
		panelCalendar = new JPanel();
		panelCalendar.setLayout(new GridBagLayout());
		panelCalendar.setBackground(Color.WHITE);

		//JPanel for the right of the calendar - the 'info' section
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		labelDate = new JLabel("<html>Saturday<br/>December 28, 2018</html>");
		labelDate.setOpaque(true);
		labelDate.setForeground(Color.BLACK);
		labelDate.setFont(Fonts.DATE);
		labelDate.setPreferredSize(dateSize);


		JPanel panelLabelDate = new JPanel();
		panelLabelDate.add(labelDate);

		//Upcoming Events
		JLabel labelEvents = new JLabel("Upcoming Events");
		labelEvents.setOpaque(true);
		labelEvents.setForeground(Color.BLACK);
		labelEvents.setFont(Fonts.LIST);
		JPanel panelLabelEvents = new JPanel();
		panelLabelEvents.add(labelEvents);

		//JList
		events = new JList<String>();
		events.setFont(Fonts.LIST);
		events.setForeground(Color.BLACK);
		events.setPreferredSize(upcomingEventsSize);
		String[] test = {"History Paper", "Chemistry Test", "Math Homework", "Writing Workshop Packet"};
		events.setListData(test);
		eventsScrollPane = new JScrollPane(events);
		JPanel panelEventsScrollPane = new JPanel();
		panelEventsScrollPane.add(eventsScrollPane);

		//Gradebook and Settings Buttons
		buttonGradebook = new JButton();
		buttonGradebook.setIcon(new ImageIcon(imageGradebook));
		buttonGradebook.setOpaque(true);
		buttonGradebook.setContentAreaFilled(false);
		buttonGradebook.setBorderPainted(false);
		buttonGradebook.addActionListener(this);
		buttonGradebook.setFocusable(false);
		buttonGradebook.setBackground(Color.cyan);

		buttonSettings = new JButton();
		buttonSettings.setIcon(new ImageIcon(imageSettings));
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
		Dimension labelSize = new Dimension(calendarColumnWidth, calendarLabelHeight);

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
				GUIButton button = new GUIButton(blockOrder.getBlock(j).getBlock().getAbbreviation(), blockOrder.getBlock(j), controller.getCourseID(block), controller.getAssignments(weekStartDate.getUpcomingDate(j), block), blockSize, Fonts.CALENDAR_BLOCK);
				panelCalendar.add(button, c);
				buttons[i][j] = button;
			}
		}

		highlightCurDay();
	}

	/**
	 * This method finds and highlights the day of week GUIButton that corresponds to the current day
	 */
	public static void highlightCurDay()
	{
		String curDay = currentDate.getDayOfWeek().toString();
		
		if(highlightedDay != null)
			highlightedDay.setBackground(Color.WHITE);

		for(GUIButton b : dayOfWeekButtons){
			System.out.println(b.getText() + " " + curDay);

			if(b.getText().equalsIgnoreCase(curDay)){
//				b.setBorder(highlightedBorder);
				b.setBackground(Color.YELLOW);
				highlightedDay = b;
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
		addCourseWindow.setSize(courseWindowSize);
		addCourseWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addCourseWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				hasAddCourseWindow = false;
				addCourseWindow.dispose();
			}
		});

		//Panel with organizing structure
		JPanel panelAddCourse = new JPanel();
		panelAddCourse.setLayout(new GridLayout(5,1));

		JLabel nameLabel = new JLabel("Course Name:");
		nameLabel.setFont(Fonts.ADD_CLASS);
		nameLabel.setForeground(Color.BLACK);
		JPanel panelNameLabel = new JPanel();
		panelNameLabel.add(nameLabel);

		nameField = new JTextField(20);
		nameField.setFont(Fonts.ADD_CLASS);
		nameField.setPreferredSize(fieldSize);
		nameField.setMinimumSize(fieldSize);
		nameField.setMaximumSize(fieldSize);	
		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		JLabel abbreviationLabel = new JLabel("Course Abbreviation:");
		abbreviationLabel.setFont(Fonts.ADD_CLASS);
		abbreviationLabel.setForeground(Color.BLACK);
		JPanel panelAbbreviationLabel = new JPanel();
		panelAbbreviationLabel.add(abbreviationLabel);

		abbreviationField = new JTextField(20);
		abbreviationField.setFont(Fonts.ADD_CLASS);
		abbreviationField.setPreferredSize(fieldSize);
		abbreviationField.setMinimumSize(fieldSize);
		abbreviationField.setMaximumSize(fieldSize);	
		JPanel panelAbbreviationField = new JPanel();
		panelAbbreviationField.add(abbreviationField);

		JLabel teacherLabel = new JLabel("Teacher:");
		teacherLabel.setFont(Fonts.ADD_CLASS);
		teacherLabel.setForeground(Color.BLACK);
		JPanel panelTeacherLabel = new JPanel();
		panelTeacherLabel.add(teacherLabel);

		teacherField = new JTextField(20);
		teacherField.setFont(Fonts.ADD_CLASS);
		teacherField.setPreferredSize(fieldSize);
		teacherField.setMinimumSize(fieldSize);
		teacherField.setMaximumSize(fieldSize);
		JPanel panelTeacherField = new JPanel();
		panelTeacherField.add(teacherField);

		JLabel blockLabel = new JLabel("Block:");
		blockLabel.setFont(Fonts.ADD_CLASS);
		blockLabel.setForeground(Color.BLACK);
		JPanel panelBlockLabel = new JPanel();
		panelBlockLabel.add(blockLabel);

		String[] blockList = {"A", "B", "C", "D", "E", "F", "G", "H", "L"};
		blockComboBox = new JComboBox<String>(blockList);
		blockComboBox.setFont(Fonts.ADD_CLASS);
		blockComboBox.setBackground(Color.WHITE);
		JPanel panelBlockComboBox = new JPanel();
		panelBlockComboBox.add(blockComboBox);

		buttonAddCourse = new JButton("Submit");
		buttonAddCourse.setFont(Fonts.ADD_CLASS);
		buttonAddCourse.addActionListener(this);
		buttonAddCourse.setBackground(Color.GREEN);
		buttonAddCourse.setForeground(Color.BLACK);
		JPanel panelButtonAddCourse = new JPanel();
		panelButtonAddCourse.add(buttonAddCourse);

		JLabel coursesLabel = new JLabel("Added Courses:");
		coursesLabel.setFont(Fonts.ADD_CLASS);
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
		mainVertical.add(panelBlockLabel);
		mainVertical.add(panelBlockComboBox);
		mainVertical.add(panelButtonAddCourse);
		mainVertical.add(panelCoursesLabel);

		//TODO: CURRENT COURSES
		JList<String> existingCourses = new JList<String>(controller.getCourseNames());
		existingCourses.setFont(Fonts.ADD_CLASS);
		existingCourses.setVisibleRowCount(6);

		JScrollPane coursesPane = new JScrollPane(existingCourses);

		JPanel panelCoursesPane = new JPanel();
		panelCoursesPane.add(coursesPane);

		mainVertical.add(panelCoursesPane);

		//		panelAddCourse.add();
		//
		//
		//		panelAddCourse.setLayout(new BoxLayout(panelAddCourse, BoxLayout.Y_AXIS));
		//		panelAddCourse.setAlignmentY(CENTER_ALIGNMENT);
		//
		//
		//
		//		nameField = new JTextField(20);
		//		nameField.setFont(addClassFont);
		//		nameField.setPreferredSize(fieldSize);
		//		nameField.setMinimumSize(fieldSize);
		//		nameField.setMaximumSize(fieldSize);
		//		nameField.setAlignmentX(RIGHT_ALIGNMENT);
		//
		//		teacherField = new JTextField(20);
		//		teacherField.setFont(addClassFont);
		//		teacherField.setPreferredSize(fieldSize);
		//		teacherField.setMinimumSize(fieldSize);
		//		teacherField.setMaximumSize(fieldSize);
		//		teacherField.setAlignmentX(RIGHT_ALIGNMENT);
		//
		//		Box box1 = Box.createHorizontalBox();
		//		box1.add(nameLabel);
		//		box1.add(nameField);
		//
		//		Box box2 = Box.createHorizontalBox();
		//		box2.add(teacherLabel);
		//		box2.add(teacherField);
		//
		//		panelAddCourse.add(box1);
		//		panelAddCourse.add(box2);
		addCourseWindow.add(mainVertical);


		addCourseWindow.setVisible(true);
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
		this.currentDate = new Date();
		labelWeek.setText(weekStartDate.toString(DateFormat.MEDIUM) + " - " + weekStartDate.getUpcomingDate(6).toString(DateFormat.MEDIUM));
		updateButtons();
	}

	/**
	 * Method which handles of of the ActionEvents recorded by ActionListeners
	 */
	public void actionPerformed(ActionEvent e)  {
		Object src = e.getSource();

		GUIMain.highlightCurDay();

		//Left button on the calendar
		if (src.equals(buttonLeft)) {
			weekStartDate = weekStartDate.getUpcomingDate(-7);
			updateWeek();
		}

		//Right button on the calendar
		else if (src.equals(buttonRight)) {
			weekStartDate = weekStartDate.getUpcomingDate(7);
			updateWeek();
		}

		//User wants to add a Class
		else if (src.equals(menuItemAddCourse)) {
			showAddCourseWindow();
			System.out.println("yet");
		}

		else if (src.equals(buttonAddCourse)){
			System.out.println("User wants to add course '" + nameField.getText() + "' (" + abbreviationField.getText() + ") with " + teacherField.getText() + " occurring on " + blockComboBox.getSelectedItem() + " blocks");
		}

		else if (src.equals(buttonGradebook)){
			System.out.println("Opened Gradebook");
		}

		else if (src.equals(buttonSettings)){
			System.out.println("Opened Settings");
		}
	}
	//TODO: CURRENT BUTTON
	//TODO ActionListener with bordercolors
}
