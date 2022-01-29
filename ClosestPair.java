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
    } 
    catch (FileNotFoundException e) {
      System.out.println("points.txt could not be read.");
      e.printStackTrace();
    }

    System.out.println("Finished reading points.txt");


    //sort points based on X value
    sort(points, true);
    ArrayList<Point> sortedPointsX = new ArrayList<Point>(points);

    //sort points based on Y value
    sort(points, false);
    ArrayList<Point> sortedPointsY = new ArrayList<Point>(points);

    System.out.println("Finished sorting points");


		// call efficientClosestPair
    //PointPair closestPair = efficientClosestPair(sortedPointsX, sortedPointsY);
    PointPair brutePair = bruteClosestPair(points);
    System.out.println(brutePair);

	}

  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static PointPair efficientClosestPair(ArrayList<Point> pointsXOrdered, ArrayList<Point> pointsYOrdered) {

    if(pointsXOrdered.size() == 0){
      return null;
    }
    else if (pointsXOrdered.size() <= 1){  //for 2 or 3 points
      ArrayList<Point> brute = new ArrayList<>();

      for(int i = 0; i <= pointsXOrdered.size()-1;i++){
        Point point = new Point(pointsXOrdered.get(i).x, pointsXOrdered.get(i).y);
        brute.add(point);
      }

      //printArray(pointsXOrdered);
      PointPair pointPair = bruteClosestPair(brute);
      System.out.println("final point pair");
      System.out.println(pointPair);
      return pointPair;
    } 
    else {
      System.out.println("efficientClosestPair");
      int size = pointsXOrdered.size();
      System.out.println("Size of X " + size);
      int halfSize = size/2;
      System.out.println("Half Size of X " + halfSize);
      int sizeY = pointsYOrdered.size();
      System.out.println("Size of Y " + size);
      int halfSizeY = sizeY/2;
      System.out.println("Half Size of Y " + halfSize);

      ArrayList<Point> Pl = new ArrayList<>();
      
      for(int i = 0; i < halfSize;i++){
        Pl.add(pointsXOrdered.get(i));
      }
        
      System.out.println("Points of Pl");
      //printArray(Pl);

      ArrayList<Point> Pr = new ArrayList<>();
      
      for(int i = halfSize; i < size;i++){
        Pr.add(pointsXOrdered.get(i));
      }
      
      System.out.println("Points of Pr");
      //printArray(Pr);

      ArrayList<Point> Ql = new ArrayList<>();
      
      for(int i = 0; i < halfSize;i++){
        Ql.add(pointsYOrdered.get(i));
      }

      System.out.println("Points of Ql");
      //printArray(Ql);

      ArrayList<Point> Qr = new ArrayList<>();
      for(int i = pointsYOrdered.size(); i < pointsYOrdered.size()-1;i++){
        Pr.add(pointsYOrdered.get(i));
      }

      PointPair left = efficientClosestPair(Pl,Ql);
      PointPair right = efficientClosestPair(Pr,Qr);

		  return null;
    }
	}
	
  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static PointPair bruteClosestPair(ArrayList<Point> points) {
    System.out.println("Starting brute...");
		double shortestDistance = Double.POSITIVE_INFINITY;
		int pointOneIndex = 0;
    int pointTwoIndex = 0;

		for(int i = 0; i < points.size() - 1; i++){
			for(int j = i + 1; j < points.size(); j++){

        PointPair pair = new PointPair(points.get(i), points.get(j));
        double distance = pair.distBetweenPoints();

				if(shortestDistance > distance){
					shortestDistance = distance;
          pointOneIndex = i;
          pointTwoIndex = j;
				}
			}
		}

		System.out.println("Closest distance is: " + shortestDistance);
		System.out.println("indices are: " + pointOneIndex + " " + pointTwoIndex);

		PointPair pointPair = new PointPair(points.get(pointOneIndex), points.get(pointTwoIndex));
		return pointPair;
	}


  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static ArrayList<Point> sort(ArrayList<Point> points, boolean sortX) {
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
