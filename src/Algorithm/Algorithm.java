package Algorithm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.ContentMainClasses.ImagePanel;

public class Algorithm {

	//Classes
	JNIWrapper wrapper;
	ImageDetails pastDetails;
	ImageDetails presentDetails;
	
	private int[][] landmarks;
	
	private int[][][] triangles;
	
	private ImagePanel imagePast;
	private ImagePanel imagePresent;
	
	public Algorithm(ImagePanel past, ImagePanel present)
	{
		setimagePast(past);
		setimagePresent(present);
	}
	
	public double startDetection()
	{
		getLandmakrs();
		
		return 0;
	}
	
	private void getLandmakrs()
	{
		wrapper = new JNIWrapper();
		
		landmarks = wrapper.getLandmarks(imagePast.getFile().getAbsolutePath(), "Points1.txt");
		triangles = wrapper.getDelaunayTriangulation(imagePast.getFile().getAbsolutePath(), "Points1_2.txt", "delaunay1.txt");
		pastDetails = new ImageDetails(landmarks, triangles, imagePast);
		
		landmarks = wrapper.getLandmarks(imagePresent.getFile().getAbsolutePath(), "Points2.txt");
		triangles = wrapper.getDelaunayTriangulation(imagePresent.getFile().getAbsolutePath(), "Points2_2.txt", "delaunay2.txt");
		presentDetails = new ImageDetails(landmarks, triangles, imagePast);
		
		if(pastDetails.getLandmarksLength() > 0 && presentDetails.getLandmarksLength() > 0)
		{
			addFrame(pastDetails.getLandmarks(), imagePast);
			addFrame(presentDetails.getLandmarks(), imagePresent);
		}
	}
	
	private void addFrame(int[][] landmarks ,ImagePanel iPanel)
	{
		JFrame frame = new JFrame("LandmarkDemo");
		Image image = new ImageIcon(iPanel.getFile().getAbsolutePath()).getImage();
		MyPanel panel = new MyPanel(landmarks, image);
		
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setSize(new Dimension(image.getWidth(null) + 25, image.getHeight(null) + 25));
		frame.setVisible(true);
	}
	
	// Setters & Getters
	public void setimagePast(ImagePanel file)
	{
		imagePast = file;
	}
	
	public ImagePanel getimagePast()
	{
		return imagePast;
	}
	
	public void setimagePresent(ImagePanel file)
	{
		imagePresent = file;
	}
	
	public ImagePanel getimagePresent()
	{
		return imagePresent;
	}
}
