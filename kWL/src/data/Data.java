/*
    k-dimensional Weisfeiler-Lehman for Group Isomorphism, a Java implementation
    of the method with various tests to help analyze the method.
    Copyright (C) 2020 Karim Elsheikh

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

package data;

/**
 * Data is a class that is used to hold some information about a file: its size and the crc32 hash.
 * The crc32 hash needs unsigned 32 bits to be stored, we store it in a long data type.
 * 
 * <p>This class is mainly used in the main method of the class 'Checking'.
 * 
 * @author Karim Elsheikh
 */
public class Data implements Comparable<Data> {
	
	public int size;
	public long crc32;
	
	/**
	 * Constructs a Data object with given a size and crc32 hash.
	 *
	 * @param size   The integer representing the size of the file in bytes
	 * @param crc32  The long integer representing the crc32 hash of this file
	 */
	public Data(int size, long crc32) {
		this.size = size;
		this.crc32 = crc32;
	}
	
	/**
	 * Compares 2 Data objects by comparing their file sizes and crc32 hashes.
	 * 
	 * <p>It first compares the "size"'s of both Data objects, if the "size"'s are found to be not
	 * equal, it would return a positive or negative number depending on the relation of the "size"
	 * of the Data object the Method was called on when compared to the "size" of the "anotherData"
	 * object passed as an argument, otherwise in case they have of equal "size"'s, it would compares
	 * the crc32 hash ("crc32") of both Data objects, if the crc32 hashes are also found to be
	 * equal, it would return 0, otherwise it subtracts the crc32 hash of "anotherData" and returns
	 * the result.
	 *
	 * @param anotherData  the Data object to be compared against
	 * 
	 * @return  the value 0 if "anotherData" is equal to this Data object (equal "size" and crc32
	 *          hash), a value less than 0 if this Data object has smaller "size" or if they have
	 *          equal "size"'s but this Data object has a smaller crc32 hash, a value greater than 0
	 *          if this Data object has greater "size" or if they have equal "size"'s but this Data
	 *          object has a greater crc32 hash
	 */
	public int compareTo(Data anotherData) {
		int cmp = size - anotherData.size;
		if (cmp != 0)
			return cmp;
		
		long Lcmp = crc32 - anotherData.crc32;
		cmp = (int) (Lcmp >> 32);
		if (cmp != 0)
			return cmp;
		/*
		 * Compares the highest 32 bits first (note that this is a signed shift and hence,
		 * if there is any signed contribution in the highest 32 bits it will be preserved).
		 */
		
		return (int) (Lcmp);
		// Returns the lowest 32 bits (whether equal or different, this will decide as all the others were equal)
	}
	
	/**
	 * Checks for equality between 2 Data objects by comparing their file sizes and crc32 hashes
	 * through calling the compareTo Method.
	 * 
	 * <p>It works basically by calling the compareTo Method and returning true only in the case
	 * where the compareTo Method returns 0 which is exactly when both Data objects are considered
	 * equal as we've explained in the documentation for compareTo. Otherwise it would return false.
	 *
	 * @param anotherData  the Data object to be checked whether it is equal to this Data object
	 * 
	 * @return  true if "anotherData" is equal to this Data object (equal "size" and crc32 hash),
	 *          false otherwise
	 */
	public boolean equals(Data anotherData) {
		return compareTo(anotherData) == 0;
	}
	
	/**
	 * Returns a string that represents the values that this Data object holds in a clear
	 * way. Useful for debugging.
	 * through calling the compareTo Method.
	 * 
	 * @return  a string that represents the "size" and "crc32" values that this Data object holds
	 */
	public String toString() {
		return "size: " + size + ", crc32: " + Long.toHexString(crc32); 
	}
}
