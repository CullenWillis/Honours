package Algorithm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Settings;
import constants.SettingsInterface;
import gui.ContentMainClasses.ImagePanel;

public class Algorithm{

	//Classes
	JNIWrapper wrapper;
	ImageDetails pastDetails, presentDetails;
	SettingsInterface settings;
	
	// Variables
	private int[][] landmarks;
	
	private int[][][] triangles;
	
	private ImagePanel imagePast;
	private ImagePanel imagePresent;
	
	// Constructor
	public Algorithm(ImagePanel past, ImagePanel present)
	{
		settings = new Settings();
		setimagePast(past);
		setimagePresent(present);
	}

	// Start the detection for facial extraction and analysis stage
	public int startDetection()
	{
		getFacialExtration();
		String path = mergeImages();
		
		if(path != null)
		{
			System.out.println("Starting Analysis...");
			return startAnalysis(path);
		}
		else
		{
			System.err.println("Can't get merged image");
			return 0;
		}
	}
	
	// gather landmarks and Delaunay triangulation
	private void getFacialExtration()
	{
		wrapper = new JNIWrapper();
		
		// Gather information for past image
		landmarks = wrapper.getLandmarks(imagePast.getTransformedFile().getAbsolutePath(), "Points1.txt");	
		triangles = wrapper.getDelaunayTriangulation(imagePast.getTransformedFile().getAbsolutePath(), "Points1_2.txt", "delaunay1.txt");
		pastDetails = new ImageDetails(landmarks, triangles, imagePast);

		// Gather information for present image
		landmarks = wrapper.getLandmarks(imagePresent.getTransformedFile().getAbsolutePath(), "Points2.txt");
		triangles = wrapper.getDelaunayTriangulation(imagePresent.getTransformedFile().getAbsolutePath(), "Points2_2.txt", "delaunay2.txt");
		presentDetails = new ImageDetails(landmarks, triangles, imagePast);
		
		System.out.println("Facial Extration Complete! \n");
	}
	
	// Merge images into one picture for analysis stage
	private String mergeImages()
	{
		int rows = 1;   //we assume the no. of rows and cols are known and each chunk has equal width and height
        int cols = 2;
        int chunks = rows * cols;

        int chunkWidth, chunkHeight;
        int type;
        
        try
        {
	       //creating a bufferd image array from image files
	        BufferedImage[] buffImages = new BufferedImage[chunks];
	        
	        buffImages[0] = imagePast.getTransformedBufferedImage();
	        buffImages[1] = imagePresent.getTransformedBufferedImage();
	        
	        type = buffImages[0].getType();
	        chunkWidth = buffImages[0].getWidth();
	        chunkHeight = buffImages[0].getHeight();
	
	        //Initialising the final image
	        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);
	
	        int num = 0;
	        for (int i = 0; i < rows; i++) 
	        {
	            for (int j = 0; j < cols; j++) 
	            {
	                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
	                num++;
	            }
	        }
	        
	        System.out.println("Image concatenated.....");
	        
	        String dataFolder = System.getenv("APPDATA");
			String path = dataFolder;

			path = settings.getImageFile() + "\\mergedImage.jpg";
				
	        ImageIO.write(finalImg, "jpeg", new File(path));
	        
	        return path;
        }
        catch(IOException e)
        {
        	System.out.println(e);
        }
        
		return null;
	}
	
	// Start analysis stage
	private int startAnalysis(String image)
	{
		// Gather necessary information for analysis stage
		String shape = "shape_predictor.dat";
		String model = "dlib_face_recognition_resnet_model_v1.dat";
		
		return wrapper.getResults(image, shape, model);
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
