package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import constants.Constants;
import gui.ContentClasses.ContentAnalytics;
import gui.ContentClasses.ContentMain;

public class ContentManager extends JFrame 
{
	private static final long serialVersionUID = 1L;

	enum display {
		DEFAULT, SETTINGS, ANALYTICS;
	}
	
	private display displayView;
	
	// Classes
	private ToolbarSettings toolbarSettings;	
	
	public ContentMain mainView;
	public ContentAnalytics analyticsView;
	
	private JPanel mainPanel;
	private JPanel settingsPanel;
	private JPanel analyticsPanel;
	
	private JToolBar toolbar;
	
	public ContentManager()
	{
		super(Constants.APPLICATION_NAME); // Setup title
		
		toolbarSettings = new ToolbarSettings(this);
		
		instantiateFrame();
		frameSetup();
		
		displayView = display.DEFAULT;
		
		setView();
	}
	
	private void instantiateFrame()
	{
		// Settings
		this.setResizable(false);
		this.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		this.setVisible(true);
		this.getContentPane().setBackground(Constants.BACKGROUND_COLOR);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Sets frame in the center of the screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
	
	private void frameSetup()
	{
		// instantiate and setup Toolbar
		toolbar = new JToolBar();
		toolbarSetup();
	}
	
	public void setView()
	{
		if(displayView == display.DEFAULT)
		{
			if(mainPanel == null) 
			{
				System.out.println("Initalising default View.\n");
				setupDefaultView();
				mainPanel.setVisible(true);
			}
			else
			{
				System.out.println("Switching to main view.\n");
				analyticsView.switchViews(false);
				mainPanel.setVisible(true);
			}
		}
		else if(displayView == display.SETTINGS)
		{
			System.out.println("Switching to settings view.\n");
			/*
			if(settingsPanel == null) 
			{
				System.out.println("Initalising Settings View");
				setupsettingsView();
			}
			else
			{
				mainPanel.setVisible(false);
				settingsPanel.setVisible(true);
			}
			*/
		}
		else if(displayView == display.ANALYTICS)
		{
			if(analyticsPanel == null) 
			{
				System.out.println("Initalising analytic View.\n");
				
				setupAdminView();
				
				analyticsView.switchViews(true);
				mainPanel.setVisible(false);
			}
			else
			{
				System.out.println("Switching to analytic view.\n");
				
				if(mainView.getImagePast() != null && mainView.getImagePresent() != null && mainView.getPastDetails() != null && mainView.getPresentDetails() != null)
				{
					System.out.println("Passing Information.\n");
					analyticsView.setImagePast(mainView.file1);
					analyticsView.setImagePresent(mainView.file2);
					
					analyticsView.setPastDetails(mainView.getPastDetails());
					analyticsView.setPresentDetails(mainView.getPresentDetails());
				}
				
				analyticsView.switchViews(true);
				mainPanel.setVisible(false);
			}
		}
	}
	
	public void changeDisplay(String view)
	{
		if(view.equals("DEFAULT"))
		{
			displayView = display.DEFAULT;
		}
		else if(view.equals("SETTINGS"))
		{
			displayView = display.SETTINGS;
		}
		else if(view.equals("ANALYTICS"))
		{
			displayView = display.ANALYTICS;
		}
		
		setView();
	}
	
	public String getDisplay()
	{
		if(displayView == display.DEFAULT)
		{
			return "DISPLAY";
		}
		else if(displayView == display.SETTINGS)
		{
			return "SETTINGS";
		}
		else if(displayView == display.ANALYTICS)
		{
			return "ANALYTICS";
		}
		
		return "ERROR";
	}
	
	private void setupDefaultView()
	{
		mainView = new ContentMain(this);
		mainPanel = mainView.setup();
		
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	private void setupSettingsView()
	{
		
	}
	
	private void setupAdminView()
	{
		analyticsView = new ContentAnalytics(this);
		
		if(mainView.getImagePast() != null && mainView.getImagePresent() != null && mainView.getPastDetails() != null && mainView.getPresentDetails() != null)
		{
			System.out.println("Passing Information.\n");
			analyticsView.setImagePast(mainView.file1);
			analyticsView.setImagePresent(mainView.file2);
			
			analyticsView.setPastDetails(mainView.getPastDetails());
			analyticsView.setPresentDetails(mainView.getPresentDetails());
		}
		
		analyticsPanel = analyticsView.setup();
		
		this.add(analyticsPanel, BorderLayout.CENTER);
	}
	
	private void toolbarSetup()
	{
		toolbar = toolbarSettings.createToolBar(); // Setup toolbar
		this.add(toolbar, BorderLayout.WEST);
	}

	
}
