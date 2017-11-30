package gui.Content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import constants.Constants;

public class ContentSettings {

	JPanel contentPanel;
	
	public ContentSettings() 
	{
		contentPanel = new JPanel();
		
		contentPanelSetup();
	}
	
	// Create and return content Panel
	public JPanel createCotnentPanel() 
	{
        return contentPanel;
    }
	
	// contentPanel
	private void contentPanelSetup()
	{
		// contentPanel settings
		contentPanel.setBackground(Constants.BACKGROUND_COLOR);
		contentPanel.setLayout(null);
		contentPanel.setVisible(true);
	}
}
