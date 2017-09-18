package gui.ContentGUI;

import java.awt.Color;

import javax.swing.JPanel;

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
	}
	
	private void fileDialogSetup()
	{
		
	}
}
