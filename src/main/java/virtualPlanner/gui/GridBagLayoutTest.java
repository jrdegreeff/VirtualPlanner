package virtualPlanner.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridBagLayoutTest 
{

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Tester");
		
		JPanel mainPanel = new JPanel();

		JButton button1 = new JButton("YYYYYAAAAAYYYYY");
		JButton button2 = new JButton("YYYYYAAAAAYYYYY");
		JButton button3 = new JButton("YYYYYAAAAAYYYYY");
		JButton button4 = new JButton("YYYYYAAAAAYYYYY");
		JButton button5 = new JButton("YYYYYAAAAAYYYYY");
		JButton button6 = new JButton("YYYYYAAAAAYYYYY");
		JButton button7 = new JButton("YYYYYAAAAAYYYYY");
		JButton button8 = new JButton("YYYYYAAAAAYYYYY");
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		mainPanel.add(button1, c);
		c.gridx = 1;
		c.gridy = 1;
		mainPanel.add(button2, c);
		
		c.gridx = 1;
		c.gridy = 5;
		mainPanel.add(button3, c);

		
		
		
		
		
		
		
		
		
		frame.add(mainPanel);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}
	
}
