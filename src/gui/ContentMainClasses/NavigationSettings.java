package gui.ContentMainClasses;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

import constants.Constants;
import javafx.scene.layout.Border;

public class NavigationSettings {
	
	JPanel navPanel;
	
	public NavigationSettings() 
	{
		navPanel = new JPanel();
		
		navPanelSetup();
	}
	
	// Create and return navPanel
	public JPanel createNavPanel() 
	{

        return navPanel;
    }
	
	// navPanel
	private void navPanelSetup()
	{
		// navPanel settings
		navPanel.setSize(new Dimension(100, 1220));		
		navPanel.setBackground(Constants.TOP_BAR_COLOR);
		
		// Border
		MatteBorder border = BorderFactory.createMatteBorder(0, 0, 2, 0, Constants.SIDE_BAR_COLOR);
		navPanel.setBorder(border);
	}
}
