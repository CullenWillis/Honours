package gui.DrawTools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawToolLandmarks extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Image backgroundImage;
    int[][] locations = new int[68][2];

    public DrawToolLandmarks(int[][] _locations, Image _image) 
    {
    	backgroundImage = _image;
    	locations = _locations;
    }

    public void setImage(Image image)
    {
    	backgroundImage = image;
    }
    
    public void setLandmarks(int[][] landmarks)
    {
    	locations = landmarks;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image each time the panel is repainted.
        g.drawImage(backgroundImage, 0, 0, null);
        g.setColor(Color.WHITE);
        
        // Draw a little square
        if(locations != null)
        {
        	for(int i = 0; i < 68; i++)
            {
            	int timeSize = 0;
            	double times = 1;
            	
            	int x = (int) Math.round(locations[i][0] * times);
            	int y = (int) Math.round(locations[i][1] * times);
            	
            	g.fillRect(x + timeSize, y + timeSize, 2, 2);
            	
            }  
        }
    }
}