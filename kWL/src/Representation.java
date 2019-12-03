import java.util.TreeSet;

import exceptions.InvalidPermutationException;

public class Representation {
	
	// Permutation given in Cyclic format "(x1,x2,x3)(y1,y2,y3,y4)"
	// with any amount of spaces or tab spaces.
	// An integer should appear at most once and should be between [1, degree]
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
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 0) { // Didn't start processing integer
				if (Character.isDigit(s.charAt(i))) {
					processingInteger = 1;
					h = i;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t') {
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
					
					foundOpeningBracket = false;
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t') {
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
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t') {
					
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
					
					foundOpeningBracket = false;
				}
				else {
					error = true;
					break;
				}
			}
			
			i++;
			if (i == s.length()) {
				if ((foundOpeningBracket || processingInteger >= 1))
					error = true;
				break;
			}
		}
		
		if (error) throw new InvalidPermutationException(
			"String: \"" + s + "\" is not a" + " valid permutation in cyclic format"
		);
		
		return p;
	}
	
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
