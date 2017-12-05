package Algorithm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Content.ImagePanel;

public class Algorithm {

	//Classes
	JNIWrapper wrapper;
	
	private int[][] landmarksPast;
	private int[][] landmarksPresent;
	
	private ImagePanel imagePast;
	private ImagePanel imagePresent;
	
	public Algorithm(ImagePanel past, ImagePanel present)
	{
		setimagePast(past);
		setimagePresent(present);
		
		landmarksPast = new int[68][2];
		landmarksPresent = new int[68][2];
	}
	
	public double startDetection()
	{
		getLandmakrs();
		
		return 0;
	}
	
	private void getLandmakrs()
	{
		wrapper = new JNIWrapper();
		
		landmarksPast = wrapper.getLandmarks(imagePast.getFile().getAbsolutePath());
		landmarksPresent = wrapper.getLandmarks(imagePresent.getFile().getAbsolutePath());
		
		if(landmarksPast.length > 0 && landmarksPresent.length > 0)
		{
			addFrame(landmarksPast, imagePast);
			addFrame(landmarksPresent, imagePresent);
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
