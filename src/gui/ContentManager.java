package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import constants.Constants;

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
	
	private JPanel mainPanel;
	private JPanel settingsPanel;
	
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
	
	public void setView()
	{
		if(displayView == display.DEFAULT)
		{
			if(mainPanel == null) 
			{
				System.out.println("Initalising Default View");
				setupDefaultView();
			}
			else
			{
				mainPanel.setVisible(true);
			}
		}
		else if(displayView == display.SETTINGS)
		{
			mainPanel.setVisible(false);
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
	
	private void toolbarSetup()
	{
		toolbar = toolbarSettings.createToolBar(); // Setup toolbar
		this.add(toolbar, BorderLayout.WEST);
	}

	
}
