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
	JButton resetButton;
	
	JButton analyticsButton;
	
	JButton exitButton;
	
	// Image Icons
	ImageIcon homeIcon;
	ImageIcon resetIcon;
	
	ImageIcon analyticIcon;
	
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
        toolbar.add(resetButton);
        
        toolbar.add(Box.createVerticalGlue());
        
        
        toolbar.add(homeButton);
        homeButton.setVisible(false);
        toolbar.add(analyticsButton);
        analyticsButton.setVisible(true);
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
		homeButton = new JButton(homeIcon); // Home
		homeButton.setToolTipText("Home");
		homeButton.setFocusPainted(false);
		homeButton.setFocusable(false);
		homeButton.setBackground(Constants.SIDE_BAR_COLOR);
        
		resetButton = new JButton(resetIcon); // Reset
		resetButton.setToolTipText("Reset Image Frames");
		resetButton.setFocusPainted(false);
		resetButton.setFocusable(false);
		resetButton.setBackground(Constants.SIDE_BAR_COLOR);
        
        analyticsButton = new JButton(analyticIcon); // Analytics
        analyticsButton.setToolTipText("Analytics");
        analyticsButton.setFocusPainted(false);
        analyticsButton.setFocusable(false);
        analyticsButton.setBackground(Constants.SIDE_BAR_COLOR);
        
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
		
		//------------------------ Home -------------------------
		homeButton.addActionListener((ActionEvent event) -> {
			String view = "DEFAULT";
			
			setView(view);
			manager.changeDisplay(view);
		});
		
		resetButton.addActionListener((ActionEvent event) -> {
            manager.mainView.resetViews();
        });
		
		analyticsButton.addActionListener((ActionEvent event) -> {
			
			String view = "ANALYTICS";
			
			setView(view);
			manager.changeDisplay(view);
		});
	
		//------------------------ Analytics -------------------------
		
		//------------------------ Other -------------------------
        exitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
	}
	
	
	private void setView(String view)
	{
		if(view.equals("DEFAULT"))
		{
			homeButton.setVisible(false);
			resetButton.setVisible(true);
			
			analyticsButton.setVisible(true);
			
			exitButton.setVisible(true);
		}
		else if(view.equals("ANALYTICS"))
		{
			homeButton.setVisible(true);
			resetButton.setVisible(false);
			
			analyticsButton.setVisible(false);
			
			exitButton.setVisible(true);
		}
	}
	
	// Icons
	
	private void getIcons()
	{
		// Icons
		homeIcon = new ImageIcon("resources/Logos/MaterialHome.png");
		resetIcon = new ImageIcon("resources/Logos/MaterialRefresh.png");
		
        analyticIcon = new ImageIcon("resources/Logos/MaterialAnalytics.png");
        
        exitIcon = new ImageIcon("resources/Logos/MaterialExit.png");
	}
}
