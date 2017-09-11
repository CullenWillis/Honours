package gui.ContentGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import constants.Constants;

public class Toolbar {

	JToolBar toolbar;
	
	// Buttons
	JButton settingsButton;
	JButton helpButton;
	JButton exitButton;
	
	// Image Icons
	ImageIcon settingsIcon;
	ImageIcon helpIcon;
	ImageIcon exitIcon;
	
	public Toolbar() 
	{
		toolbar = new JToolBar();
		
		toolbarSetup();
	}
	
	// Create and return toolbar
	public JToolBar createToolBar() 
	{
		getIcons(); // Get all necessary icons 
        buttonSetup(); // Setup each button
        
        // Add buttons to the toolbar
        toolbar.add(settingsButton);
        toolbar.add(helpButton);
        toolbar.add(exitButton);
        
        // Give buttons eventHandlers
        eventHandlerManager();
        
        return toolbar;
    }
	
	// Toolbar
	private void toolbarSetup()
	{
		// Toolbar settings
        toolbar.setFloatable(false);
        toolbar.setBackground(Constants.SIDE_BAR_COLOR);
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
	}
	
	// Buttons
	private void buttonSetup()
	{
        settingsButton = new JButton(settingsIcon); // Settings
        settingsButton.setToolTipText("Settings");
        settingsButton.setFocusPainted(false);
        settingsButton.setFocusable(false);
        settingsButton.setBackground(Constants.SIDE_BAR_COLOR);
        
        helpButton = new JButton(helpIcon); // Help
        helpButton.setToolTipText("Help");
        helpButton.setFocusPainted(false);
        helpButton.setFocusable(false);
        helpButton.setBackground(Constants.SIDE_BAR_COLOR);
        
        exitButton = new JButton(exitIcon); // Exit
        exitButton.setToolTipText("Exit");
        exitButton.setFocusPainted(false);
        exitButton.setFocusable(false);
        exitButton.setBackground(Constants.SIDE_BAR_COLOR);
	}
	
	// EventHandlers
	private void eventHandlerManager()
	{
		// EventHandlers
        exitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
	}
	
	// Icons
	private void getIcons()
	{
		// Icons
        settingsIcon = new ImageIcon("resources/MaterialSettings.png");
        helpIcon = new ImageIcon("resources/MaterialHelp.png");
        exitIcon = new ImageIcon("resources/MaterialExit.png");
	}
}
