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

public class AddCourseWindow implements ActionListener {

	GUIController controller;
	
	//Add Course Window Settings
	/**Size of the Add Course Window*/
	private static final Dimension COURSE_WINDOW_SIZE = new Dimension(320, 600);
	/**Size of the individual fields within the Add Course Window*/
	private static final Dimension FIELD_SIZE = new Dimension(80, 35);
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
	private JList<String> existingCourses;
	
	private JFrame frame;

	public AddCourseWindow(GUIController controller) {
		this.controller = controller;

		//Create new Window
		frame = new JFrame("Add Course");
		frame.setResizable(false);

		//Override default close operation
		frame.setSize(COURSE_WINDOW_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
				controller.addCourseClosed();
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
		buttonChooseBlocks.setOpaque(true);
		buttonChooseBlocks.setBorderPainted(false);
		buttonChooseBlocks.setBackground(Color.WHITE);
		buttonChooseBlocks.addActionListener(this);
		JPanel panelButtonChooseBlocks = new JPanel();
		panelButtonChooseBlocks.add(buttonChooseBlocks);
		blockPicker = new GUIBlockPicker("Choose Meeting Blocks");

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

		existingCourses = new JList<String>(controller.getCourseNames());

		existingCourses.setFont(Fonts.CALENDAR_ADD_CLASS);
		existingCourses.setVisibleRowCount(6);

		JScrollPane coursesPane = new JScrollPane(existingCourses);

		JPanel panelCoursesPane = new JPanel();
		panelCoursesPane.add(coursesPane);

		mainVertical.add(panelCoursesPane);

		frame.add(mainVertical);

		frame.setVisible(true);
	}
	
	private void addCourse() {
		ArrayList<Block> tempBlocks = blockPicker.getSelectedBlocks();
		Block[] blocks = tempBlocks.toArray(new Block[0]);

		boolean successful = controller.addCourse(blocks, nameField.getText(), abbreviationField.getText(), teacherField.getText());
		if (successful){
			existingCourses.setListData(controller.getCourseNames());
			nameField.setText("");
			abbreviationField.setText("");
			teacherField.setText("");
			blockPicker.clearCheckBoxes();
		}
		else
			JOptionPane.showMessageDialog(null, "Blocks Overlap with other courses' blocks", "Error", JOptionPane.ERROR_MESSAGE);

	}

	public void actionPerformed(ActionEvent e) {

		Object src = e.getSource();
		

		if (src.equals(buttonAddCourse)) {
			addCourse();
		}
		

		else if (src.equals(buttonChooseBlocks)){
			blockPicker.setVisible(true);
		}
	}

}
