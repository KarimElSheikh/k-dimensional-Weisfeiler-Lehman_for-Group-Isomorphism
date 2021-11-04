/*
    k-dimensional Weisfeiler-Lehman for Group Isomorphism, a Java implementation
    of the Weisfeiler-Lehman combinatorial method with various launch
    configurations to test, analyze the method, as well as gathering info
    from running the method on finite groups. The implementation currently
    supports 2-dimensional Weisfeiler-Lehman and is planned to have support
    for any number of dimensions in the future (hence the name "k-dimensional").
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

package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import kWL.Permutation;

/**
 * This class implements the Comparator interface, comparing ArrayLists of
 * of Integers sequentially while always assuming that the 2 compared
 * ArrayLists have the same size.
 * 
 * @author Karim Elsheikh
 */
public class ArrayListComparator implements Comparator<ArrayList<Integer>>, Serializable {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = -2270921455737146094L;
	
	/**
	 * Compare Method that compares between 2 given ArrayLists of Integers lexicographically.
	 * (i.e., finds the first index where the Integers differ and compares them)
	 * 
	 * Assumes both ArrayLists have the same number of elements.
	 * 
	 * @param arrayList1  first ArrayList of Integers
	 * @param arrayList2  second ArrayList of Integers with the same size as the first Array
	 * @return int  result of the comparison
	 */
	public int compare(ArrayList<Integer> arrayList1, ArrayList<Integer> arrayList2) 
	{
		int cmp;
		for (int i = 0; i < arrayList1.size(); i++) {
			cmp = arrayList1.get(i) - arrayList2.get(i);
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	/**
	 * Compare Method that works the same as compare but additionally compares the lengths of both
	 * ArrayLists first.
	 * 
	 * @param arrayList1  first ArrayList of Integers
	 * @param arrayList2  second ArrayList of Integers
	 * @return int  result of the comparison
	 */
	public int compare2(ArrayList<Permutation> arrayList1, ArrayList<Permutation> arrayList2) 
	{
		int cmp = arrayList1.size() - arrayList2.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < arrayList1.size(); i++) {
			cmp = arrayList1.get(i).compareTo(arrayList2.get(i));
			if (cmp != 0) return cmp;
		}
		
		return 0;
    }
}
