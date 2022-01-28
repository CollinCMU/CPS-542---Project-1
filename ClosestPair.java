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
    //sort points based on X value
    sort(points, true);
    ArrayList<Point> sortedPointsX = points;
    //System.out.println(sortedPointsX);

    //sort points based on Y value
    sort(points, false);
    ArrayList<Point> sortedPointsY = points;
    System.out.println(sortedPointsY);

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
	
	


  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static ArrayList<Point> sort(ArrayList<Point> points, boolean sortX) {
		
		/* No call to sort method here.  Implement something that is divide-
		 *  -and-conquer and O(n log n) */

    //create havles of array
    ArrayList<Point> left = new ArrayList<Point>();
    ArrayList<Point> right = new ArrayList<Point>();
    int mid;

    if (points.size() > 1){

      mid = points.size() / 2;

      //populate halves
      for(int i = 0; i < mid; i++){
        left.add(points.get(i));
      }
      for(int j = mid; j < points.size(); j++){
        right.add(points.get(j));
      }

      sort(left, sortX);
      sort(right, sortX);
      merge(points, left, right, sortX);
    }

    return null;
	}
	
	public static void merge(ArrayList<Point> points, ArrayList<Point> left, ArrayList<Point> right, boolean sortX){
    
    ArrayList<Point> mergedList = new ArrayList<Point>();

    int i = 0;
    int j = 0;
    int k = 0;

    while((i < left.size()) && (j < right.size())){
      
      //sort based on X
      if (sortX){
        if(left.get(i).x < right.get(j).x){
          points.set(k, left.get(i));
          i++;
        }
        else{
          points.set(k, right.get(j));
          j++;
        }
      }
      //sort based on Y
      else{
        if(left.get(i).y < right.get(j).y){
          points.set(k, left.get(i));
          i++;
        }
        else{
          points.set(k, right.get(j));
          j++;
        }
      }

      k++;
    }

    int t = 0;
    if (i >= left.size()){
      mergedList = right;
      t = j;
    }
    else {
      mergedList = left;
      t = i;
    }
    
    for (int h = t; h < mergedList.size(); h++){
      points.set(k, mergedList.get(h));
      h++;
    }
  }
}
