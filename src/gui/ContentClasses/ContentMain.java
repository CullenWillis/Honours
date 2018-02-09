package gui.ContentClasses;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algorithm.Algorithm;
import Algorithm.ImageDetails;
import constants.Constants;
import gui.ContentManager;
import gui.ContentMainClasses.ContentSettings;
import gui.ContentMainClasses.ImagePanel;
import gui.ContentMainClasses.NavigationSettings;
import gui.ContentMainClasses.NoImagePanel;

public class ContentMain implements DropTargetListener{

	ContentManager manager;
	
	private Algorithm algorithm;
	private ContentSettings contentSettings;
	
	//Pictures
	private JPanel pictureBoxPast, pictureBoxPresent;
	private NoImagePanel noImagePast, noImagePresent;
	public ImagePanel imagePast, imagePresent;
	
	private ImageDetails pastDetails, presentDetails;
	
	//Buttons
	private JButton detectButton;
	
	private JPanel container, contentPanel;
	
	private JFileChooser fileChooser;
	public File file1, file2;
	
	public ContentMain(ContentManager _manager)
	{
		manager = _manager;
	}
	
	public JPanel setup()
	{
		instantiateContents();
		
		instantiateDragandDrop();

		return container;
	}
	
	private void instantiateContents()
	{
		contentSettings = new ContentSettings();
		
		pictureBoxPast = new JPanel();
		pictureBoxPresent = new JPanel();
		
		noImagePast = new NoImagePanel();
		noImagePresent = new NoImagePanel();
		
		imagePast = new ImagePanel();
		imagePast.setName("imagePast");
		
		imagePresent = new ImagePanel();
		imagePresent.setName("imagePresent");
		
		detectButton = new JButton("Start");
		
		this.fileChooser = new JFileChooser();
		fileChooserSetup();
		
		frameSetup();
	}

	private void instantiateDragandDrop()
	{
		new DropTarget(noImagePast, this);
		new DropTarget(noImagePresent, this);
		new DropTarget(imagePast, this);
		new DropTarget(imagePresent, this);
	}
	
	private void fileChooserSetup()
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
		fileChooser.setFileFilter(filter);
	}
	
	private void frameSetup()
	{	
		// instantiate and setup Container
		container = new JPanel();
		containerSetup();
		
		// instantiate and setup Content Panel
		contentPanel = new JPanel();
		contentSetup();
	}
	
	private void containerSetup()
	{
		container.setBackground(new Color(255, 0, 0));
		container.setLayout(new BorderLayout());
	}
	
	private void contentSetup()
	{
		contentPanel = contentSettings.createContentPanel(); // Setup content Panel
		container.add(contentPanel, BorderLayout.CENTER);
		
		pictureBoxSetup();
		detectButtonSetup();
	}
	
	private void pictureBoxSetup()
	{
		pictureBoxPast.setBackground(Constants.SIDE_BAR_COLOR);
		pictureBoxPast.setSize(new Dimension(400, 400));
		pictureBoxPast.setLayout(new CardLayout());
		
		pictureBoxPresent.setBackground(Constants.SIDE_BAR_COLOR);
		pictureBoxPresent.setSize(new Dimension(400, 400));
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
	
	private void detectButtonSetup()
	{
		int x = Constants.FRAME_WIDTH / 2;
		int y = Constants.FRAME_HEIGHT / 2;
		
		detectButton.setBounds(x - 80, y - 10, 100, 50);
		detectButton.setFocusPainted(false);
		detectButton.setVisible(true);
		detectButton.setFocusable(false);
		buttonDetectionEventHandler(detectButton);
		
        contentPanel.add(detectButton, null);
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
				if(fileChooser.showOpenDialog(manager) == JFileChooser.APPROVE_OPTION)
				{
					ContentMain.this.file1 = fileChooser.getSelectedFile();
					
					ContentMain.this.imagePast.loadImage(ContentMain.this.file1);
					
					ContentMain.this.setView(true);
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
				if(fileChooser.showOpenDialog(manager) == JFileChooser.APPROVE_OPTION)
				{
					ContentMain.this.file2 = fileChooser.getSelectedFile();
					
					ContentMain.this.imagePresent.loadImage(ContentMain.this.file2);
					
					ContentMain.this.setView(false);
				}
			}
		});
	}
	
	private void buttonDetectionEventHandler(JButton button)
	{
		// Add eventListener to load image button (Will open the dialog window)
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				algorithm = new Algorithm(imagePast, imagePresent);
				
				algorithm.startDetection();
				
				pastDetails = algorithm.getPastDetails();
				presentDetails = algorithm.getPresentDetails();
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
		file1 = null;
		file2 = null;
		CardLayout cardsPast = (CardLayout) pictureBoxPast.getLayout();
		CardLayout cardsPresent = (CardLayout) pictureBoxPresent.getLayout();
		
		cardsPast.show(pictureBoxPast, "noImage");
		cardsPresent.show(pictureBoxPresent, "noImage");
	}

	public ImagePanel getImagePast()
	{
		return imagePast;
	}
	
	public ImagePanel getImagePresent()
	{
		return imagePresent;
	}
	
	public ImageDetails getPastDetails()
	{
		return pastDetails;
	}
	
	public ImageDetails getPresentDetails()
	{
		return presentDetails;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	
	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dragOver(DropTargetDragEvent e) 
	{
		
	}
	
	@Override
	public void drop(DropTargetDropEvent event) 
	{
		try
		{		
			// Accept dropped items
			DropTargetContext dtc = event.getDropTargetContext();
			
			JPanel panel = new JPanel();
			panel = (JPanel) dtc.getComponent();
			
			event.acceptDrop(DnDConstants.ACTION_COPY);
			
			// Transferable items from file
			Transferable t = event.getTransferable();
			
			// Get data formats
			DataFlavor[] df = t.getTransferDataFlavors();
			
			// Loop through flavours
			for(DataFlavor f : df)
			{
				try
				{
					// Check if items are of file type
					if(f.isFlavorJavaFileListType())
					{
						@SuppressWarnings("unchecked")
						List<File> files = (List<File>) t.getTransferData(f);
						
						for(File file : files)
						{
							
							if(checkDropPosition())
							{
								file1 = file;
								
								imagePast.loadImage(file1);
								
								setView(true);
							}
							else
							{
								file2 = file;
								
								imagePresent.loadImage(file2);
								
								setView(false);
							}
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			event.rejectDrop();
		}
		
	}
	
	private Boolean checkDropPosition()
	{
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - manager.getLocationOnScreen().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - manager.getLocationOnScreen().y;
		
		int positionX = 107;
		int positionY = 119;
		int borders = 401;
		
		if(mouseX > positionX && mouseX < (positionX + borders))
		{
			if(mouseY > positionY && mouseY < (positionY + borders))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
