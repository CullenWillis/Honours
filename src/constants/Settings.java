package constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Settings implements SettingsInterface
{
	private Properties settingsFile;
	
	private String propertiesFile = "settings.properties";
	private String directory;
	
	public Settings()
	{
		settingsFile = new Properties();
		loadSettingsFile();
	}
	
	private void loadSettingsFile()
	{
		URL u = getClass().getProtectionDomain().getCodeSource().getLocation();
		
		try 
	    {
			File f = new File(u.toURI());
			directory = f.getParent();
			
			directory += "\\" + propertiesFile;
			
			settingsFile.load(new FileInputStream(directory));
			System.out.println("properties file found! Time lapse: " + System.currentTimeMillis() % 1000 + "ms");
		} 
	    catch (URISyntaxException e1) 
	    {
	    	System.out.println("ERROR | " + e1);
		}
		catch(IOException e)
		{
			System.out.println("ERROR | Couldn't find file: settings.properties");
		}
	}
	
	public String getShapePredictor()
	{
		return(settingsFile.getProperty("shape_predictor"));
	}
	
	public String getDNNModel()
	{
		return(settingsFile.getProperty("DNN_model"));
	}
	
	public String getPointsFile()
	{
		return(settingsFile.getProperty("points_file"));
	}
	
	public String getDelaunayFile()
	{
		return(settingsFile.getProperty("delaunay_file"));
	}
	
	public String getImageFile()
	{
		return(settingsFile.getProperty("image_file"));
	}
}
