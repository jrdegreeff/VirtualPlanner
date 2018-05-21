package virtualPlanner.gui;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class GUIButton extends JButton
{
	public GUIButton(String name)
	{
		super();
		String textWrap = "Text\nWrap\nV1";
		this.setText("<html>" + textWrap.replaceAll("\\n", "<br>") + "</html>");
	}
	
	/**
	 * Enables multi-line text
	 * PRECONDITION: New lines are denotated by a "\n"
	 * @param text
	 */
	public void setMultiLineText(String text)
	{
		this.setText("<html>" + text.replaceAll("\\n", "<br>") + "</html>");
	}
}