package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import virtualPlanner.backend.Course;
import virtualPlanner.reference.Fonts;
import virtualPlanner.reference.Preferences;

public class SettingsWindow {

	private MainCalendarWindow parent;

	private GUIController controller;

	private JFrame frame;

	/**Size of the settings window*/
	private static final Dimension SETTINGS_SIZE = new Dimension(400, 500);

	/**Size of the numUpcomingDays JTextField*/
	private static final Dimension UPCOMING_DAYS_SIZE = new Dimension(30, 25);
	
	/**Size of this specific GUISampleColorButton*/
	private static final Dimension BUTTON_SIZE = new Dimension(60,30);

	/**JRadioButton to customize whether assignments show on the Date assigned*/
	private JRadioButton buttonShowDateAssigned;
	/**JRadioButton to customize whether assignments show on the Date due*/
	private JRadioButton buttonShowDateDue;
	/**Field which represents the number of days the Upcoming Events JList show include*/
	private JTextField upcomingDaysField;

	private ArrayList<GUISampleColorButton> buttons;

	public SettingsWindow(MainCalendarWindow parent, GUIController controller) {
		this.parent = parent;
		this.controller = controller;

		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(SETTINGS_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					Preferences.setNumDaysUpcoming(Integer.parseInt(upcomingDaysField.getText()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Input for Upcoming Days", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				for(GUISampleColorButton b: buttons) {
					System.out.println(b.getBackground());
					Preferences.addColor(b.getCourse().getID(), b.getBackground());
				}
				frame.dispose();
				parent.settingsClosed();
			}
		});

		JLabel blockLabel = new JLabel("Course Colors:");
		blockLabel.setFont(Fonts.CALENDAR_SETTINGS);
		blockLabel.setForeground(Color.BLACK);
		JPanel panelBlockLabel = new JPanel();
		panelBlockLabel.add(blockLabel);

		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelBlockLabel);

		Course[] courses = controller.getAllCourses();

		buttons = new ArrayList<GUISampleColorButton>();
		for (Course course : courses) {
			GUISampleColorButton b = new GUISampleColorButton(course);
			b.setBackground(Preferences.getColor(course.getID()));
			buttons.add(b);
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

		frame.add(mainVertical);
		frame.setVisible(true);
	}
	
	/**
	 * This class represents an extension to the javax.swing.JButton class with added functionality for displaying colors and choosing colors
	 * Used in the Settings JFrame, where the user can choose custom colors for each course 
	 * @author KevinGao
	 *
	 */
	@SuppressWarnings("serial")
	private class GUISampleColorButton extends JButton implements ActionListener {
		
		/**Course which this GUISampleColorButton corresponds to*/
		private Course course;
		
		/**
		 * Constructor for GUISampleColorButton
		 * Sets all the defaults of the butotn
		 * @param name text for the GUISampleColorButton to show
		 * @param course which corresponds to this GUISampleColorButton
		 */
		public GUISampleColorButton(Course course) {
			super(course.getName());
			this.setFocusPainted(false);
			this.setFont(Fonts.CALENDAR_BLOCK);
			this.setBackground(Color.WHITE);
			this.setPreferredSize(BUTTON_SIZE);
			this.addActionListener(this);
			this.course = course;
		}
		
		/**
		 * @return the course which corresponds to this GUISampleColorButton
		 */
		public Course getCourse() {
			return this.course;
		}

		/**
		 * actionPerformed method which handles the ActionEvents for all GUISampleColorButtons
		 */
		public void actionPerformed(ActionEvent e) {
			//Show a color palatte and save the user's choice
			Color c = JColorChooser.showDialog(this, this.getText() + " Block Color", this.getBackground());
			
			//If the user cancelled out, do nothing
			if (c == null)
				return;
			
			//If the user selected a color, update the color
			this.setBackground(c);
		}

	}

}
