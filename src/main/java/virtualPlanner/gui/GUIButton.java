package virtualPlanner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUIButton extends JButton implements ActionListener
{

	private ArrayList<Object> assignments = new ArrayList<Object>();
	private boolean hasOptionsWindow;
	private String block;

	//TODO: Move calls to constructor
	public GUIButton(String block)
	{
		super();
		hasOptionsWindow = false;
		this.addActionListener(this);
		this.block = block + "\n";
		this.setMultiLineText("");
	}

	/**
	 * Enables multi-line text
	 * PRECONDITION: New lines are denotated by a "\n"
	 * @param text
	 */
	public void setMultiLineText(String text)
	{
		String newText = block + text;
		this.setText("<html>" + newText.replaceAll("\\n", "<br>") + "</html>");
		this.setAlignmentX(CENTER_ALIGNMENT);
	}

	/**
	 * Creates a window of options for this GUIButton
	 * 
	 */
	private void showUserOptions()
	{
		if (hasOptionsWindow)
			return;

		hasOptionsWindow = true;

		JFrame optionsWindow = new JFrame("Options");

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
		showUserOptions();
		System.out.println("Called1");
	}
}