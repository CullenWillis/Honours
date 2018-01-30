package Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JNIWrapper {

	public native void landmarkDetection(String imagePath, String datPath);
	public native int delaunayTriangulation(String imagePath, String pointPath);
	
	static {
		System.loadLibrary("DlibLibrary");
		System.loadLibrary("DelaunayLib");
	}
	
	public int[][][] getDelaunayTriangulation(String imagePath, String pointName, String triangleName)
	{
		String pointPath = System.getProperty("user.dir") + "\\" + pointName;
		
		JNIWrapper wrapper = new JNIWrapper();
		
		int size = wrapper.delaunayTriangulation(imagePath, pointPath);
		
		return readTrianglesFile(triangleName, size);
	}

	public int[][] getLandmarks(String imagePath, String filename)
	{
		File file = new File("resources/shape_predictor.dat");
		String shapePredictor = file.getAbsolutePath();
		
		JNIWrapper wrapper = new JNIWrapper();
		wrapper.landmarkDetection(imagePath, shapePredictor);
		
		int[][] locations = readLandmarksFile(filename);
		
		if(locations != null)
		{	
			return locations;
		}
		return null;
	}
	
	private static int[][] readLandmarksFile(String filename)
	{
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
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
	
	private static int[][][] readTrianglesFile(String filename, int size)
	{
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			
			int[][][] triangles = new int[size / 3][3][2];
			
			String sCurrentLine;

			int countX = 0;
			int countY = 0;
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				String line = sCurrentLine;
				
				if(line.length() != 0)
				{
					line = line.substring(1, line.length() - 1);
					
					String x = "";
					String y = "";
					
					for(int i = 1; i < line.length(); i++)
					{
						if(line.substring(i - 1, i).equals(","))
						{
							x = line.substring(0, (i - 1));
							
							int space = i + 1;
							y = line.substring(space, line.length());
						}
					}
					
					triangles[countX][countY][0] = Integer.parseInt(x);
					triangles[countX][countY][1] = Integer.parseInt(y);
					
					countY++;
					
					if(countY == 3)
					{
						countX++;
						countY = 0;
					}
				}
			}
			
			return triangles;

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
