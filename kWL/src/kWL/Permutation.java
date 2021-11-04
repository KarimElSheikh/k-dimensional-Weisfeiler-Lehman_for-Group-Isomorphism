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

	public int[] array;

	public Permutation(int[] array) {
		this.array = array;
	}

	public static Permutation identityPermutation(int degree) {
		int[] array = new int[degree + 1];
		for (int i = 1; i <= degree; i++)
			array[i] = i;

		return new Permutation(array);
	}

	/**
	 * Compares this Permutation with another Permutation p. Assumes both arrays'
	 * lengths are equal (i.e., Both permutations have been initialized with the
	 * same degree). Compares starting from index 1.
	 * 
	 * @param p  the Permutation to compare to
	 * @return   int which is the result of the comparison (0 if equal)
	 */
	public int compareTo(Permutation p) {
		int cmp;

		for (int i = 1; i < array.length; i++) {
			cmp = array[i] - p.array[i];
			if( cmp != 0 )
				return cmp;
		}

		return 0;
	}

	public boolean equals(Object p) {
		return compareTo((Permutation) p) == 0;
	}
	
	/**
	 * Copies the Permutation starting from index 0 (index 0 is supposed to be
	 * irrelevant in my usage, but we copy it too anyway).
	 * 
	 * @return  Permutation object that is an exact copy of this Permutation
	 */
	public Permutation copy() {
		int[] arrayCopy = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			arrayCopy[i] = array[i];
		}

		return new Permutation(arrayCopy);
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
		int[] arrayOut = new int[array.length];

		for (int i = 1; i < array.length; i++) {
			arrayOut[i] = p.array[array[i]];
		}

		return new Permutation(arrayOut);
	}
	
	/**
	 * Returns a hash code for this {@code Permutation}.
	 * result. Assumes both arrays' lengths are equal which is the case if they've
	 * been initialized with the same degree.
	 * 
	 * @return  a hash code value for this {@code Permutation} object which is computed using
	 *          powers of a prime which is already determined.
	 */
	public int hashCode() {
	    int p = 31;
	    int m = 1000_000_009;
	    long hashValue = 0;
	    long pPow = 1;
	    
	    for (int i = 1; i < array.length; i++) {
			hashValue = (hashValue + array[i] * pPow) % m;
	        pPow = (pPow * p) % m;
	    }
	    
	    return (int) hashValue;
	}
}
