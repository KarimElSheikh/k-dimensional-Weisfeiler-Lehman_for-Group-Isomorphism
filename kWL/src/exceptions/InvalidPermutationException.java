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

package exceptions;

/**
 * Checked exception is thrown during parsing a Permutation string if the
 * string is in a wrong format (i.e., does not match the cyclic format).
 * 
 * <p>The parsePermutation Method in the Representation class throws this
 * exception.
 * 
 * @author Karim Elsheikh
 */
public class InvalidPermutationException extends Exception {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = -7898722555145938677L;
	
	public InvalidPermutationException(String errorMessage) {
		super(errorMessage);
	}
}
