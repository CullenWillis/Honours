package gui.ContentGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import constants.Constants;

public class PicturePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JPanel noImagePanel;
	private JLabel imagePanel;
	
	private ImageIcon transformedImageIcon;
	
	private JFileChooser fileChooser;
	private File file;

	public PicturePanel()
	{
		noImagePanel = new JPanel();
		imagePanel = new JLabel();
		
		setup();
		
		this.fileChooser = new JFileChooser();
		fileChooserSetup();
	}
	
	private void fileChooserSetup()
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
		fileChooser.setFileFilter(filter);
	}
	
	private void intializeJPanel()
	{
		this.setBackground(Constants.SIDE_BAR_COLOR);
		this.setLayout(null);
		this.setSize(new Dimension(400, 400));
		
		// Border
		MatteBorder border = BorderFactory.createMatteBorder(2, 2, 2, 2, Constants.BORDER_COLOR);
		this.setBorder(border);
	}
	
	private void setup()
	{
		intializeJPanel();
		
		createBroswerDialog();
		
		this.add(noImagePanel);
		
		noImagePanel.setVisible(true);
		imagePanel.setVisible(false);
	}
	
	private void switchViews()
	{
		if(imagePanel.isVisible())
		{
			imagePanel.setVisible(false);
			noImagePanel.setVisible(true);
		}
		else
		{
			imagePanel.setVisible(true);
			noImagePanel.setVisible(false);
		}
	}
	
	private void createBroswerDialog()
	{
		noImagePanel.setSize(new Dimension(350, 350));
		noImagePanel.setLocation(25, 25);
		noImagePanel.setLayout(null);
		
		Border dashed = BorderFactory.createDashedBorder(null, 6, 6);

		noImagePanel.setBorder(dashed);
		
		JButton button = new JButton("Browse");
		
		button.setBounds(75, this.getHeight() - 150, 80, 30);  
		button.setFocusPainted(false);
		button.setFocusable(false);
		
		JLabel label = new JLabel("or drag image here.");
		
		label.setBounds(165, this.getHeight() - 150, 110, 30);  
		label.setFont(new Font("Gadugi", Font.PLAIN, 12));
		
		buttonEventHandler(button);
		
		noImagePanel.add(button);
		noImagePanel.add(label);
	}
	
	private void buttonEventHandler(JButton button)
	{
		// Add eventListener to load image button (Will open the dialog window)
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				if(fileChooser.showOpenDialog(PicturePanel.this) == JFileChooser.APPROVE_OPTION)
				{
					PicturePanel.this.file = fileChooser.getSelectedFile();
					
					//PicturePanel.this.PicturePanel.loadImage(PicturePanel.this.file);
				}
			}
		});
	}
}
