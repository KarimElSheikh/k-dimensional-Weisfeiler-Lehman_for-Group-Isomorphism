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

package utilities;

import java.io.Serializable;

/**
 * Pair is a class used to hold 2 integers, it is mainly required by the Color class.
 * 
 * @author Karim Elsheikh
 */
public class Pair implements Comparable<Pair>, Serializable {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = 5085418280083257080L;
	
	public int id1;
	public int id2;
	
	/**
	 * Constructs a Pair object with given int "id1" and int "id2"
	 * 
	 * @param id1  the 1st int argument
	 * @param id2  the 2nd int argument
	 */
	public Pair(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	
	/**
	 * Compares 2 Pair objects by comparing their "id1"'s and "id2"'s.
	 * 
	 * <p>It first compares both "id1"'s, if they're found to be not equal, it would return a
	 * positive or negative number which is (this Pair object's "id1" - "anotherPair"'s "id1"),
	 * otherwise in case they have equal "id1"'s, it would proceed to compare both "id2"'s, if
	 * they're found to be not equal, it would return a subtraction analogous to the above one,
	 * otherwise in case they also have equal "id2"'s, it  would return 0.
	 * 
	 * @param anotherPair  the Pair object to be compared against
	 * 
	 * @return  the value 0 if "anotherPair" is equal to this Pair object, a value less than 0 if
	 *          this Pair object compares less, a value greater than 0 if this Pair compares
	 *          greater, all as explained above.
	 */
	public int compareTo(Pair anotherPair) {
		int cmp = id1 - anotherPair.id1;
		if (cmp != 0) return cmp;
		
		return id2 - anotherPair.id2;
	}
	
	/**
	 * Checks for equality between 2 Pair objects by comparing their "id1"'s and "id2"'s through
	 * calling the compareTo Method.
	 * 
	 * <p>It works basically by calling the compareTo Method and returning true only in the case
	 * where the compareTo Method returns 0 which is exactly when both Pair objects are considered
	 * equal as we've explained in the documentation for compareTo. Otherwise it would return
	 * false.
	 * 
	 * @param anotherPair  the Pair object to be checked whether it is equal to this Pair object
	 * 
	 * @return  true if "anotherPair" is equal to this Pair object, false otherwise
	 */
	public boolean equals(Pair anotherPair) {
		return compareTo(anotherPair) == 0;
	}
}
