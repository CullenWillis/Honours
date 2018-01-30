package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import com.sun.javafx.tk.Toolkit;

import constants.Constants;

public class ToolbarSettings {

	JToolBar toolbar;
	ContentManager manager;
	
	// Buttons
	JButton homeButton;
	JButton settingsButton;
	JButton resetButton;
	JButton helpButton;
	JButton exitButton;
	
	// Image Icons
	ImageIcon homeIcon;
	ImageIcon settingsIcon;
	ImageIcon resetIcon;
	ImageIcon helpIcon;
	ImageIcon exitIcon;
	
	JMenu settingsBar;
	JMenuItem menuItem;
	
	public ToolbarSettings(ContentManager frame) 
	{
		toolbar = new JToolBar();
		manager = frame;
		
		toolbarSetup();
	}

	// Create and return toolbar
	public JToolBar createToolBar() 
	{
		getIcons(); // Get all necessary icons 
        buttonSetup(); // Setup each button
        
        // Add buttons to the toolbar
        
        toolbar.add(homeButton);
        homeButton.setVisible(false);
        
        toolbar.add(settingsButton);
        toolbar.add(resetButton);
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
        
        // Border
		MatteBorder border = BorderFactory.createMatteBorder(0, 0, 0, 3, Constants.BORDER_COLOR);
		toolbar.setBorder(border);
	}
	
	// Buttons
	private void buttonSetup()
	{
		homeButton = new JButton(homeIcon); // Settings
		homeButton.setToolTipText("Home");
		homeButton.setFocusPainted(false);
		homeButton.setFocusable(false);
		homeButton.setBackground(Constants.SIDE_BAR_COLOR);
        
        settingsButton = new JButton(settingsIcon); // Settings
        settingsButton.setToolTipText("Settings");
        settingsButton.setFocusPainted(false);
        settingsButton.setFocusable(false);
        settingsButton.setBackground(Constants.SIDE_BAR_COLOR);
        
        resetButton = new JButton(resetIcon); // Settings
		resetButton.setToolTipText("Reset Image Frames");
		resetButton.setFocusPainted(false);
		resetButton.setFocusable(false);
		resetButton.setBackground(Constants.SIDE_BAR_COLOR);
		
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
		homeButton.addActionListener((ActionEvent event) -> {
			String view = "DEFAULT";
			
			setView(view);
			manager.changeDisplay(view);
		});
		
		settingsButton.addActionListener((ActionEvent event) -> {
			String view = "SETTINGS";
			
			setView(view);
			manager.changeDisplay(view);
		});
		
		resetButton.addActionListener((ActionEvent event) -> {
            manager.mainView.resetViews();
        });
		
        exitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
	}
	
	private void setView(String view)
	{
		if(view.equals("DEFAULT"))
		{
			homeButton.setVisible(false);
			settingsButton.setVisible(true);
			resetButton.setVisible(true);
			helpButton.setVisible(true);
			exitButton.setVisible(true);
		}
		else if(view.equals("SETTINGS"))
		{
			homeButton.setVisible(true);
			settingsButton.setVisible(false);
			resetButton.setVisible(false);
			helpButton.setVisible(true);
			exitButton.setVisible(true);
		}
		else if(view.equals("ANALYTICS"))
		{
			homeButton.setVisible(true);
			settingsButton.setVisible(true);
			resetButton.setVisible(false);
			helpButton.setVisible(true);
			exitButton.setVisible(true);
		}
	}
	
	// Icons
	private void getIcons()
	{
		// Icons
		homeIcon = new ImageIcon("resources/MaterialHome.png");
        settingsIcon = new ImageIcon("resources/MaterialSettings.png");
        resetIcon = new ImageIcon("resources/MaterialRefresh.png");
        helpIcon = new ImageIcon("resources/MaterialHelp.png");
        exitIcon = new ImageIcon("resources/MaterialExit.png");
	}
}
