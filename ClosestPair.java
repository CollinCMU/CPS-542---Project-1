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
    //System.out.println(points);

    System.out.println("Finished sorting points");


		// call efficientClosestPair
    PointPair closestPair = efficientClosestPair(sortedPointsX, sortedPointsY);
    System.out.println(closestPair);

	}

  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static PointPair efficientClosestPair(ArrayList<Point> pointsXOrdered, ArrayList<Point> pointsYOrdered) {
    PointPair smallest;

    //if points <= 3, use brute force
    if (pointsXOrdered.size() <= 3){
      return bruteClosestPair(pointsXOrdered);
    } 
    //efficient closest pair algorithm
    else {
      //get sizes and create arraylists
      int size = pointsXOrdered.size();
      int halfSize = size / 2;
      ArrayList<Point> Pl = new ArrayList<Point>(pointsXOrdered.subList(0, halfSize));
      ArrayList<Point> Pr = new ArrayList<Point>(pointsXOrdered.subList(halfSize, size));
      ArrayList<Point> Ql = new ArrayList<Point>(pointsYOrdered.subList(0, halfSize));
      ArrayList<Point> Qr = new ArrayList<Point>(pointsYOrdered.subList(halfSize, size));

      //recursive calls
      PointPair left = efficientClosestPair(Pl,Ql);
      PointPair right = efficientClosestPair(Pr,Qr);
      
      //determine smallest of each half
      if (left.distBetweenPoints() < right.distBetweenPoints()){
        smallest = left;
      }
      else{
        smallest = right;
      }

      double minDist = smallest.distBetweenPoints();
      double minDistSqr = Math.pow(minDist, 2);
      double midX = pointsXOrdered.get(halfSize - 1).x;

      ArrayList<Point> S = new ArrayList<Point>();     

      for(int i = 0; i < size; i++){
        if(Math.abs(pointsYOrdered.get(i).x - midX) < smallest.distBetweenPoints()){
          S.add(pointsYOrdered.get(i));
        }
      }  


      for(int i = 0; i < S.size() - 2; i++){
        int k = i + 1;

        while(k <= S.size() - 1 && (S.get(k).y - S.get(i).y) < minDistSqr){
          PointPair points = new PointPair(S.get(k), S.get(i));
          //minDistSqr = Math.min(points.distBetweenPoints(), minDistSqr);

          if(points.distBetweenPoints() < minDistSqr){
            minDistSqr = points.distBetweenPoints();
            smallest = points;
          }

          k++;
        }

      }
      
		  
    }
    return smallest;
	}
	

  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
	public static PointPair bruteClosestPair(ArrayList<Point> points) {
    if (points.size() == 0){
      return null;
    }

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
    ArrayList<Point> left;
    ArrayList<Point> right;
    int mid;

    if (points.size() > 1){
      mid = points.size() / 2;

      left = new ArrayList<Point>(points.subList(0, mid));
      right = new ArrayList<Point>(points.subList(mid, points.size()));

      sort(left, sortX);
      sort(right, sortX);
      merge(points, left, right, sortX);
    }
    return null;
	}
	

  /*
	 * Write what it does
	 * 
	 * @return Write what it returns 
	 */
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
