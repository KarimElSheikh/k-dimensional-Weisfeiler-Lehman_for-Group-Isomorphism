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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * InitialColor is a class used to denote the initial color data for a single group/subgroup
 * (defined by its generators) — "generatedBy", an ArrayList object which stores references each of
 * which corresponds to one of the group's elements (except the identity), each reference is a
 * reference to a 1D Integer array that is comprised of a minimum length sequence of generators that
 * generate the specific element. The InitialColor object also has the field "yield", a 2D Integer
 * array that is used to hold the result of the multiplication of each specific element by a
 * generator (the 1-based index of the element that is the result of this multiplication).
 * 
 * <p>An explanation in detail is found at the generatedByAndYields Method in "kWLClass".
 * 
 * @author Karim Elsheikh
 */
public class InitialColor implements Comparable<InitialColor>, Serializable {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = 8304232391352687748L;
	
	public ArrayList<Integer[]> generatedBy;
	public Integer[][] yield;
	
	/**
	 * Constructs an InitialColor object with given ArrayList of Integer arrays "generatedBy" and
	 * 2D Integer array "yield".
	 *
	 * @param generatedBy  the ArrayList of Integer arrays
	 * @param yield        the 2D Integer array "yield"
	 */
	public InitialColor(ArrayList<Integer[]> generatedBy, Integer[][] yield) {
		this.generatedBy = generatedBy;
		this.yield = yield;
	}
	
	/**
	 * Compares 2 InitialColor objects by comparing their "generatedBy"'s and "yield"'s.
	 * 
	 * <p>It first compares the sizes of both "generatedBy" objects, if the sizes are found to be
	 * not equal, it would return a positive or negative number depending on the relation of the
	 * size of this InitialColor object's "generatedBy" when compared to the size of the
	 * "anotherInitialColor"'s "generatedBy", otherwise in case they have of equal size's, it would
	 * proceed to compare each Integer array entry by length and then each of the array's Integers
	 * by the corresponding Integer in case the Integer arrays have equal lengths, otherwise it will
	 * stop, and return (this Integer array's length - "anotherInitialColor"'s corresponding array's
	 * length). If corresponding Integers stored in the arrays are different it will stop and return
	 * (this integer - "anotherInitialColor"'s corresponding integer).
	 * 
	 * <p>Moving on to comparing the 2 "yield"'s, the Method will just compare them analogously to how
	 * it compared the 2 "generatedBy"'s, only returning 0 if everything is equal in the end.
	 *
	 * @param anotherInitialColor  the InitialColor object to be compared against
	 * 
	 * @return  the value 0 if "anotherInitialColor" is equal to this InitialColor object, a value
	 *          less than 0 if this InitialColor object compares less, a value greater than 0 if
	 *          this InitialColor compares greater, all as explained above.
	 */
	public int compareTo(InitialColor anotherInitialColor) {
		int cmp = generatedBy.size() - anotherInitialColor.generatedBy.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < generatedBy.size(); i++) {
			int length1 = generatedBy.get(i).length;
			int length2 = anotherInitialColor.generatedBy.get(i).length;
			cmp = length1 - length2;
			if (cmp != 0) return cmp;
			
			for (int j = 0; j < length1; j++) {
				cmp = generatedBy.get(i)[j] - anotherInitialColor.generatedBy.get(i)[j];
				if (cmp != 0) return cmp;
			}
		}
		
		for (int i = 0; i < yield.length; i++) {			
			for (int j = 0; j < yield[i].length; j++) {
				cmp = yield[i][j] - anotherInitialColor.yield[i][j];
				if (cmp != 0) return cmp;
			}
		}
		/*
		 * yield.length is not compared
		 * i.e., number of generators is assumed to be the same
		 * yield[i].length is not compared
		 * i.e., order of groups is assumed to be the same
		 */
		
		return 0;
	}
	
	/**
	 * Checks for equality between 2 InitialColor objects by comparing their "generatedBy"'s and
	 * "yield"'s through calling the compareTo Method.
	 * 
	 * <p>It works basically by calling the compareTo Method and returning true only in the case
	 * where the compareTo Method returns 0 which is exactly when both InitialColor objects are
	 * considered equal as we've explained in the documentation for compareTo. Otherwise it would
	 * return false.
	 *
	 * @param anotherInitialColor  the InitialColor object to be checked whether it is equal to this
	 *                             InitialColor object
	 * 
	 * @return  true if "anotherInitialColor" is equal to this InitialColor object, false otherwise
	 */
	public boolean equals(Object anotherInitialColor) {
		return compareTo((InitialColor) anotherInitialColor) == 0;
	}
	
	public int hashCode() {
	    int p = 31;
	    int m = 1000_000_009;
	    long hashValue = 0;
	    long pPow = 1;
	    
	    for(Integer[] genSequence : generatedBy) {
	    	for(int entry : genSequence) {
				hashValue = (hashValue + entry * pPow) % m;
		        pPow = (pPow * p) % m;
	    	}
	    }
	    
	    int i = 1;
	    for(Integer[] yieldsFromMultByThisGen : yield) {
    		hashValue = (hashValue + (i+1) * pPow) % m;
	        pPow = (pPow * p) % m;
	    	
	    	for(int entry : yieldsFromMultByThisGen) {
	    		hashValue = (hashValue + entry * pPow) % m;
		        pPow = (pPow * p) % m;
	    	}
	    	
	    	i++;
	    }
		
		return (int) hashValue;
	}
}
