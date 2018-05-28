package virtualPlanner.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import virtualPlanner.reference.Blocks;
import virtualPlanner.reference.Days;
import virtualPlanner.util.Block;

/**
 * This class creates a pop-up window which holds a model of the MX block-schedule
 * This window is used during the adding of courses to allow the user to input special circumstances for class meeting times (ie. Drop blocks, L blocks, Middle-of-day double blocks)
 * @author KevinGao
 *
 */
public class GUIBlockPicker {

	/**The JFrame of this GUIBlockPicker instance*/
	private JFrame frame;

	/**Size of the window*/
	private static final Dimension BLOCK_PICKER_SIZE = new Dimension(500, 400);

	/**ArrayList of the GUICheckBoxes*/
	private ArrayList<GUICheckBox[]> checkBoxes = new ArrayList<GUICheckBox[]>();

	//	/**The GUICheckBoxes which represent Blocks*/
	//	private GUICheckBox monC, monF, monD, monE, monG, monB, monA, monL, tueD, tueA, tueC, tueB, tueH, tueF, tueE, tueL, wedB, wedC, wedA, wedF, wedG, thuE, thuD, thuH, thuF, thuB, thuC, thuL, friF, friE, friB, friC, friG, friA, friD, friL, satA, satH, satE, satD;


	/**
	 * Constructor for the GUIBlockPicker which creates a BlockPicker Window
	 * @param name the title of the JFrame
	 */
	public GUIBlockPicker(String name) {

		//Frame Settings
		frame = new JFrame(name);
		frame.setSize(BLOCK_PICKER_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		//Override close operation
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				getSelectedBlocks();
				frame.dispose();
			}
		});
		frame.setResizable(false);

		//Instantiate all the CheckBoxes
		addCheckBoxes();

		//Retrieve and show the JPanel which holds the model calendar
		frame.add(getModelCalendar());

		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * @return the blocks that correspond to the currently selected GUICheckBoxes
	 */
	public ArrayList<Block> getSelectedBlocks() {
		//Resulting ArrayList to return
		ArrayList<Block> result = new ArrayList<Block>();

		//For each checkBox
		for(GUICheckBox[] row : checkBoxes)
			for(GUICheckBox c : row)
				//If it is selected, add the block to the result
				if(c != null && c.isSelected())
					result.add(c.getBlock());

		return result;
	}

	/**
	 * This method instantiates the GUICheckBoxes, and corresponds them to their appropriate Blocks and Days
	 */
	private void addCheckBoxes() {
		// Loop through days in regular schedule.
		for(int i = 0; i < Days.values().length; i++) {
			Days day = Days.values()[i];
			for(int j = 0; j < day.getBlockCount(); j++) {
				if(checkBoxes.size() <= j)
					checkBoxes.add(new GUICheckBox[6]);
				Block block = day.getBlock(j);
				checkBoxes.get(j)[i] = new GUICheckBox(block.getBlock().getAbbreviation(), block, block.getBlock().isClass());
			}
		}
	}

	/**
	 * Adds the GUICheckBoxes to their correct locations within the JPanel
	 * @return the JPanel with all of the GUICheckBoxes modeling the Block Schedule
	 */
	private JPanel getModelCalendar() {

		//JPanel to return
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(checkBoxes.size() + 1, 6));

		//First Row: Day of Week Labels
		result.add(new JLabel("Mon", JLabel.CENTER));
		result.add(new JLabel("Tue", JLabel.CENTER));
		result.add(new JLabel("Wed", JLabel.CENTER));
		result.add(new JLabel("Thu", JLabel.CENTER));
		result.add(new JLabel("Fri", JLabel.CENTER));
		result.add(new JLabel("Sat", JLabel.CENTER));

		for(GUICheckBox[] row : checkBoxes)
			for(GUICheckBox c : row)
				if(c != null)
					result.add(c);

		return result;
	}
}

/**
 * This class is a very simple extension of javax.swing.JCheckBox which helps correspond JCheckBoxes to their corresponding Block and Day of Week
 * Used only within GUIBlockPicker
 * @author KevinGao
 *
 */
@SuppressWarnings("serial")
class GUICheckBox extends JCheckBox {

	/**The block that corresponds to this JCheckBox*/
	private Block block;

	/**
	 * Default constructor for GUICheckBox
	 * GUICheckBox for the clickable block options
	 * @param name the name of the CheckBox
	 * @param block the corresponding block
	 * @param selectable whether to enable or disable the check box
	 */
	public GUICheckBox(String name, Block block, boolean selectable) {
		super(name);
		this.block = block;
		setEnabled(selectable);
		setFocusPainted(false);
	}

	/**
	 * @return the block that corresponds to this GUICheckBox
	 */
	public Block getBlock() {
		return block;
	}

}
