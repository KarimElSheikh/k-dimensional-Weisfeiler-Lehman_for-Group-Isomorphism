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

import org.junit.Test;

public class PermutationTest{
	
	@Test
	public void testIdentityPermutation(){
		int degree = 3;
		Permutation identityPermutation = Permutation.identityPermutation(degree);
		for (int i = 1; i <= degree; i++) {
			assert(identityPermutation.array[i] == i);
		}
	}
	
	@Test
	public void testCompareTo(){
		Permutation alpha = new Permutation(new int[] {0, 2, 3, 1, 4});
		Permutation beta = new Permutation(new int[] {0, 3, 4, 1, 2});
		
		assert(alpha.compareTo(beta) == -1);
		assert(beta.compareTo(alpha) == 1);
		
		Permutation c = new Permutation(new int[] {8, 2, 3, 4, 1});
		assert(alpha.compareTo(c) == -3);
		assert(c.compareTo(alpha) == 3);
	}
	
	@Test
	public void testEqualsPermutation(){
		Permutation alpha = new Permutation(new int[] {0, 2, 3, 1, 4});
		Permutation beta = new Permutation(new int[] {0, 3, 4, 1, 2});
		
		assert(!alpha.equals(beta));
		assert(alpha.equals(alpha));
		assert(alpha.equals(new Permutation(new int[] {0, 2, 3, 1, 4})));
		assert(new Permutation(new int[] {0, 2, 3, 1, 4}).equals(alpha));
		assert(new Permutation(new int[] {15, 3, 4, 1, 2}).equals(beta));
		
		Permutation c = new Permutation(new int[] {0, 2, 3, 4, 1});
		Permutation c_modified1 = new Permutation(new int[] {0, 2, 3, 4, 1, 6, 5});
		assert(c.equals(c_modified1));
	}
	
	@Test
	public void testCopy(){
		Permutation alpha = new Permutation(new int[] {0, 2, 3, 1, 4});
		Permutation copyOfAlpha = alpha.copy();
		assert(alpha.equals(copyOfAlpha));
	}
	
	@Test
	public void testMultiply(){
		Permutation alpha = new Permutation(new int[] {0, 2, 3, 1, 4});
		Permutation beta = new Permutation(new int[] {0, 3, 4, 1, 2});
		
		Permutation alphaBeta = alpha.multiply(beta);
		assert(alphaBeta.equals(new Permutation(new int[] {0, 4, 1, 3, 2})));
		Permutation betaAlpha = beta.multiply(alpha);
		assert(betaAlpha.equals(new Permutation(new int[] {0, 1, 4, 2, 3})));
	}
}
