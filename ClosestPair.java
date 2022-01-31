package edu.cmich.cps542;

import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Reads in points from a file called 'points.txt'.
 * Sorts points based on X and Y coordinates using merge-sort.
 * Finds the closest pair of points using efficient-closest-pair algorithm.
 * 
 * @author vanbr1c, tallu1r, uniss1sh, rojan1a
 * @since 1-31-2022
 */
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

		//sort points based on X and Y values
		sort(points, true);
		ArrayList<Point> sortedPointsX = new ArrayList<Point>(points);
		sort(points, false);
		ArrayList<Point> sortedPointsY = new ArrayList<Point>(points);

		// call efficientClosestPair
		PointPair closestPair = efficientClosestPair(sortedPointsX, sortedPointsY);
		System.out.println(closestPair);
	}


	/**
	 * Returns the closest two points using two copies of an ArrayList sorted on the X and Y coordinate respectively.
	 * 
	 * @param pointsXOrdered ArrayList of points sorted by the X coordinate.
	 * @param pointsYOrdered ArrayList of points sorted by the Y coordinate.
	 * @return A PointPair containing the closest points.
	 */
	public static PointPair efficientClosestPair(ArrayList<Point> pointsXOrdered, ArrayList<Point> pointsYOrdered) {
		PointPair closestPair;

		//if points <= 3, use brute force
		if (pointsXOrdered.size() <= 3){
			return bruteClosestPair(pointsXOrdered);
		} 
		//efficient closest pair algorithm
		else {
			//get sizes and create array lists
			int size = pointsXOrdered.size();
			int midPoint = size / 2;
			ArrayList<Point> Pl = new ArrayList<Point>(pointsXOrdered.subList(0, midPoint));
			ArrayList<Point> Pr = new ArrayList<Point>(pointsXOrdered.subList(midPoint, size));
			ArrayList<Point> Ql = new ArrayList<Point>(pointsYOrdered.subList(0, midPoint));
			ArrayList<Point> Qr = new ArrayList<Point>(pointsYOrdered.subList(midPoint, size));
			ArrayList<Point> S = new ArrayList<Point>();

			//recursive calls
			PointPair left = efficientClosestPair(Pl,Ql);
			PointPair right = efficientClosestPair(Pr,Qr);

			//get the closer PointPair of the two
			if (left.distBetweenPoints() < right.distBetweenPoints()){
				closestPair = left;
			}
			else{
				closestPair = right;
			}

			double minDist = closestPair.distBetweenPoints();
			double minDistSqr = Math.pow(minDist, 2);
			double midX = pointsXOrdered.get(midPoint - 1).x;

			//add points to S
			for(int i = 0; i < size; i++){
				if(Math.abs(pointsYOrdered.get(i).x - midX) < closestPair.distBetweenPoints()){
					S.add(pointsYOrdered.get(i));
				}
			}  

			//search for closestPair
			for(int i = 0; i < S.size() - 2; i++){
				int j = i + 1;

				while(j <= S.size() - 1 && (S.get(j).y - S.get(i).y) < minDistSqr){
					PointPair points = new PointPair(S.get(j), S.get(i));

					//update closestPair
					if(points.distBetweenPoints() < minDistSqr){
						minDistSqr = points.distBetweenPoints();
						closestPair = points;
					}
					j++;
				}
			}
		}
		return closestPair;
	}


	/**
	 * Brute-force approach to finding the closest pair of points.
	 * 
	 * @param points The ArrayList of Points to search through.
	 * @return A PointPair containing the closest points.
	 */
	public static PointPair bruteClosestPair(ArrayList<Point> points) {
		double shortestDistance = Double.POSITIVE_INFINITY;
		PointPair closestPair = null;

		//if points is empty, return null
		if (points.size() == 0){
			return null;
		}

		//compare each set of points to find the closest pair
		for(int i = 0; i < points.size() - 1; i++){
			for(int j = i + 1; j < points.size(); j++){
				PointPair pair = new PointPair(points.get(i), points.get(j));
				double distance = pair.distBetweenPoints();

				//update shortestDistance and closestPair
				if(distance < shortestDistance){
					shortestDistance = distance;
					closestPair = pair;
				}
			}
		}
		return closestPair;
	}


	/**
	 * An implementation of merge-sort.
	 * Uses divide-and-conquer to sort an ArrayList of points.
	 * 
	 * @param points The ArrayList to sort.
	 * @param sortX Boolean used to sort on either X or Y coordinate.
	 */
	public static void sort(ArrayList<Point> points, boolean sortX) {
		ArrayList<Point> left;
		ArrayList<Point> right;
		int midPoint;

		if (points.size() > 1){
			midPoint = points.size() / 2;

			//divide the array into two halves
			left = new ArrayList<Point>(points.subList(0, midPoint));
			right = new ArrayList<Point>(points.subList(midPoint, points.size()));

			//sort each half and merge them together
			sort(left, sortX);
			sort(right, sortX);
			merge(points, left, right, sortX);
		}
	}


	/**
	 * Helper function for merge-sort. Merges two ArrayLists into the parent.
	 * 
	 * @param points The parent arrayList.
	 * @param left The left sub-list of the parent.
	 * @param right The right sub-list of the parent.
	 * @param sortX Boolean used to sort on either X or Y coordinate.
	 */
	public static void merge(ArrayList<Point> points, ArrayList<Point> left, ArrayList<Point> right, boolean sortX){
		ArrayList<Point> tempList = new ArrayList<Point>();
		int pointsIdx = 0;
		int leftIdx = 0;
		int rightIdx = 0;
		int tempIdx = 0;

		while((leftIdx < left.size()) && (rightIdx < right.size())){
			//sort based on X coordinate
			if (sortX){
				if(left.get(leftIdx).x < right.get(rightIdx).x){
					points.set(pointsIdx, left.get(leftIdx));
					leftIdx++;
				}
				else{
					points.set(pointsIdx, right.get(rightIdx));
					rightIdx++;
				}
			}
			//sort based on Y coordinate
			else{
				if(left.get(leftIdx).y < right.get(rightIdx).y){
					points.set(pointsIdx, left.get(leftIdx));
					leftIdx++;
				}
				else{
					points.set(pointsIdx, right.get(rightIdx));
					rightIdx++;
				}
			}
			pointsIdx++;
		}

		//check if left or right list still has elements remaining
		if (leftIdx < left.size()){
			tempList = left;
			tempIdx = leftIdx;
		}
		else {
			tempList = right;
			tempIdx = rightIdx;
		}

		//copy remaining elements
		for (int i = tempIdx; i < tempList.size(); i++){
			points.set(pointsIdx, tempList.get(i));
			i++;
		}
	}
}
