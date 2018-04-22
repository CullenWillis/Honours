package gui.DrawTools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawToolTriangles extends JPanel {
 
	private boolean debug = false;
	
	private static final long serialVersionUID = 1L;
	Image backgroundImage;
    int[][][] locations;

    public DrawToolTriangles(int[][][] _locations, Image _image) 
    {
    	backgroundImage = _image;
    	locations = _locations;
    }

    public void setImage(Image image)
    {
    	backgroundImage = image;
    	
    	/*
        JLabel lbl = new JLabel(new ImageIcon(backgroundImage));
        JOptionPane.showMessageDialog(null, lbl, "ImageDialog", 
                                     JOptionPane.PLAIN_MESSAGE, null);*/
    }
    
    public void setTriangles(int[][][] triangles)
    {
    	locations = triangles;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image each time the panel is repainted.        
        g.drawImage(backgroundImage, 0, 0, null);
        g.setColor(Color.WHITE);
        
        if(locations != null)
        {      
	        for(int i = 0; i < locations.length; i++)
	        {
	        	for(int j = 0; j < 3; j++)
	        	{
	            	int x1 = (int) Math.round(locations[i][j][0]);
	            	int y1 = (int)  Math.round(locations[i][j][1]);
	            	
	            	int x2, y2;
	            	
	            	if(j != 2)
	            	{
	            		x2 = (int) Math.round(locations[i][j + 1][0]);
	                	y2 = (int)  Math.round(locations[i][j + 1][1]);
	            	}
	            	else
	            	{
	            		x2 = (int) Math.round(locations[i][0][0]);
	                	y2 = (int)  Math.round(locations[i][0][1]);
	            	}
	            	
	            	boolean check1 = checkPosition(x1, y1);
	            	boolean check2 = checkPosition(x2, y2);
	            	
	            	if(check1 && check2)
	            	{
	            		if(debug)
	            		{
	            			if(j == 0)
	                		{ 
	                			System.out.println("\n");
	                		}
	                		System.out.println("Drawing: [" + x1 + ", " + y1 + "] to [" + x2 + ", " + y2 + "]");
	            		}
	            		
	                	g.drawLine(x1, y1, x2, y2);
	            	}
	        	}
	        }   
        }
    }

    private Boolean checkPosition(int x1, int y1)
	{	
		if(x1 > 0 && x1 < backgroundImage.getWidth(null))
		{
			if(y1 > 0 && y1 < backgroundImage.getHeight(null))
			{
				return true;
			}
		}

		return false;
	}
}