package kWL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Helpers is a class of static helper methods that mainly aid in the parsing of the groups.
 * 
 * Currently, I am considering moving some/all of the methods from the Representation class to this class.
 * A Less likely choice is to move some methods from this class to the Representation class.
 * 
 * @author Karim Elsheikh
 */
public class Helpers {
	
	static Random random = new Random();
	
	public static Integer[] copyAndAdd(Integer[] array, int elementToAdd) {
		Integer[] arrayOut = new Integer[array.length+1];
		
		for (int i = 0; i < arrayOut.length-1; i++) {
			arrayOut[i] = array[i];
		}
		
		arrayOut[array.length] = elementToAdd;
		return arrayOut;
	}
	
	public static void appendUsingFileOutputStream(String fileName, String data) {
		OutputStream os = null;
		try {
			// the below true boolean tells the FileOutputStream to append (instead of writing to a new file)
			os = new FileOutputStream(new File(fileName), true);
			os.write(data.getBytes(), 0, data.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Scrappy method that was used to generate a random Integer between 10 and 385 (both inclusive).
	 * Only used in the main method in this class.
	 */
	public static int randomize() {
		return random.nextInt(376) + 10;
	}
	
	/**
	 * main method was mainly used initially to test the methods in this class and
	 * was also used for some scrappy testing.
	 */
	public static void main(String[] args) {
		String s = "Stream.txt";
		appendUsingFileOutputStream(s, randomize() + "\n");
	}
	
}
