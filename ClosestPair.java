//===== Uncomment this package before submitting project ======
//package edu.cmich.cps542;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class ClosestPair {

	public static void main(String[] args) {
	
    /* load data from points.txt here */

    // create array list for points
    ArrayList<Point> points = new ArrayList<Point>();

    // read from the file
    try {
      File pointFile = new File("points.txt");
      Scanner fileReader = new Scanner(pointFile);

      // extract the numbers from each line of points.txt
      while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        data = data.replaceAll("[( )]", "");
        String[] nums = data.split(",");
        double x = Double.parseDouble(nums[0]);
        double y = Double.parseDouble(nums[1]);

        //create a new Point and add it to array
        Point p = new Point(x, y);
        points.add(p);
      }
      fileReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("points.txt could not be read.");
      e.printStackTrace();
    }
    System.out.println("Finished reading points.txt");


		/* use your sort method here */

    ArrayList<Point> sortedPointsX = sort(points, true);
    //ArrayList<Point> sortedPointsY = sort(points, false);

    //System.out.println("Finished sorting points");

		/* call efficientClosestPair here */

		
	}

  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static PointPair efficientClosestPair(ArrayList<Point> pointsXOrdered, ArrayList<Point> pointsYOrdered) {
		
		
			
		return null;
			
	}
	
  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static PointPair bruteClosestPair(ArrayList<Point> points) {
		

		return null;

	}
	
	
	public static ArrayList<Point> sort(ArrayList<Point> points, boolean sortX) {
		
		/* No call to sort method here.  Implement something that is divide-
		 *  -and-conquer and O(n log n) */
    
		
		return null;
	}
	
	
	
}
