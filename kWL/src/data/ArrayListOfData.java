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
 * and an integer id.
 * <p>
 * In the main method of the {@link kWL.Checking} class, we use this object type this way — each
 * created ArrayListOfData object is used to associate a list of files ("{@link #arrayList}")
 * with each group (identified with its "id").
 * <p>
 * For each file in the list of files belonging to a certain group, we associate a {@link data.Data} object
 * comprised of the file size and crc32 hash of the file, stored as int and long respectively.
 * <br>
 * These Data objects' references are stored sequentially in "<tt>arrayList</tt>".
 * <p>
 * This object type is currently not used anywhere else in the project
 * other the <tt>Checking</tt> class.
 * 
 * @author Karim Elsheikh
 */
public class ArrayListOfData implements Comparable<ArrayListOfData> {
	
	public int id;
	public ArrayList<Data> arrayList;
	
	/**
	 * Constructs a new ArrayListOfData with an empty "<tt>arrayList</tt>"
 	 * and the specified "<tt>id</tt>".
	 *
	 * @param id  The integer number to associate with this <tt>ArrayListOfData</tt>
	 *            object (object's "<tt>id</tt>").
	 */
	public ArrayListOfData(int id) {
		this.id = id;
		arrayList = new ArrayList<Data>();
	}
	
	/**
	 * Constructs a new ArrayListOfData object with the specified "<tt>arrayList</tt>" and
	 * the specified "<tt>id</tt>".
	 *
	 * @param id     The integer number to associate with this <tt>ArrayListOfData</tt>
	 *               object (object's "<tt>id</tt>").
	 * @param array  The <tt>ArrayList</tt> to be used as base for this <tt>ArrayListOfData</tt>
	 *               object (object's "<tt>arrayList</tt>").
	 */
	public ArrayListOfData(int id, ArrayList<Data> arrayList) {
		this.id = id;
		this.arrayList = arrayList;
	}
	
	/**
	 * Compares 2 ArrayListOfData objects by comparing their "<tt>arrayList</tt>"s while
	 * ignoring the "<tt>id</tt>" field.
	 * <p>
	 * It first compares the sizes of the 2 "<tt>arrayList</tt>"s, if the sizes are found to be
	 * different, it would return a positive or negative number depending on the sizes of both
	 * "<tt>arrayList</tt>"s, otherwise in case they are of equal size, it would loop through
	 * sequentially and compare every <tt>Data</tt> object with its corresponding counterpart
	 * (positionally) in the other "<tt>arrayList</tt>". It will stop comparing at the first
	 * <tt>Data</tt> object that compares "less than" or "greater than" to its
	 * corresponding <tt>Data</tt> object, and return a non-zero number accordingly, or it will
	 * proceed to compare every <tt>Data</tt> object, if both "<tt>arrayList</tt>"s are found to
	 * contain equivalent <tt>Data</tt> objects in every position, then the 2
	 * <tt>ArrayListOfData</tt> objects are considered to be equal.
	 *
	 * @param anotherArrayListOfData  the <tt>ArrayListOfData</tt> object to be compared against.
	 * 
	 * @return  the integer value 0 if "<tt>anotherArrayListOfData</tt>" is equal to this
	 *          <tt>ArrayListOfData</tt> object (i.e., equal "<tt>arrayList</tt>" sizes and contents);
	 *          <br>
	 *          a value less than 0 if this <tt>ArrayListOfData</tt> object has an
	 *          "<tt>arrayList</tt>" of smaller size or its first <tt>Data</tt> object that
	 *          compares differently, compares less than its corresponding <tt>Data</tt> object
	 *          counterpart;
	 *          <br>
	 *          a value greater than 0 if this <tt>ArrayListOfData</tt> object has an
	 *          "<tt>arrayList</tt>" of greater size or its first <tt>Data</tt> object that compares
	 *          differently, compares greater than its corresponding <tt>Data</tt> object
	 *          counterpart.
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
