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
import gui.ContentGUI.Toolbar;

public class ContentFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	// Classes
	private Toolbar _toolbar;
	
	// Private Variables
	private JToolBar toolbar;
	private JPanel navigationPanel;
	private JPanel contentPanel;
	
	public ContentFrame()
	{
		super(Constants.APPLICATION_NAME); // Setup title
		
		_toolbar = new Toolbar();
		
		instantiateFrame();
		contentSetup();
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
	
	private void contentSetup()
	{
		// instantiate and setup Toolbar
		toolbar = new JToolBar();
		toolbarSetup();
		
		// instantiate and setup Navigation Panel
		navigationPanel = new JPanel();
		navigationSetup();
	}
	
	private void toolbarSetup()
	{
		toolbar = _toolbar.createToolBar(); // Setup toolbar
		add(toolbar, BorderLayout.WEST);
	}

	private void navigationSetup()
	{
		
	}
}
