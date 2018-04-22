package Algorithm;

import gui.ContentMainClasses.ImagePanel;

public class ImageDetails {

	private int[][] landmarks;
	private int[][][] triangles;
	
	private ImagePanel image;
	
	// Constructor for holding the landmarks and triangles produced during facial extraction stage
	public ImageDetails(int[][] _landmarks, int[][][] _tirangles, ImagePanel _image)
	{
		landmarks = _landmarks;
		triangles = _tirangles;
		image = _image;
	}
	
	// Getters
	public int[][] getLandmarks()
	{
		return landmarks;
	}
	
	public int getLandmarksLength()
	{
		return landmarks.length;
	}
	
	public int[][][] getTriangles()
	{
		return triangles;
	}
	
	public int getTrianglesLength()
	{
		return triangles.length;
	}
	
	public ImagePanel getImage()
	{
		return image;
	}
}
