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

@SuppressWarnings("serial")
public class GUISampleColorButton extends JButton implements ActionListener {

	private static final Dimension buttonSize = new Dimension(30,30);
	private static ArrayList<GUISampleColorButton> buttons = new ArrayList<GUISampleColorButton>();
	private Course course;
	
	public GUISampleColorButton(String name, Course course) {
		super(name);
		this.setFocusPainted(false);
		this.setFont(Fonts.CALENDAR_BLOCK);
		this.setBackground(Color.WHITE);
		this.addActionListener(this);
		this.course = course;
		buttons.add(this);
	}

	public static ArrayList<GUISampleColorButton> getButtons() {
		return buttons;
	}
	
	public Course getCourse(){
		return this.course;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color c = JColorChooser.showDialog(this, this.getText() + " Block Color", this.getBackground());
		if (c == null)
			return;
			
		this.setBackground(c);
	}

}
