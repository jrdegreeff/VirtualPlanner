package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import virtualPlanner.backend.Assignment;

/**
 * This class is used in place of JButton to provide extra functionality to the JButtons in the Calendar
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton implements ActionListener
{

	private static final Border defaultBorder = BorderFactory.createEtchedBorder(1);
	private static final Border highlightedBorder = BorderFactory.createEtchedBorder(1, Color.RED, Color.WHITE);

	private static GUIButton highlightedButton;

	
	private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
	private boolean hasOptionsWindow;
	private String name;
	private int id;
	private String day;
	private boolean isDayLabel;

	/**
	 * Base constructor with call to super 
	 * Sets the universal settings for the buttons
	 */
	public GUIButton(String name)
	{
		super();
		hasOptionsWindow = false;
		this.addActionListener(this);
		this.name = name + "\n";
		this.setMultiLineText("");
		this.setOpaque(true);
		this.setFocusable(false);
		this.setBackground(Color.WHITE);
		this.setBorder(defaultBorder);
		highlightedButton = null;
//		this.setBorder(BorderFactory.createEtchedBorder(1, Color.RED, Color.RED));
	}

	/**
	 * Constructor for the JButtons in the Calendar that represent Blocks
	 * @param block
	 * @param id
	 * @param assignments
	 * @param size
	 */
	public GUIButton(String name, int id, ArrayList<Assignment> assignments, Dimension size, Font font)
	{
		this(name);
		this.id = id;
		this.assignments = assignments;
		this.setPreferredSize(size);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setFont(font);
		this.isDayLabel = false;
	}
	
	public GUIButton(String name, Dimension size, Font font)
	{
		this(name);
		this.setPreferredSize(size);
		this.setFont(font);
		this.isDayLabel = true;
	}
	
	/**
	 * Enables multi-line text
	 * PRECONDITION: New lines are denotated by a "\n"
	 * @param text
	 */
	public void setMultiLineText(String text)
	{
		String newText = name + text;
		this.setText("<html><center>" + newText.replaceAll("\\n", "<br>") + "</center></html>");
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	/**
	 * Creates a window of options for this GUIButton
	 */
	private void showUserOptions()
	{
		if (hasOptionsWindow)
			return;

		hasOptionsWindow = true;

		JFrame optionsWindow = new JFrame(name);

		optionsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		optionsWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				hasOptionsWindow = false;
				optionsWindow.dispose();
			}
		});
		
		optionsWindow.setSize(600, 600);
		optionsWindow.setVisible(true);
		
	}

	/**
	 * ActionListener for each individual JButtno
	 */
	public void actionPerformed(ActionEvent e) 
	{
		Object src = e.getSource();
		if (src instanceof GUIButton)
		{	
			GUIButton button = (GUIButton)src;
			
			//Button is a label for the day
			if(button.isDayLabel)
			{
				
			}
			
			//Button is a block
			else
			{
				//Reset old highlight
				if(highlightedButton != null)
					highlightedButton.setBorder(defaultBorder);
				highlightedButton = button;
				
				button.setBorder(highlightedBorder);
				
				//Option Window
				showUserOptions();
			}
		}
	}
}