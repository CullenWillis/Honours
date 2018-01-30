package gui.ContentMainClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import constants.Constants;

public class NoImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public NoImagePanel()
	{
		setup();
	}
	
	private void setup()
	{
		intializeJPanel();
		createBroswerDialog();
	}
	
	private void intializeJPanel()
	{
		this.setBackground(Constants.SIDE_BAR_COLOR);
		this.setSize(new Dimension(350, 350));
		this.setLocation(25, 25);
		this.setLayout(null);
		
		// Border
		MatteBorder border = BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.BORDER_COLOR);
		this.setBorder(border);
	}
	
	private void createBroswerDialog()
	{
		Border dashed = BorderFactory.createDashedBorder(Color.GRAY, 6, 6);

		this.setBorder(dashed);
		
		JLabel label = new JLabel("or drag an image here.");
		
		label.setBounds(180, this.getHeight() - 150, 150, 30);  
		label.setFont(new Font("Gadugi", Font.PLAIN, 13));
		
		this.add(label);
	}
	
	public JButton createDialogButton()
	{
		JButton button = new JButton("Browse");
		
		button.setBounds(95, this.getHeight() - 150, 80, 30);  
		button.setFocusPainted(false);
		button.setFocusable(false);
		
		return button;
	}
}
