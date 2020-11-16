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

package kWL;

import java.util.TreeSet;

import exceptions.InvalidPermutationException;

/**
 * Class that provides static methods to deal with permutations given in cyclic format as a String.
 * The String format used is the format GAP uses when representing permutations.
 * Also contains a method to format and print a permutation instance.
 * 
 * @author Karim
 */
public class Representation {
	
	/**
	 * Parses a String given in the GAP format to a Permutation instance.
	 * Degree of the String must be given, and must be able to cover all the integers appearing
	 * in the Permutation.
	 * 
	 * @param	s		the String to parse
	 * @param	degree	the degree of the permutation
	 * 
	 *  Permutation should be given in Cyclic format "(x1,x2,x3)(y1,y2,y3,y4)" where x1, x2, y1, ... are integers.
	 *  Any amount of spaces or tab spaces anywhere is allowed.
	 *  Any integer should appear at most once and should be in the interval [1, degree].
	 */
	public static Permutation parsePermutation(String s, int degree) throws InvalidPermutationException {
		if (s.length() <= 1) throw new InvalidPermutationException(
			"String: \"" + s + "\" is not a" + " valid permutation in cyclic format"
		);
		
		Permutation p = Permutation.identityPermutation(degree);
		
		int first = -1, prev = -1, temp = -1;
		int h = 0, i = 0, j = 0;		
		int processingInteger = 0;
		boolean foundOpeningBracket = false;
		boolean error = false;
		
		TreeSet<Integer> ts = new TreeSet<Integer>();
		while (true) {
			if (!foundOpeningBracket) {
				if (s.charAt(i) == '(') {
					foundOpeningBracket = true;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\0') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 0) { // Didn't start processing integer
				if (Character.isDigit(s.charAt(i))) {
					processingInteger = 1;
					h = i;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\0') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 1) { // Started processing integer
				if (Character.isDigit(s.charAt(i))) {
					
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;

					temp = Integer.parseInt(s.substring(h, i));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						first = prev = temp;
					}
					else {
						p.array[prev] = temp;
						prev = temp;
					}
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
					
					temp = Integer.parseInt(s.substring(h, i));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						
					}
					else {
						p.array[prev] = temp;
						p.array[temp] = first;
						first = -1;
					}
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\0') {
					processingInteger = 2;
					
					j = i;
				}
				else {
					error = true;
					break;
				}
			}
			else if (processingInteger == 2) { // Whitespaces after integer
				if (Character.isDigit(s.charAt(i))) {
					error = true;
					break;
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;

					temp = Integer.parseInt(s.substring(h, j));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						first = prev = temp;
					}
					else {
						p.array[prev] = temp;
						prev = temp;
					}
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\0') {
					
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
					
					temp = Integer.parseInt(s.substring(h, j));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						
					}
					else {
						p.array[prev] = temp;
						p.array[temp] = first;
						first = -1;
					}
				}
				else {
					error = true;
					break;
				}
			}
			
			i++;
			if (i == s.length()) {
				if (foundOpeningBracket)
					error = true;
				break;
			}
		}
		
		if (error) throw new InvalidPermutationException(
			"String: \"" + s + "\" is not a" + " valid permutation in cyclic format"
		);
		
		return p;
	}
	
	/**
	 *  Same method as the above, except gets the start and end indices of where to parse in the String,
	 *  as in, it ignores everything not in the interval [start, end).
	 *  
	 * @param	s		the String to parse
	 * @param	degree	the degree of the permutation
	 * @param	start	start index
	 * @param	end		end index
	 */
	public static Permutation parsePermutation(String s, int start, int end, int degree) throws InvalidPermutationException {
		if (end-start <= 1) throw new InvalidPermutationException(
			"String: \"" + s + "\" is not a" + " valid permutation in cyclic format"
		);
		
		Permutation p = Permutation.identityPermutation(degree);
		
		int first = -1, prev = -1, temp = -1;
		int h = start, i = start, j = start;		
		int processingInteger = 0;
		boolean foundOpeningBracket = false;
		boolean error = false;
		
		TreeSet<Integer> ts = new TreeSet<Integer>();
		while (true) {
			if (!foundOpeningBracket) {
				if (s.charAt(i) == '(') {
					foundOpeningBracket = true;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\0') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 0) { // Didn't start processing integer
				if (Character.isDigit(s.charAt(i))) {
					processingInteger = 1;
					h = i;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\0') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 1) { // Started processing integer
				if (Character.isDigit(s.charAt(i))) {
					
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;
					
					temp = Integer.parseInt(s.substring(h, i));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						first = prev = temp;
					}
					else {
						p.array[prev] = temp;
						prev = temp;
					}
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
					
					temp = Integer.parseInt(s.substring(h, i));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						
					}
					else {
						p.array[prev] = temp;
						p.array[temp] = first;
						first = -1;
					}
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\0') {
					processingInteger = 2;
					
					j = i;
				}
				else {
					error = true;
					break;
				}
			}
			else if (processingInteger == 2) { // Whitespaces after integer
				if (Character.isDigit(s.charAt(i))) {
					error = true;
					break;
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;

					temp = Integer.parseInt(s.substring(h, j));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						first = prev = temp;
					}
					else {
						p.array[prev] = temp;
						prev = temp;
					}
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\0') {
					
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
					
					temp = Integer.parseInt(s.substring(h, j));
					if (temp >= 1 && temp <= degree && !ts.contains(temp)) {
						ts.add(temp);
					}
					else {
						error = true;
						break;
					}
					
					if (first == -1) {
						
					}
					else {
						p.array[prev] = temp;
						p.array[temp] = first;
						first = -1;
					}
				}
				else {
					error = true;
					break;
				}
			}
			
			i++;
			if (i == end) {
				if (foundOpeningBracket)
					error = true;
				break;
			}
		}
		
		if (error) throw new InvalidPermutationException(
			"String: \"" + s + "\" is not a" + " valid permutation in cyclic format"
		);
		
		return p;
	}
	
	/**
	 * Formats a permutation to the GAP format and prints it.
	 * 
	 * @param	p	the permutation instance
	 */
	public static void printCyclic(Permutation p) {
		boolean[] printed = new boolean[p.array.length];
		boolean printedSomething = false;
		
		for (int i = 1; i < p.array.length; i++) {
			if (!printed[i]) {
				if (p.array[i] == i) continue;
				printedSomething = true;
				System.out.print("(" + i);
				printed[i] = true;
				
				int j = p.array[i];
				while (j != i) {
					System.out.print("," + j);
					printed[j] = true;
					j = p.array[j];
				}
				System.out.print(')');
			}
		}
		
		if (printedSomething)
			System.out.println();
		else
			System.out.println("()");
	}
}
