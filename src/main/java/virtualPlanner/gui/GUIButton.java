package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import virtualPlanner.backend.Assignment;
import virtualPlanner.backend.Course;
import virtualPlanner.util.Date;
import virtualPlanner.reference.Preferences;
import virtualPlanner.util.Block;

/**
 * This class is used in place of JButton to provide extra functionality to the JButtons in the Calendar
 * @author KevinGao
 *
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton implements ActionListener {

	/**The GUIController for this GUIButton*/
	private GUIController controller;

	/**Default Border which all buttons are instantiated with*/
	private static final Border DEFAULT_BORDER = BorderFactory.createEtchedBorder(1);
	/**Highlight Border used to indicate the last edited GUIButton which represents a block*/
	private static final Border HIGHLIGHTED_BORDER = BorderFactory.createEtchedBorder(1, Color.RED, Color.WHITE);

	/**Reference to the last highlighted button so that it can be easily reset to normal*/
	private static GUIButton highlightedButton;

	/**Reference to Parent Window*/
	private MainCalendarWindow mainWindow;

	/**Reference to this GUIButton's add assignment window*/
	private AssignmentWindow assignmentWindow;

	/**ArrayList of all uncompleted assignments within this GUIButton*/
	private ArrayList<Assignment> assignments;

	/**The block that corresponds to this GUIButton*/
	private Block block;
	/**The course that corresponds to this GUIButton*/
	private Course course;
	/**The name and text of this GUIButton*/
	private String name;
	/**Variable used to track the color of this GUIButton*/
	private Color color;
	/**The Calendar Date on which this GUIButton exists*/
	private Date date;

	/**
	 * If true, this GUIButton represents a label for the day of week
	 * If false, this GUIButton represnets a block on the schedule
	 */
	private boolean isDayLabel;

	//The following variables must have references here in order to be able to be accessed in actionPerformed.


	/**
	 * Base constructor with call to super 
	 * Sets the universal defaults for all GUIButtons
	 */
	public GUIButton(String name) {
		super();
		this.addActionListener(this);
		this.name = name;
		this.setOpaque(true);
		this.setFocusPainted(false);
		this.setBackground(color);
		this.setBorder(DEFAULT_BORDER);
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	/**
	 * Constructor for the GUIButtons in the Calendar that represent Blocks
	 * @param block the block that corresponds to the GUIButton
	 * @param courseID the courseID of the course 
	 * @param assignments an ArrayList of type Assignment which holds all existing Assignments for this GUIButton
	 * @param size the preferred size of this GUIButton
	 */
	public GUIButton(String name, Block block, int courseID, ArrayList<Assignment> assignments, Dimension size, Font font, GUIController controller, Course course, MainCalendarWindow mainWindow, Date date) {
		this(name);
		this.block = block;
		this.color = Preferences.getColor(courseID);
		this.setBackground(color);
		this.assignments = assignments;
		this.setPreferredSize(size);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setFont(font);
		this.isDayLabel = false;
		this.setMultiLineText("");
		this.controller = controller;
		this.course = course;
		this.mainWindow = mainWindow;
		this.date = date;
		updateButton();
	}

	/**
	 * Construction for the GUIButtons in the Calendar that are the day of week labels
	 * @param name
	 * @param size
	 * @param font
	 */
	public GUIButton(String name, Dimension size, Font font) {
		this(name);
		this.setPreferredSize(size);
		this.setFont(font);
		this.isDayLabel = true;
		this.setText(name);
	}
	
	public void closeAssignmentWindow(){
		assignmentWindow = null;
		mainWindow.update();
	}

	/**
	 * Enables multi-line text
	 * PRECONDITION: New lines are denotated by a "\n"
	 * @param text the text which this GUIButton will be set to display
	 */
	public void setMultiLineText(String text) {
		String newText = name + text;
		this.setText("<html><center>" + newText.replaceAll("\\n", "<br>") + "</center></html>");
	}


	/**
	 * Updates the text that shows on this GUIButton
	 */
	private void updateButton() {
		if (assignments == null)
			return;
		String result = "";
		for (Assignment a: assignments)
			result += "\n" + a.getName();
		this.setMultiLineText(result);
	}

	/**
	 * Highlights a particular button.
	 * 
	 * @param button The button to select.
	 */
	public static void select(GUIButton button) {
		highlightedButton = button;
		button.setBorder(HIGHLIGHTED_BORDER);
	}

	/**
	 * De-Highlights the button that is currently highlighted.
	 */
	public static void deselect() {
		if(highlightedButton != null) {
			highlightedButton.setBorder(DEFAULT_BORDER);
			highlightedButton = null;
		}
	}

	/**
	 * actionPerformed method which handles all ActionEvents for GUIButtons
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		//All other GUIButtons
		if (src instanceof GUIButton) {	
			GUIButton button = (GUIButton)src;

			//Button is a label for the day
			if(button.isDayLabel) {
				//Do Nothing
			}

			//Button is a Block
			else {
				//Un-highlight the already highlighted block
				deselect();
				//Re-highlight the clicked GUIButton if it represents a class
				if(block.getBlock().isClass()) {
					select(button);
					//Create the New Assignments window for that GUIButton
					if(course != null && assignmentWindow == null)
						assignmentWindow = new AssignmentWindow(name, assignments, date, block, course, controller);
				}
			}
		}
	}
}
