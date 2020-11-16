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

package group.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NavigableSet;

import datastructure.IndexedTreeMap2;

/**
 * Color is a class that is used to denote the color data for a single group/subgroup starting from
 * round 1 (the 2nd round) onwards. Each color is as defined in the Weisfeiler-Lehman method: an
 * integer denoting the index of the color and a multiset of pairs of integers that groups all the
 * "neighboring" colors in pairs. A Pair object as defined in this project just holds 2 integers
 * denoting the 2 colors.
 * 
 * @author Karim Elsheikh
 */
public class Color implements Comparable<Color>, Serializable {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = 5006072381800913170L;
	
	public Integer color;
	public IndexedTreeMap2<Pair> multiSet;
	
	/**
	 * Constructs a Color object with given Integer "color" and IndexedTreeMap2<Pair> "multiSet".
	 * The IndexedTreeMap2<Pair> "multiSet" represents the multiset of pairs of colors by mapping
	 * each pair of colors to an integer (its count).
	 *
	 * @param color     the Integer "color"
	 * @param multiSet  the IndexedTreeMap2<Pair> "multiSet"
	 */
	public Color(Integer color, IndexedTreeMap2<Pair> multiSet) {
		this.color = color;
		this.multiSet = multiSet;
	}
	
	/**
	 * Compares 2 Color objects by comparing their "color"'s and "multiSet"'s.
	 * 
	 * <p>It first compares the "color"'s of both objects, if they're found to be not equal, it
	 * would return a positive or negative number which is (this Pair object's "color" -
	 * "anotherColor"'s "color"), otherwise in case they have equal "color"'s, it would proceed to
	 * compare both "multiSet"'s, by comparing the number of the keys in this Color object's
	 * "multiSet" against the number of the keys in the other Color object's "multiSet", if the
	 * sizes are found to be not equal it would return a positive or negative number which is (this
	 * Color object's "multiSet"'s number of keys - "anotherColor"'s "multiSet"'s number of keys), 
	 * otherwise it would proceed to sequentially compare both "multiSet"'s key/value pairs in the
	 * natural ordering of the integer keys stopping at the first different key/value pair,
	 * returning either a subtraction of the 2 corresponding keys (this is checked first) or a
	 * subtraction of the 2 corresponding values if the keys are equal but their values (counts) are
	 * not. Only if all key/value pairs are equal, the Method would then return 0.
	 *
	 * @param anotherColor  the Color object to be compared against
	 * 
	 * @return  the value 0 if "anotherColor" is equal to this Color object, a value less than 0 if
	 *          this InitialColor object compares less, a value greater than 0 if this InitialColor
	 *          compares greater, all as explained above.
	 */
	public int compareTo(Color anotherColor) {
		int cmp = color - anotherColor.color;
		if (cmp != 0) return cmp;
		
		NavigableSet<Pair> keySet1 = multiSet.navigableKeySet();
		NavigableSet<Pair> keySet2 = anotherColor.multiSet.navigableKeySet();
		
		cmp = keySet1.size() - keySet2.size();
		if (cmp != 0) return cmp;
		
		Iterator<Pair> it1 = keySet1.iterator();
		Iterator<Pair> it2 = keySet2.iterator();
		while (it1.hasNext()) {
			Pair t1 = it1.next(), t2 = it2.next();
			cmp = t1.compareTo(t2);
			if (cmp != 0) return cmp; // Comparing the color (Pair)
			
			cmp = multiSet.get(t1) - anotherColor.multiSet.get(t2); // Comparing the count of the color
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	/**
	 * Checks for equality between 2 Color objects by comparing their "color"'s and
	 * "multiSet"'s through calling the compareTo Method.
	 * 
	 * <p>It works basically by calling the compareTo Method and returning true only in the case
	 * where the compareTo Method returns 0 which is exactly when both Color objects are considered
	 * equal as we've explained in the documentation for compareTo. Otherwise it would return false.
	 *
	 * @param anotherColor  the anotherColor object to be checked whether it is equal to this
	 *                      anotherColor object
	 * 
	 * @return  true if "anotherColor" is equal to this Color object, false otherwise
	 */
	public boolean equals(Color anotherColor) {
		return compareTo(anotherColor) == 0;
	}
}
