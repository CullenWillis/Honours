package Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JNIWrapper{

	// Instantiate classes inside shared library
	public native void landmarkDetection(String imagePath, String datPath);
	public native int delaunayTriangulation(String imagePath, String pointPath);
	public native int faceRecognition(String imagePath, String shapePredictor, String model);
	
	// Load the shared libraries
	static {
		System.loadLibrary("DlibLibrary");
		System.loadLibrary("DelaunayLib");
		System.loadLibrary("dnn_face_recognition_ex");
	}
	
	// method to call the class inside the shared library, this gathers the Delaunay Triangulation 
	public int[][][] getDelaunayTriangulation(String imagePath, String pointName, String triangleName)
	{
		// load the data
		String pointPath = System.getProperty("user.dir") + "\\" + pointName;
		
		// call the function in DLL
		JNIWrapper wrapper = new JNIWrapper();
		int size = wrapper.delaunayTriangulation(imagePath, pointPath);
		
		// read the locations and return and array
		return readTrianglesFile(triangleName, size);
	}

	// method to call the class inside the shared library, this gathers the Facial Landmarks
	public int[][] getLandmarks(String imagePath, String filename)
	{
		// load the data
		File file = new File("shape_predictor.dat");
		String shapePredictor = file.getAbsolutePath();
		
		// call the function in DLL
		JNIWrapper wrapper = new JNIWrapper();
		wrapper.landmarkDetection(imagePath, shapePredictor);
		
		// read the locations and return and array
		int[][] locations = readLandmarksFile(filename);
		
		if(locations != null)
		{	
			return locations;
		}
		return null;
	}
	
	// Get the results from analysis stage
	public int getResults(String image, String shape, String model)
	{
		// call the function in DLL and return 1 if it's a match and 0 if it's not a match
		JNIWrapper wrapper = new JNIWrapper();
		return wrapper.faceRecognition(image, shape, model);
	}
	
	// Read landmarks file and put the information in correct format
	private static int[][] readLandmarksFile(String filename)
	{
		// Read file
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			
			int[][] locations = new int[68][2];
			
			String sCurrentLine;

			int countX = 0;
			int countY = 0;
			
			// put information into correct format
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
	
	// Read each location in triangles file and put the information in correct format
	private static int[][][] readTrianglesFile(String filename, int size)
	{
		// Read each location in triangles file
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			
			int[][][] triangles = new int[size / 3][3][2];
			
			String sCurrentLine;

			int countX = 0;
			int countY = 0;
			
			//  put the information in correct format
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
