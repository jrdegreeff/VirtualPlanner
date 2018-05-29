package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import virtualPlanner.reference.Fonts;
import virtualPlanner.util.Block;

/**
 * This class implements a JFrame from which the user can view current courses and add new ones
 * @author KevinGao
 *
 */
public class AddCourseWindow implements ActionListener {

	/**Size of the Add Course Window*/
	private static final Dimension COURSE_WINDOW_SIZE = new Dimension(320, 600);
	/**Size of the individual fields within the Add Course Window*/
	private static final Dimension FIELD_SIZE = new Dimension(80, 35);
	/**Size of the current courses JList within the Add Course Window*/
	private static final Dimension JLIST_SIZE = new Dimension(250, 35);

	/**The JFrame for this AddCourseWindow*/
	private JFrame frame;
	/**JButton for user to submit new course*/
	private JButton buttonAddCourse;
	/**The Name of the New Course to Add*/
	private JTextField nameField;
	/**The Abbreviation of the New Course to Add*/
	private JTextField abbreviationField;
	/**The Teacher of the New Course to Add*/
	private JTextField teacherField;
	/**JButton which enables the user to choose which blocks a course meets*/
	private JButton buttonChooseBlocks;
	/**GUIBlockPicker which enables the user to add special meeting times for the course*/
	private GUIBlockPicker blockPicker;
	/**JList of user's existing courses*/
	private JList<String> existingCourses;

	/**GUIController for this AddCourseWindow*/
	private GUIController controller;

	/**
	 * Constructor for the AddCourseWindow
	 * Instantiates and shows a new AddCourseWindow
	 */
	protected AddCourseWindow(GUIController controller) {

		//Set controller
		this.controller = controller;

		//Create new Window
		frame = new JFrame("Add Course");
		frame.setResizable(false);
		frame.setSize(COURSE_WINDOW_SIZE);

		//Overrride the close operation
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			//On window close
			public void windowClosing(WindowEvent windowEvent) {
				//Dispose of JFrame
				frame.dispose();
				//Inform middle-end that the JFrame has closed
				//Used to ensure that multiple AddCourseWindows aren't instantiated
				controller.addCourseClosed();
			}
		});

		//Add the components to this JFrame
		addComponents();

		frame.setVisible(true);
	}

	/**
	 * This method adds the Java Swing components to the AddCourseWindow JFrame
	 */
	private void addComponents() {

		//Panel with organizing structure
		JPanel panelAddCourse = new JPanel();
		panelAddCourse.setLayout(new GridLayout(5, 1));

		JLabel nameLabel = new JLabel("Course Name:");
		nameLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		nameLabel.setForeground(Color.BLACK);
		//Nested JPanels are the best way to ensure that the overall JFrame respects the sizes and fonts of the individual components 
		//This is because default JPanels have FlowLayout, the simplest LayoutManager which respects its inner component's preferred settings
		JPanel panelNameLabel = new JPanel();
		panelNameLabel.add(nameLabel);

		//Adding new Course: Name
		nameField = new JTextField(20);
		nameField.setFont(Fonts.CALENDAR_ADD_CLASS);
		nameField.setPreferredSize(FIELD_SIZE);
		//Nested JPanel
		JPanel panelNameField = new JPanel();
		panelNameField.add(nameField);

		//Adding new Course: Abbreviation
		JLabel abbreviationLabel = new JLabel("Course Abbreviation:");
		abbreviationLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		abbreviationLabel.setForeground(Color.BLACK);
		JPanel panelAbbreviationLabel = new JPanel();
		panelAbbreviationLabel.add(abbreviationLabel);

		abbreviationField = new JTextField(20);
		abbreviationField.setFont(Fonts.CALENDAR_ADD_CLASS);
		abbreviationField.setPreferredSize(FIELD_SIZE);

		JPanel panelAbbreviationField = new JPanel();
		panelAbbreviationField.add(abbreviationField);

		//Adding new Course: Teacher
		JLabel teacherLabel = new JLabel("Teacher:");
		teacherLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		teacherLabel.setForeground(Color.BLACK);
		JPanel panelTeacherLabel = new JPanel();
		panelTeacherLabel.add(teacherLabel);

		teacherField = new JTextField(20);
		teacherField.setFont(Fonts.CALENDAR_ADD_CLASS);
		teacherField.setPreferredSize(FIELD_SIZE);

		JPanel panelTeacherField = new JPanel();
		panelTeacherField.add(teacherField);

		//JButton which creates a popup window which allows the user to choose the Course's meeting times
		buttonChooseBlocks = new JButton("Choose Meeting Blocks");
		buttonChooseBlocks.setFont(Fonts.CALENDAR_ADD_CLASS);
		buttonChooseBlocks.setFocusPainted(false);
		buttonChooseBlocks.setOpaque(true);
		buttonChooseBlocks.setBorderPainted(false);
		buttonChooseBlocks.setBackground(Color.WHITE);
		buttonChooseBlocks.addActionListener(this);
		JPanel panelButtonChooseBlocks = new JPanel();
		panelButtonChooseBlocks.add(buttonChooseBlocks);
		//The actual popup window
		blockPicker = new GUIBlockPicker("Choose Meeting Blocks");

		//JButton that the user clicks when done editing fields
		buttonAddCourse = new JButton("Submit");
		buttonAddCourse.setFont(Fonts.CALENDAR_ADD_CLASS);
		buttonAddCourse.addActionListener(this);
		buttonAddCourse.setBackground(Color.GREEN);
		buttonAddCourse.setOpaque(true);
		buttonAddCourse.setBorderPainted(false);
		buttonAddCourse.setFocusPainted(false);
		buttonAddCourse.setForeground(Color.BLACK);
		frame.getRootPane().setDefaultButton(buttonAddCourse);
		JPanel panelButtonAddCourse = new JPanel();
		panelButtonAddCourse.add(buttonAddCourse);

		//Section of the AddCourseWindow which shows the user's already existing courses for reference
		//QOL feature which allows the user to see which courses he/she has already added in order to prevent user error
		JLabel coursesLabel = new JLabel("Added Courses:");
		coursesLabel.setFont(Fonts.CALENDAR_ADD_CLASS);
		coursesLabel.setForeground(Color.BLACK);
		coursesLabel.setOpaque(true);
		JPanel panelCoursesLabel = new JPanel();
		panelCoursesLabel.add(coursesLabel);

		//JList of the existing courses
		existingCourses = new JList<String>(controller.getCourseNames());
		existingCourses.setFont(Fonts.CALENDAR_ADD_CLASS);
		existingCourses.setPreferredSize(JLIST_SIZE);
		existingCourses.setVisibleRowCount(6);
		
		//JScrollPane to enable scrollable features for the existing courses JList
		JScrollPane coursesPane = new JScrollPane(existingCourses);
		JPanel panelCoursesPane = new JPanel();
		panelCoursesPane.add(coursesPane);

		//Overall main organizing vertical structure of this AddCourseWindow
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
		mainVertical.add(panelCoursesPane);

		frame.add(mainVertical);
	}

	/**
	 * Attempts to create a new course using the given user input
	 */
	private void addCourse() {
		//Array of Blocks which the user selected as meeting times
		ArrayList<Block> tempBlocks = blockPicker.getSelectedBlocks();
		Block[] blocks = tempBlocks.toArray(new Block[0]);

		//Attempt to add course through GUIController >> Store whether successful or not
		boolean successful = controller.addCourse(blocks, nameField.getText(), abbreviationField.getText(), teacherField.getText());
		
		//Update JList + Reset Fields
		if (successful) {
			existingCourses.setListData(controller.getCourseNames());
			nameField.setText("");
			abbreviationField.setText("");
			teacherField.setText("");
			blockPicker.clearCheckBoxes();
		} 
		//The only possible error is if the chosen blocks overlap with an existing course's blocks
		//Inform user of error
		else
			JOptionPane.showMessageDialog(null, "Blocks Overlap with other courses' blocks", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Method which handles ActionEvents for the AddCourseWindow
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		//User clicks the "Submit" JButton
		if (src.equals(buttonAddCourse)) {
			addCourse();
		}

		//User clicks the "Choose Meeting Times" JButton
		else if (src.equals(buttonChooseBlocks)) {
			blockPicker.setVisible(true);
		}
	}
}