/*
    k-dimensional Weisfeiler-Lehman for Group Isomorphism, a Java implementation
    of the method with various tests to help analyze the method.
    Copyright (C) 2021 Karim Elsheikh

    This file is part of k-dimensional Weisfeiler-Lehman for Group Isomorphism,
    the Java project.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package kWL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Helpers is a class of static helper methods of various utility.
 * 
 * Currently, I am considering moving some/all of the methods from the Representation class to this class or
 * the opposite (From the Representation class to here) which is less likely.
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
	
	public static void createDirectory(String directoryPath) {
		
		Path path = Paths.get(directoryPath);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException exception) {
			//	Failed to create directory
				exception.printStackTrace();
			}
		}
	}
	
	/**
	 * Reads a serialized Java Object that was written to the hard drive and returns it.
	 * 
	 * @param	filePath	a string that is the path to the file that is to be read.
	 * 
	 * @return	Object		a java class instance that is simply casted as an "Object".
	 */
	public static Object ReadObjectFromFile(String filePath) {
		
		try {
			ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filePath));
			Object obj = objectIn.readObject();
		//	System.out.println("The Object has been read from the file");
			
			objectIn.close();
			return obj;
			
		} catch (IOException | ClassNotFoundException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public static void writeObjectToFile(Object serObj, String filePath) {
		
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath));
			objectOut.writeObject(serObj);
		//	System.out.println("The Object was successfully written to a file");
			
			objectOut.close();
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void appendUsingFileOutputStream(String fileName, String data) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(fileName), true);
		//	The above true boolean in 2nd arg tells the FileOutputStream instance to append instead of write to the file
			os.write(data.getBytes(), 0, data.length());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
