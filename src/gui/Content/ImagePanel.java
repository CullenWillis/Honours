package gui.Content;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel imageLabel;
	private ImageIcon transformedImageIcon;
	
	
	// Contains image
	public ImagePanel() 
	{
		this.imageLabel = new JLabel();
		
		setLayout(new BorderLayout());
		
		int gap = 5;
		
		setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
	
		add(imageLabel);
	}
	
	// Updates image
	public void updateImage(Image image)
	{		
		imageLabel.setIcon(new ImageIcon(scaleImage(image)));
	}
	
	// Scaling image
	private Image scaleImage(Image image)
	{
		ImageIcon icon = new ImageIcon(image);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	// Load image
	public void loadImage(File file)
	{
		// Store reference to transport icon
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
	
}
