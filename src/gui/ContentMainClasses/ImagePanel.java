package gui.ContentMainClasses;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel imageLabel;
	
	private Image imageSrc;
	private File fileSrc;
	
	private ImageIcon transformedImageIcon;
	private File transformedImageFile;
	private String transformedImagePath;
	
	// Contains image
	public ImagePanel() 
	{
		this.imageLabel = new JLabel();
		
		setLayout(new BorderLayout());
		
		int gap = 5;
		
		setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		imageLabel.setVerticalAlignment(JLabel.CENTER);
		
		add(imageLabel);
	}
	
	// Updates image
	public void updateImage(Image image)
	{		
		image = scaleImage(image);
		
		transformedImageIcon = new ImageIcon(image);
		
		BufferedImage i = getBufferedImage(image);
		
		writeImage(i);
		setImage(image);
		imageLabel.setIcon(new ImageIcon(imageSrc));
	}
	
	// Scaling image
	private Image scaleImage(Image image)
	{
		Dimension imageSize = new Dimension(image.getWidth(null), image.getHeight(null));
		Dimension boundary = new Dimension(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
		Dimension scaledSize = getScaledDimension(imageSize, boundary);
		
		return image.getScaledInstance(scaledSize.width, scaledSize.height, Image.SCALE_SMOOTH);
	}
	
	Dimension getScaledDimension(Dimension imageSize, Dimension boundary) 
	{
	    double widthRatio = boundary.getWidth() / imageSize.getWidth();
	    double heightRatio = boundary.getHeight() / imageSize.getHeight();
	    double ratio = Math.min(widthRatio, heightRatio);

	    return new Dimension((int) (imageSize.width * ratio), (int) (imageSize.height * ratio));
	}
	
	// Load image
	public void loadImage(File file)
	{
		// Store reference to transport icon
		setFile(file);
		
		//Get File
		Image image = null;
		
		try
		{
			image = ImageIO.read(file);
		}
		catch (IOException e) 
		{
			System.out.println("Error: " + e);
		}
		
		// Update image after transformation
		updateImage(image);
	}
	
	public void writeImage(BufferedImage image)
	{
		try
		{
			String dataFolder = System.getenv("APPDATA");
			String path1 = dataFolder + "\\FacialComparison\\images\\processedImage.jpg";	
			String path2 = dataFolder + "\\FacialComparison\\images\\processedImage1.jpg";
			
			File f1 = new File(path1);  //output file path
			File f2 = new File(path2);
			
			Path pathToFile1 = Paths.get(path1);
			Path pathToFile2 = Paths.get(path2);
			
			if(f1.exists() && f2.exists())
			{
				try 
				{
				    Files.delete(pathToFile1);
				    Files.delete(pathToFile2);
				} 
				catch (NoSuchFileException x) 
				{
				    System.err.format("%s: no such" + " file or directory%n", path1);
				    System.err.format("%s: no such" + " file or directory%n", path2);
				} 
				catch (DirectoryNotEmptyException x) 
				{
				    System.err.format("%s not empty%n", path1);
				    System.err.format("%s not empty%n", path2);
				} 
				catch (IOException x) 
				{
				    // File permission problems are caught here.
				    System.err.println(x);
				}
			}
			
			if(!f1.exists())
			{
				Files.createDirectories(pathToFile1.getParent());
				
				transformedImagePath = path1;
				ImageIO.write(image, "jpg", f1);
				
				transformedImageFile = f1;
			}
			else if (f1.exists())
			{
				Files.createDirectories(pathToFile2.getParent());
				
				transformedImagePath = path2;
				ImageIO.write(image, "jpg", f2);
				
				transformedImageFile = f2;
			}	
		}
		catch(IOException e)
		{
			System.out.println("Error: " + e);
		}
	}
	
	public boolean checkIcon()
	{
		if (imageLabel.getIcon() == null) 
		{
			  return false;
		}
		return true;
	}
	
	public void setImage(Image image)
	{
		imageSrc = image;
	}
	
	public Image getImage()
	{
		return imageSrc;
	}
	
	public void setFile(File file)
	{
		fileSrc = file;
	}
	
	public File getFile()
	{
		return fileSrc;
	}
	
	public File getTransformedFile()
	{
		return transformedImageFile;
	}

	public String getTransformedPath()
	{
		return transformedImagePath;
	}
	
	public static BufferedImage getBufferedImage(Image img)
	{
	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
