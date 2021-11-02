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

/**
 * Class Permutation defines a permutation that in the context of Group Theory, typically
 * acts on a subset of the natural numbers. It is comprised of an Integer array. Each
 * integer stored at a specific index in the array defines the effect of this permutation
 * on this index (i.e., it permutes this index to that integer). We ignore the index 0
 * when dealing with the array (for example in the multiply Method).
 * 
 * @author Karim
 */
public class Permutation implements Comparable<Permutation> {

	public Integer[] array;

	public Permutation(Integer[] array) {
		this.array = array;
	}

	public static Permutation identityPermutation(int degree) {
		Integer[] array = new Integer[degree + 1];
		for (int i = 1; i <= degree; i++)
			array[i] = i;

		return new Permutation(array);
	}

	/**
	 * Multiplies this Permutation with another Permutation p and returns the
	 * result. Assumes both arrays' lengths are equal which is the case if they've
	 * been initialized with the same degree.
	 * 
	 * @param p  the 2nd multiplicand of the multiplication operation
	 * @return   Permutation object that is the result of multiplying this Permutation by p
	 */
	public Permutation multiply(Permutation p) {
		Integer[] arrayOut = new Integer[array.length];

		for (int i = 1; i < array.length; i++){
			arrayOut[i] = p.array[array[i]];
		}

		return new Permutation(arrayOut);
	}

	/**
	 * Copies the Permutation starting from index 0 (index 0 is supposed to be
	 * irrelevant in my usage, but we copy it too anyway).
	 * 
	 * @return  Permutation object that is an exact copy of this Permutation
	 */
	public Permutation copy() {
		Integer[] arrayCopy = new Integer[array.length];

		for (int i = 0; i < array.length; i++){
			arrayCopy[i] = array[i];
		}

		return new Permutation(arrayCopy);
	}

	/**
	 * Compares this Permutation with another Permutation p. Assumes both arrays'
	 * lengths are equal which is the case if they've been initialized with the same
	 * degree. Compares starting from index 1.
	 * 
	 * @param p  the Permutation to compare to
	 * @return   int which is the result of the comparison (0 if equal)
	 */
	public int compareTo(Permutation p) {
		int cmp;

		for (int i = 1; i < array.length; i++){
			cmp = array[i] - p.array[i];
			if( cmp != 0 )
				return cmp;
		}

		return 0;
	}

	public boolean equals(Permutation p) {
		return compareTo(p) == 0;
	}
}
