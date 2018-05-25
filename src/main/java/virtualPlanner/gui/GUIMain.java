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

import javax.imageio.ImageIO;
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
	private Date currentDate;
	/**
	 * The start date of the week which is being displayed currently.
	 */
	private Date weekStartDate;

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
	private JButton buttonLeft, buttonRight;

	//JLabels
	private JLabel labelWeek, labelDay;

	//Icons and Images
	private static BufferedImage imagePrev, imageNext, imageGradebook, imageSettings;

	//2D-Array representation of the Calendar GUIButtons
	private GUIButton[][] buttons;

	//JList and JScrollPane for upcomingEvents
	private JList<String> events;
	private JScrollPane eventsScrollPane;

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
//			imageGradebook = ImageIO.read(getClass().getResource("gradebookgreen.png"));
			

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
//		infoPanel.setBackground(Color.BLUE);
		infoPanel.setAlignmentX(CENTER_ALIGNMENT);

		labelDay = new JLabel("Monday May 14, 2018");
		labelDay.setOpaque(true);
//		labelDay.setBackground(Color.PINK);
		labelDay.setForeground(Color.BLACK);
		labelDay.setFont(Fonts.DATE);
		labelDay.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(labelDay);

		//JLabel so that the spacing of labelDay is consistent
		JLabel spaceTaker = new JLabel("Monday May 14, 2018");
		spaceTaker.setOpaque(true);
		spaceTaker.setForeground(labelDay.getBackground());
//		spaceTaker.setBackground(Color.CYAN);
		spaceTaker.setFont(Fonts.DATE);
		spaceTaker.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(spaceTaker);

		
		//
		//Upcoming Events
		JLabel labelEvents = new JLabel("    Upcoming Events");
		labelEvents.setOpaque(true);
		labelEvents.setForeground(Color.BLACK);
		labelEvents.setFont(Fonts.LIST);
		labelEvents.setAlignmentX(LEFT_ALIGNMENT);
		infoPanel.add(labelEvents);

		//JList
		events = new JList<String>();
		events.setFont(Fonts.LIST);
		events.setForeground(Color.BLACK);
		String[] test = {"History Paper", "Chemistry Test", "Math Homework", "Writing Workshop Packet"};
		events.setListData(test);

		eventsScrollPane = new JScrollPane(events);
		System.out.println(infoPanel.getWidth());
		Dimension scrollPaneSize = new Dimension(100, 250);
		eventsScrollPane.setPreferredSize(scrollPaneSize);

		infoPanel.add(eventsScrollPane);

		//Final Box
		JPanel calendarVertical = new JPanel();
		calendarVertical.add(level1);
		calendarVertical.add(panelCalendar);

		calendarVertical.setOpaque(true);
		calendarVertical.setBackground(Color.white);
		calendarVertical.setLayout(new BoxLayout(calendarVertical, BoxLayout.Y_AXIS));

		JPanel temp2 = new JPanel();
		temp2.add(infoPanel);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(calendarVertical);
		mainPanel.add(Box.createHorizontalGlue());
		mainPanel.add(temp2);

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
			panelCalendar.add(new GUIButton(Days.values()[i].getName(), labelSize, Fonts.CALENDAR_DAY), c);
		}

		Dimension blockSize = new Dimension(calendarColumnWidth, 30);
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
	}
	//TODO: CURRENT BUTTON
	//TODO ActionListener with bordercolors
}
