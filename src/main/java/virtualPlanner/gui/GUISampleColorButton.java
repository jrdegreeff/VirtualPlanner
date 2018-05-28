package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import virtualPlanner.backend.Course;
import virtualPlanner.reference.Fonts;

/**
 * This class represents an extension to the javax.swing.JButton class with added functionality for displaying colors and choosing colors
 * Used in the Settings JFrame, where the user can choose custom colors for each course 
 * @author KevinGao
 *
 */
@SuppressWarnings("serial")
public class GUISampleColorButton extends JButton implements ActionListener {

	/**Size of this specific GUISampleColorButton*/
	private static final Dimension BUTTON_SIZE = new Dimension(60,30);
	
	/**ArrayList of all GUISampleColorButton instances*/
	private static ArrayList<GUISampleColorButton> buttons = new ArrayList<GUISampleColorButton>();
	
	/**Course which this GUISampleColorButton corresponds to*/
	private Course course;
	
	/**
	 * Constructor for GUISampleColorButton
	 * Sets all the defaults of the butotn
	 * @param name text for the GUISampleColorButton to show
	 * @param course which corresponds to this GUISampleColorButton
	 */
	public GUISampleColorButton(String name, Course course) {
		super(name);
		this.setFocusPainted(false);
		this.setFont(Fonts.CALENDAR_BLOCK);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(BUTTON_SIZE);
		this.addActionListener(this);
		this.course = course;
		buttons.add(this);
	}

	/**
	 * @return an ArrayList of all GUISampleColorButton instances
	 */
	public static ArrayList<GUISampleColorButton> getButtons() {
		return buttons;
	}
	
	/**
	 * @return the course which corresponds to this GUISampleColorButton
	 */
	public Course getCourse(){
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
