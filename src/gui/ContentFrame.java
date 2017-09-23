package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import constants.Constants;
import gui.Content.ContentSettings;
import gui.Content.ImagePanel;
import gui.Content.NavigationSettings;
import gui.Content.NoImagePanel;
import gui.Content.ToolbarSettings;

public class ContentFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	// Classes
	private ToolbarSettings toolbarSettings;
	private NavigationSettings navigationSettings;
	private ContentSettings contentSettings;
	
	//Pictures
	private JPanel pictureBoxPast, pictureBoxPresent;
	private NoImagePanel noImagePast, noImagePresent;
	private ImagePanel imagePast, imagePresent;
	
	// Private Variables
	private JToolBar toolbar;
	private JPanel container;
	private JPanel navPanel;
	private JPanel contentPanel;
	
	private JFileChooser fileChooser;
	private File file1;
	private File file2;
	
	public ContentFrame()
	{
		super(Constants.APPLICATION_NAME); // Setup title
		
		toolbarSettings = new ToolbarSettings();
		navigationSettings = new NavigationSettings();
		contentSettings = new ContentSettings();
		
		pictureBoxPast = new JPanel();
		pictureBoxPresent = new JPanel();
		
		noImagePast = new NoImagePanel();
		noImagePresent = new NoImagePanel();
		
		imagePast = new ImagePanel();
		imagePresent = new ImagePanel();
		
		instantiateFrame();
		
		this.fileChooser = new JFileChooser();
		fileChooserSetup();
		
		frameSetup();
	}
	
	private void fileChooserSetup()
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
		fileChooser.setFileFilter(filter);
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
		
		pictureBoxSetup();
	}
	
	private void pictureBoxSetup()
	{
		pictureBoxPast.setBackground(Constants.SIDE_BAR_COLOR);
		pictureBoxPast.setSize(new Dimension(400, 400));
		pictureBoxPast.setLocation(25, 25);
		pictureBoxPast.setLayout(new CardLayout());
		
		pictureBoxPresent.setBackground(Constants.SIDE_BAR_COLOR);
		pictureBoxPresent.setSize(new Dimension(400, 400));
		pictureBoxPresent.setLocation(25, 25);
		pictureBoxPresent.setLayout(new CardLayout());
		
		int gapSize = 50;
		int height = Constants.FRAME_HEIGHT / 2 - 200;
		int width = Constants.FRAME_WIDTH - pictureBoxPresent.getWidth() - 63 - gapSize;
		
		pictureBoxPast.setLocation(new Point(gapSize, height));
		pictureBoxPresent.setLocation(new Point(width, height));
		
		contentPanel.add(pictureBoxPast, null);
		contentPanel.add(pictureBoxPresent, null);
		
		noImagePanelSetup();
		imagePanelSetup();
		
		CardLayout cardsPast = (CardLayout) pictureBoxPast.getLayout();
		CardLayout cardsPresent = (CardLayout) pictureBoxPresent.getLayout();
		
		cardsPast.show(pictureBoxPast, "noImage");
		cardsPresent.show(pictureBoxPresent, "noImage");
	}
	
	private void noImagePanelSetup()
	{
		JButton dialogButtonPast = new JButton();
		JButton dialogButtonPresent = new JButton();
		
		dialogButtonPast = noImagePast.createDialogButton();
		dialogButtonPresent = noImagePast.createDialogButton();
		
		buttonImageEventHandler1(dialogButtonPast);
		buttonImageEventHandler2(dialogButtonPresent);
		
		noImagePast.add(dialogButtonPast);
		noImagePresent.add(dialogButtonPresent);
		
		pictureBoxPast.add(noImagePast, "noImage");
		pictureBoxPresent.add(noImagePresent, "noImage");
	}
	
	private void imagePanelSetup()
	{
		pictureBoxPast.add(imagePast, "image");
		pictureBoxPresent.add(imagePresent, "image");
	}
	
	private void buttonImageEventHandler1(JButton button)
	{
		// Add eventListener to load image button (Will open the dialog window)
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				if(fileChooser.showOpenDialog(ContentFrame.this) == JFileChooser.APPROVE_OPTION)
				{
					ContentFrame.this.file1 = fileChooser.getSelectedFile();
					
					ContentFrame.this.imagePast.loadImage(ContentFrame.this.file1);
					
					ContentFrame.this.setView(true);
				}
			}
		});
	}
	
	private void buttonImageEventHandler2(JButton button)
	{
		// Add eventListener to load image button (Will open the dialog window)
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				if(fileChooser.showOpenDialog(ContentFrame.this) == JFileChooser.APPROVE_OPTION)
				{
					ContentFrame.this.file2 = fileChooser.getSelectedFile();
					
					ContentFrame.this.imagePresent.loadImage(ContentFrame.this.file2);
					
					ContentFrame.this.setView(false);
				}
			}
		});
	}
	
	public void setView(boolean isPanelPast)
	{
		CardLayout cardsPast = (CardLayout) pictureBoxPast.getLayout();
		CardLayout cardsPresent = (CardLayout) pictureBoxPresent.getLayout();
		
		if(isPanelPast)
		{
			cardsPast.show(pictureBoxPast, "image");
		}
		else
		{
			cardsPresent.show(pictureBoxPresent, "image");
		}
	}
	
	public void resetViews()
	{
		CardLayout cardsPast = (CardLayout) pictureBoxPast.getLayout();
		CardLayout cardsPresent = (CardLayout) pictureBoxPresent.getLayout();
		
		cardsPast.show(pictureBoxPast, "noImage");
		cardsPresent.show(pictureBoxPresent, "noImage");
	}
}
