package virtualPlanner.gui;

import java.util.ArrayList;

import javax.swing.JButton;

/**
 * This class represents individual blocks/button objects within the calendar
 * @author Kevin
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton
{
	
	private ArrayList<String> activities = new ArrayList<String>();
	
	//TODO: Multiple Line JButtons
	/**
	 * Constructor for the GUIButton
	 * @param name the text which this GUIButton will show
	 */
	public GUIButton(String name)
	{
		super(name);		
	}
	
	/**
	 * Returns the Text which this GUIButton shows
	 */
	public String getText()
	{
		return this.getText();
	}
	
	/**
	 * Sets the Text which this GUIButton shows
	 */
	public void setText(String text)
	{
		//TODO: Automatic update system
		this.setText(text);
	}
	
	/**
	 * Adds the event to the list of activities, updates the GUIButton text
	 */
	public boolean addEvent(String text)
	{
		activities.add(text);
		//autoSetText();
		return true;
	}
	
	/**
	 * Removes the given event from the activities list and the GUIButton
	 * @return the removed object
	 */
	public String removeEvent(String text)
	{
		int index = activities.indexOf(text);
		if (index == -1)
			return null;
		
		String result = activities.get(index);
		//autoSetText();
		return result;
	}
	
	
}
