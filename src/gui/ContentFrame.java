package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import constants.Constants;
import gui.ContentGUI.ContentSettings;
import gui.ContentGUI.NavigationSettings;
import gui.ContentGUI.ToolbarSettings;

public class ContentFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	// Classes
	private ToolbarSettings toolbarSettings;
	private NavigationSettings navigationSettings;
	private ContentSettings contentSettings;
	
	// Private Variables
	private JToolBar toolbar;
	private JPanel container;
	private JPanel navPanel;
	private JPanel contentPanel;
	
	public ContentFrame()
	{
		super(Constants.APPLICATION_NAME); // Setup title
		
		toolbarSettings = new ToolbarSettings();
		navigationSettings = new NavigationSettings();
		contentSettings = new ContentSettings();
		
		instantiateFrame();
		frameSetup();
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
		
		// instantiate and setup Container
		container = new JPanel();
		containerSetup();
		
		// instantiate and setup Navigation Panel
		navPanel = new JPanel();
		navigationSetup();
		
		// instantiate and setup Content Panel
		contentPanel = new JPanel();
		contentSetup();
	}
	
	private void toolbarSetup()
	{
		toolbar = toolbarSettings.createToolBar(); // Setup toolbar
		this.add(toolbar, BorderLayout.WEST);
	}

	private void containerSetup()
	{
		container.setBackground(new Color(255, 0, 0));
		container.setLayout(new BorderLayout());
		
		this.add(container, BorderLayout.CENTER);
	}
	
	private void navigationSetup()
	{
		navPanel = navigationSettings.createNavPanel(); // Setup navigation Panel
		container.add(navPanel, BorderLayout.NORTH);
	}
	
	private void contentSetup()
	{
		contentPanel = contentSettings.createCotnentPanel(); // Setup content Panel
		container.add(contentPanel, BorderLayout.CENTER);
	}
}
