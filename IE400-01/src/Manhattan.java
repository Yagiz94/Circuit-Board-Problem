import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class Manhattan {

	static int distance = 0;
	static int points_drilled = 0;
	static int maxDrill = 50;
	static int counter = 0;

	static int[] x_coordinates = { 41, 46, 7, 46, 32, 5, 28, 48, 49, 8, 49, 48, 25, 41, 8, 22, 46, 40, 48, 33, 2, 43,
			47, 34, 38, 38, 20, 33, 9, 36, 2, 14, 3, 5, 42, 35, 16, 48, 2, 22, 20, 39, 40, 10, 25, 23, 33, 36, 14, 34 };

	static int[] y_coordinates = { 9, 40, 16, 27, 9, 31, 33, 35, 38, 23, 5, 12, 46, 8, 42, 27, 50, 4, 23, 6, 49, 1, 39,
			41, 44, 5, 20, 13, 41, 22, 46, 10, 14, 8, 7, 44, 29, 28, 8, 43, 32, 18, 26, 21, 4, 12, 7, 10, 21, 3 };

	static int[] block_x_coordinates = { 29, 18, 37, 24, 16, 43, 40, 25, 29, 27, 10, 14, 22, 11, 38, 9, 11, 8, 11, 20 };
	static int[] block_y_coordinates = { 14, 42, 20, 9, 41, 45, 20, 6, 12, 19, 27, 12, 28, 33, 10, 6, 14, 15, 20, 23 };
	static int[] width = { 1, 1, 3, 1, 3, 3, 2, 2, 1, 2, 3, 2, 2, 1, 2, 2, 3, 2, 2, 3 };
	static int[] height = { 1, 3, 3, 3, 1, 1, 2, 3, 1, 3, 1, 2, 2, 3, 3, 3, 3, 2, 3, 1 };

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		int min_element = 0;
		int index_smallest = 0;
		int total_distance = 0;
		int x1 = 0;
		int y1 = 0;

		ArrayList<Integer> drilled_points_x = new ArrayList<Integer>();
		ArrayList<Integer> drilled_points_y = new ArrayList<Integer>();
		ArrayList<Integer> distances = new ArrayList<Integer>(); // holds distance values from the current positions

		for (int j = 0; j < maxDrill; j++) {
			/*
			 * maxDrill is updated if a coordinate is drilled or contains rectangle it is
			 * removed from the coordinate list
			 */
			maxDrill = x_coordinates.length;
			// System.out.println(maxDrill);
			distances = calculateDist(distances, x1, y1);
			min_element = Collections.min(distances);
			index_smallest = distances.indexOf(min_element);

			while (counter < block_x_coordinates.length && isInRectangle(x_coordinates[index_smallest],
					y_coordinates[index_smallest], block_x_coordinates[counter], block_y_coordinates[counter],
					width[counter], height[counter])) {

				/*
				 * while there is any rectangle on the path select the next shortest path and
				 * check rectangles again
				 */
				x_coordinates = removeTheElement(x_coordinates, index_smallest);
				y_coordinates = removeTheElement(y_coordinates, index_smallest);
				distances = calculateDist(distances, x1, y1);
				min_element = Collections.min(distances);
				index_smallest = distances.indexOf(min_element);
				counter++;
				distances.clear();
			}

			/* update the position to drilled location's coordinates */

			x1 = x_coordinates[index_smallest];
			y1 = y_coordinates[index_smallest];
			drilled_points_x.add(x1);
			drilled_points_y.add(y1);
			points_drilled++;

			total_distance = total_distance + min_element;

			x_coordinates = removeTheElement(x_coordinates, index_smallest);
			y_coordinates = removeTheElement(y_coordinates, index_smallest);

			distances.clear();
			if (points_drilled == 50)
				break;

		}

		/* On the final step, return back to initial point which is (0,0) */
		int returnBackValue = Manhattan(0, 0, drilled_points_x.get(drilled_points_x.size() - 1),
				drilled_points_y.get(drilled_points_y.size() - 1));
		drilled_points_x.add(0);
		drilled_points_y.add(0);

		total_distance += returnBackValue;

		/* Printing the values */
		System.out.println("Drilled x coordinates: " + drilled_points_x.toString());
		System.out.println("Drilled y coordinates: " + drilled_points_y.toString());
		System.out.println("x_coordinates.length: " + drilled_points_x.size());
		System.out.println("y_coordinates.length: " + drilled_points_y.size());
		System.out.println("\nTotal distance traveled is: " + total_distance);

		/* Print all output to a text file */
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		writer.println("Drilled x coordinates: " + drilled_points_x.toString());
		writer.println("Drilled y coordinates: " + drilled_points_y.toString());
		writer.println("x_coordinates.length: "  + drilled_points_x.size());
		writer.println("y_coordinates.length: " + drilled_points_y.size());
		writer.println("\nTotal distance traveled is: " + total_distance);
		writer.close();
	}

	public static int Manhattan(int x1, int y1, int x2, int y2) {
		return Math.abs((x1 - x2)) + Math.abs((y1 - y2));
	}

	public static ArrayList<Integer> calculateDist(ArrayList<Integer> A, int x, int y) {

		/* x & y are current location coordinates */
		for (int i = 0; i < maxDrill; i++) {
			/* calculate the distances from current position */
			int distance = Manhattan(x_coordinates[i], y_coordinates[i], x, y);
			A.add(distance);
		}
		return A;
	}

	/*
	 * This method is used to remove the array indexes that are traveled or whose
	 * path intersects with a rectangle
	 */
	public static int[] removeTheElement(int[] arr, int index) {

		// If the array is empty
		// or the index is not in array range
		// return the original array
		if (arr == null || index < 0 || index >= arr.length) {

			return arr;
		}

		// Create another array of size one less
		int[] anotherArray = new int[arr.length - 1];

		// Copy the elements except the index
		// from original array to the other array
		for (int i = 0, k = 0; i < arr.length; i++) {

			// if the index is
			// the removal element index
			if (i == index) {
				continue;
			}

			// if the index is not
			// the removal element index
			anotherArray[k++] = arr[i];
		}

		// return the resultant array
		return anotherArray;
	}

	/*
	 * checks if the x1 and y1 values intersect with any given rectangle's
	 * coordinates
	 */
	public static boolean isInRectangle(int x1, int y1, int x_val, int y_val, int width, int height) {
		boolean result = true;

		if ((x_val <= x1 && x1 <= x_val + width) && (y_val <= y1 && y1 <= y_val + height))
			result = true;

		else
			result = false;

		return result;
	}
}