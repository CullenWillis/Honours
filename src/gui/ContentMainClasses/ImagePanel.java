package gui.ContentMainClasses;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel imageLabel;
	private ImageIcon transformedImageIcon;
	
	private Image imageSrc;
	private File fileSrc;
	
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
		setImage(scaleImage(image));
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
		this.transformedImageIcon = new ImageIcon(file.getAbsolutePath());
		Image image = transformedImageIcon.getImage();
		
		// Update image after transformation
		updateImage(image);
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
}
