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
import constants.Settings;
import constants.SettingsInterface;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	SettingsInterface settings;
	
	private JLabel imageLabel;
	
	private Image imageSrc;
	private File fileSrc;
	
	private ImageIcon transformedImageIcon;
	private File transformedImageFile;
	private String transformedImagePath;
	private BufferedImage transformedBufferedImage;
	
	private String imageType = null;
	
	// Contains image
	public ImagePanel() 
	{
		this.imageLabel = new JLabel();
		settings = new Settings();
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
		
		transformedBufferedImage = i;
		
		writeImage(i);
		
		setImage(image);
		imageLabel.setIcon(new ImageIcon(imageSrc));
	}
	
	// Crop image
	public void cropImage(BufferedImage image, int x, int y, int width, int height)
	{
		BufferedImage croppedImage = image.getSubimage(x, y, width, height);
		
		updateImage(croppedImage);
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
	
	public void setImageType(String type)
	{
		imageType = type;
	}
	
	private void writeImage(BufferedImage image)
	{
		try
		{
			String path = "";
			
			if(imageType.equals("past"))
			{
				path = settings.getImageFile() + "processedImage1.jpg";	
			}
			else if (imageType.equals("present"))
			{
				path = settings.getImageFile() + "processedImage2.jpg";	
			}
			
			File file = new File(path);  //output file path
			
			Path pathToFile = Paths.get(path);
			
			if(file.exists())
			{
				try 
				{
				    Files.delete(pathToFile);
				} 
				catch (NoSuchFileException x) 
				{
				    System.err.format("%s: no such" + " file or directory%n", path);
				} 
				catch (DirectoryNotEmptyException x) 
				{
				    System.err.format("%s not empty%n", path);
				} 
				catch (IOException x) 
				{
				    // File permission problems are caught here.
				    System.err.println(x);
				}
			}
			
			if(!file.exists())
			{
				Files.createDirectories(pathToFile.getParent());
				
				transformedImagePath = path;
				ImageIO.write(image, "jpg", file);
				
				transformedImageFile = file;
			}
		}
		catch(IOException e)
		{
			System.out.println("Error: " + e);
		}
	}
	
	public ImageIcon getIcon()
	{
		return transformedImageIcon;
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
	
	public BufferedImage getTransformedBufferedImage()
	{
		return transformedBufferedImage;
	}

	public String getTransformedPath()
	{
		return transformedImagePath;
	}
	
	public static BufferedImage getBufferedImage(Image img)
	{
	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
