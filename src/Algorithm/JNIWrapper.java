package Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JNIWrapper {

	public native void landmarkDetection(String imagePath, String datPath);
	
	static {
		System.loadLibrary("DlibLibrary");
	}
	public int[][] getLandmarks(String imagePath)
	{
		
		
		File file = new File("resources/shape_predictor.dat");
		String shapePredictor = file.getAbsolutePath();
		
		JNIWrapper wrapper = new JNIWrapper();
		wrapper.landmarkDetection(imagePath, shapePredictor);
		
		int[][] locations = readFile();
		
		if(locations != null)
		{	
			return locations;
		}
		
		return null;
	}
	
	private static int[][] readFile()
	{
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader("Points.txt");
			br = new BufferedReader(fr);
			
			int[][] locations = new int[68][2];
			
			String sCurrentLine;

			int countX = 0;
			int countY = 0;
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				locations[countX][countY] = Integer.valueOf(sCurrentLine);
				
				countY++;
				
				if(countY == 2)
				{
					countX++;
					countY = 0;
				}
			}
			
			return locations;

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return null;
	}
}
