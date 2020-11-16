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

import java.util.ArrayList;

/**
 * ArrayListOfData is just a class that is comprised of an ArrayList of Data objects
 * along with an integer id.
 * 
 * <p>In the main method of the Checking class, we use this class this way — each created
 * ArrayListOfData object is used to associate a list of files ("arrayList" — Field name)
 * with each group (identified with its "id").<br>
 * For each file in the list of files belonging to a certain group, we associate a Data object
 * comprised of the file size and crc32 hash stored as int and long data types respectively.<br>
 * These Data objects' references are stored sequentially in "arrayList".</p>
 * 
 * <p>For simplicity of the code in the other classes, we made the
 * "id" and "arrayList" Fields public.
 * 
 * @author Karim Elsheikh
 */
public class ArrayListOfData implements Comparable<ArrayListOfData> {
	
	public int id;
	public ArrayList<Data> arrayList;
	
	/**
	 * Constructs an ArrayListOfData object with an empty ArrayList.
	 * Associates that ArrayListOfData with the integer "id".
	 *
	 * @param id  The integer number to associate with this ArrayListOfData
	 */
	public ArrayListOfData(int id) {
		this.id = id;
		arrayList = new ArrayList<Data>();
	}
	
	/**
	 * Constructs an ArrayListOfData object with an already-created ArrayList object as base.
	 * Associates that ArrayListOfData with the integer "id".
	 *
	 * @param id     The integer number to associate with this ArrayListOfData
	 * @param array  The ArrayList to be used as base for the ArrayListOfData
	 */
	public ArrayListOfData(int id, ArrayList<Data> arrayList) {
		this.id = id;
		this.arrayList = arrayList;
	}
	
	/**
	 * Compares 2 ArrayListOfData objects by comparing their ArrayList objects while
	 * ignoring the "id" field.
	 * 
	 * <p>It first compares the sizes (number of elements) of the 2 "arrayList"'s (belonging to
	 * the 2 ArrayListOfData objects), if the sizes are found to be not equal, it would return a
	 * positive or negative number depending on the sizes of both "arrayList"'s, otherwise in case
	 * they are of equal size, it would loop through sequentially and compare every Data object with
	 * its respective counterpart (positionally speaking) in the other "arrayList". It will stop
	 * comparing at the first Data object that compares "less than" or "greater than" to its
	 * respective Data object, or if at the end, both "arrayList"'s are found to contain equivalent
	 * Data objects in every position.</p>
	 *
	 * @param anotherArrayListOfData  the ArrayListOfData object to be compared against
	 * 
	 * @return  the value 0 if ArrayListOfData is equal to this ArrayListOfData (equal ArrayList
	 *          sizes and contents), a value less than 0 if this ArrayListOfData has an ArrayList
	 *          object of smaller size or its first Data object that compares differently compares
	 *          less than its respective Data object counterpart, a value greater than 0 if this
	 *          ArrayListOfData has an ArrayList object of greater size or its Data object that
	 *          compares differently compares greater than its respective Data object counterpart
	 */
	public int compareTo(ArrayListOfData anotherArrayListOfData) {
		int cmp = arrayList.size() - anotherArrayListOfData.arrayList.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < arrayList.size(); i++) {
			cmp = arrayList.get(i).compareTo(anotherArrayListOfData.arrayList.get(i));
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
}
