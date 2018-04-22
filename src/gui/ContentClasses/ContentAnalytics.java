package gui.ContentClasses;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	
	DrawToolLandmarks pastLandmarks;
	DrawToolLandmarks presentLandmarks;
	
	DrawToolTriangles pastTriangles;
	DrawToolTriangles presentTriangles;
	
	private JPanel container, contentPanel;
	
	private JLabel desc;
	
	private JPanel pictureBoxPast, pictureBoxPresent;
	private ImagePanel imagePast, imagePresent;
	
	private ImageDetails pastDetails, presentDetails;
	
	private File file1, file2;
	
	int result = 0;
	
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
		descriptionSetup();
	}
	
	// -------------------------------- PICTURE BOX -------------------------
	
	private void descriptionSetup()
	{
		int y = Constants.FRAME_HEIGHT - 325;
		
		desc = new JLabel();
		
		desc.setBounds(50, y, 900, 200);
		desc.setFont(new Font("gadugi", Font.PLAIN, 16));
		desc.setText("No images have been loaded yet...");
		desc.setVisible(true);
		contentPanel.add(desc, null);
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
		int height = Constants.FRAME_HEIGHT / 2 - 320;
		
		pictureBoxPast.setLocation(new Point(gapSize, height));
		pictureBoxPresent.setLocation(new Point((350) + pictureBoxPresent.getWidth(), height));
		
		contentPanel.add(pictureBoxPast, null);
		contentPanel.add(pictureBoxPresent, null);
		
		// Card setup
		pastLandmarks = new DrawToolLandmarks(null, null);
		presentLandmarks = new DrawToolLandmarks(null, null);
		
		pastTriangles = new DrawToolTriangles(null, null);
		presentTriangles = new DrawToolTriangles(null, null);
		
		pictureBoxPast.add(pastLandmarks, "landmarks");
		pictureBoxPresent.add(presentLandmarks, "landmarks");
		
		pictureBoxPast.add(pastTriangles, "triangles");
		pictureBoxPresent.add(presentTriangles, "triangles");
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
				
				imagePast.setImageType("past");
				imagePast.loadImage(file1);
				
				imagePresent.setImageType("present");
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
	
	private void startDrawing(ImageDetails pastDetails, ImagePanel pastImage, ImageDetails presentDetails, ImagePanel presentImage)
	{
		result = ContentMain.resultNumber;
		
		pastLandmarks.setImage(pastImage.getImage());
		pastLandmarks.setLandmarks(pastDetails.getLandmarks());
		
		presentLandmarks.setImage(presentImage.getImage());
		presentLandmarks.setLandmarks(presentDetails.getLandmarks());

		pastTriangles.setImage(pastImage.getImage());
		pastTriangles.setTriangles(pastDetails.getTriangles());
		
		presentTriangles.setImage(presentImage.getImage());
		presentTriangles.setTriangles(presentDetails.getTriangles());
		
		pastLandmarks.repaint();
		presentLandmarks.repaint();
		pastTriangles.repaint();
		presentTriangles.repaint();
		
		System.out.println(result);
		
		String content = "";
		
		if(result != 1)
		{
			content = "<html>Sadly it wasn't a match. <BR> Are you sure these pictures are of the same person? If so then try these tips: <BR> Make sure there is nothing abstructing the face <BR> Ensure the picture is in good lighting conditions. <BR> Make sure the face is clearly visible and covers most the image.</html>";
		}
		else
		{
			content = "<html>It's a match! <BR> These pictures contain the same person.</html>";
		}
		
		desc.setText(content);
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
