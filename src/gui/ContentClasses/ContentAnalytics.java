package gui.ContentClasses;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import Algorithm.ImageDetails;
import constants.Constants;
import gui.ContentManager;
import gui.ContentMainClasses.ContentSettings;
import gui.ContentMainClasses.ImagePanel;
import gui.DrawTools.DrawToolLandmarks;
import gui.DrawTools.DrawToolTriangles;

public class ContentAnalytics {

	ContentManager manager;

	Timer timer;
	
	private ContentSettings contentSettings;
	
	private JPanel container, contentPanel;
	
	private JPanel pictureBoxPast, pictureBoxPresent;
	private ImagePanel imagePast, imagePresent;
	
	private ImageDetails pastDetails, presentDetails;
	
	private File file1, file2;
	
	public ContentAnalytics(ContentManager _manager)
	{
		manager = _manager;
	}
	
	public JPanel setup()
	{
		instantiateContents();

		return container;
	}
	
	// -------------------------------- INITIALISATION -------------------------
	
	private void instantiateContents()
	{
		contentSettings = new ContentSettings();
		
		pictureBoxPast = new JPanel();
		pictureBoxPresent = new JPanel();
		
		imagePast = new ImagePanel();
		imagePast.setName("imagePast");
		
		imagePresent = new ImagePanel();
		imagePresent.setName("imagePresent");
		
		frameSetup();
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
	}
	
	// -------------------------------- PICTURE BOX -------------------------
	
	private void pictureBoxSetup()
	{
		pictureBoxPast.setBackground(Constants.SIDE_BAR_COLOR);
		pictureBoxPast.setSize(new Dimension(400, 400));
		pictureBoxPast.setLayout(new CardLayout());
		
		pictureBoxPresent.setBackground(Constants.SIDE_BAR_COLOR);
		pictureBoxPresent.setSize(new Dimension(400, 400));
		pictureBoxPresent.setLayout(new CardLayout());
		
		int gapSize = 50;
		int height = Constants.FRAME_HEIGHT / 2 - 320;
		
		pictureBoxPast.setLocation(new Point(gapSize, height));
		pictureBoxPresent.setLocation(new Point((gapSize * 2) + pictureBoxPresent.getWidth(), height));
		
		contentPanel.add(pictureBoxPast, null);
		contentPanel.add(pictureBoxPresent, null);
	}

	public void switchViews(boolean option)
	{
		if(option)
		{
			container.setVisible(option);
			
			timer = new Timer(3000, new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					CardLayout cardsPast = (CardLayout) pictureBoxPast.getLayout();
					CardLayout cardsPresent = (CardLayout) pictureBoxPresent.getLayout();
					
					cardsPast.next(pictureBoxPast);
					cardsPresent.next(pictureBoxPresent);
				}
			});

			if(pastDetails != null && presentDetails != null && file1 != null && file2 != null)
			{
				System.out.println("Data acquired");
				
				imagePast.loadImage(file1);
				imagePresent.loadImage(file2);
				
				startDrawing(pastDetails, imagePast, presentDetails, imagePresent);	
				
				timer.start();
			}
		}
		else
		{
			timer.stop();
			container.setVisible(option);
		}
		
	}
	
	public void setView(String option)
	{
		CardLayout cardsPast = (CardLayout) pictureBoxPast.getLayout();
		CardLayout cardsPresent = (CardLayout) pictureBoxPresent.getLayout();
		
		if(option.equals("landmarks"))
		{
			cardsPast.show(pictureBoxPast, "landmarks");
			cardsPresent.show(pictureBoxPresent, "landmarks");
		}
		else if (option.equals("triangles"))
		{
			cardsPast.show(pictureBoxPast, "triangles");
			cardsPresent.show(pictureBoxPresent, "triangles");
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
	
	// -------------------------------- Image Drawing -------------------------
	
	private void startDrawing(ImageDetails past, ImagePanel pastImage, ImageDetails present, ImagePanel presentImage)
	{
		DrawToolLandmarks pastLandmarks = drawLandmarks(past, pastImage);
		DrawToolLandmarks presentLandmarks = drawLandmarks(present, presentImage);
		
		DrawToolTriangles pastTriangles = drawTriangles(past, pastImage);
		DrawToolTriangles presentTriangles = drawTriangles(present, presentImage);
		
		pictureBoxPast.add(pastLandmarks, "landmarks");
		pictureBoxPresent.add(presentLandmarks, "landmarks");
		
		pictureBoxPast.add(pastTriangles, "triangles");
		pictureBoxPresent.add(presentTriangles, "triangles");
	}
	
	private DrawToolLandmarks drawLandmarks(ImageDetails details, ImagePanel imageP)
	{
		Image image = new ImageIcon(imageP.getTransformedFile().getAbsolutePath()).getImage();
		
		DrawToolLandmarks panel = new DrawToolLandmarks(details.getLandmarks(), image);
		
		return panel;
	}
	
	private DrawToolTriangles drawTriangles(ImageDetails details, ImagePanel imageP)
	{
		Image image = new ImageIcon(imageP.getTransformedFile().getAbsolutePath()).getImage();
		
		DrawToolTriangles panel = new DrawToolTriangles(details.getTriangles(), image);
		
		return panel;
	}
	
	// -------------------------------- Getter & Setters -------------------------
	
	public void setImagePast(File image)
	{
		file1 = image;
	}
	
	public void setImagePresent(File image)
	{
		file2 = image;
	}
	
	public void setPastDetails(ImageDetails details)
	{
		pastDetails = details;
	}
	
	public void setPresentDetails(ImageDetails details)
	{
		presentDetails = details;
	}	
}
