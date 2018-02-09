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
	ImageDetails pastDetails, presentDetails;
	
	private int[][] landmarks;
	
	private int[][][] triangles;
	
	private ImagePanel imagePast;
	private ImagePanel imagePresent;
	
	public Algorithm(ImagePanel past, ImagePanel present)
	{
		setimagePast(past);
		setimagePresent(present);
	}
	
	public void startDetection()
	{
		getFacialExtration();
	}
	
	private void getFacialExtration()
	{
		wrapper = new JNIWrapper();
		
		landmarks = wrapper.getLandmarks(imagePast.getTransformedFile().getAbsolutePath(), "Points1.txt");	
		triangles = wrapper.getDelaunayTriangulation(imagePast.getTransformedFile().getAbsolutePath(), "Points1_2.txt", "delaunay1.txt");
		pastDetails = new ImageDetails(landmarks, triangles, imagePast);

		landmarks = wrapper.getLandmarks(imagePresent.getTransformedFile().getAbsolutePath(), "Points2.txt");
		triangles = wrapper.getDelaunayTriangulation(imagePresent.getTransformedFile().getAbsolutePath(), "Points2_2.txt", "delaunay2.txt");
		presentDetails = new ImageDetails(landmarks, triangles, imagePast);
		
		System.out.println("Facial Extration Complete! \n");
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
	
	public ImageDetails getPastDetails()
	{
		return pastDetails;
	}
	
	public ImageDetails getPresentDetails()
	{
		return presentDetails;
	}
}
