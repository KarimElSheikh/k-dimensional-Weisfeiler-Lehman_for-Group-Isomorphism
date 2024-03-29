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

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datastructure.IndexedTreeSet;
import datastructure.IndexedTreeMap2;
import exceptions.InvalidPermutationException;
import group.data.Color;
import group.data.InitialColor;
import utilities.ArrayListComparator;
import utilities.Pair;

/**
 * This class contains the implementation of the kWL method for group isomorphism.
 * kWL() and kWLinv() are the main methods of this class and the whole project. They are very similar,
 * the first method uses the k-dimensional Weisfeiler Lehman method to compare between 2 groups and the other
 * method uses the same on a single group, but stores all data about that group in all rounds.
 * All groups are assumed to be finite permutation groups of finite degree.
 * The usage of these methods is outlined in the current scrappy main method (as code)
 * as well as in the commented out main method.
 * Make sure the file "1.g" is in the project folder or in the working directory while running the main
 * method of this class.
 * 
 * @author Karim Elsheikh
 */
public class kWLClass {
	
	static int maxRounds;
	static int roundsTaken;
	static int globalCounter;
	static int numberOfColorClasses1;
	static int numberOfColorClasses2;
	static int globalOrder;
	
	static ArrayListComparator arrayListComparator = new ArrayListComparator();
	static String s;
	
	public static boolean kWL(Permutation[] S1, Permutation[] S2, int degree1, int degree2) {
		Permutation[] G1, G2;
		G1 = generate(S1, degree1);
		G2 = generate(S2, degree2);
		
		if (G1.length != G2.length) {
			System.out.println("Non-isomorphic: Orders of the two groups when fully generated are not equal.");
			System.out.println(G1.length + " and " + G2.length);
			roundsTaken = 0;
			numberOfColorClasses1 = -1;
			numberOfColorClasses2 = -1;
			return false;
		}
		int order = G1.length;
		int p = 0;
		
		TreeMap<ArrayList<Integer>, InitialColor> E1 = new TreeMap<ArrayList<Integer>, InitialColor> (arrayListComparator);
		TreeMap<ArrayList<Integer>, InitialColor> E2 = new TreeMap<ArrayList<Integer>, InitialColor> (arrayListComparator);
	//	Maps from pair of indices to Pair (color in Round 0)
		
		IndexedTreeSet<InitialColor> uniqueInitialColors = new IndexedTreeSet<InitialColor>();
	//	Set containing all different Pairs, to allow mapping colors to integers
		
		IndexedTreeMap2<Integer> C1 = new IndexedTreeMap2<Integer>();
		IndexedTreeMap2<Integer> C2 = new IndexedTreeMap2<Integer>();
	//	IndexedTreeMap<Integer, Integer> with increment
	//	Multisets of the colors in each group, where the colors are referred to by integers
		
		TreeMap<ArrayList<Integer>, Integer> M1 = new TreeMap<ArrayList<Integer>, Integer> (arrayListComparator);
		TreeMap<ArrayList<Integer>, Integer> M2 = new TreeMap<ArrayList<Integer>, Integer> (arrayListComparator);
	//	Maps from pair of indices to color index
		
	//	Round 0
		for (int i = 1; i < order; i++) {
			for (int j = 1; j < order; j++) {
				if (i == j)
					continue;
				
				ArrayList<Integer> A1 = new ArrayList<Integer> (Arrays.asList(i, j));
				ArrayList<Integer> A2 = new ArrayList<Integer> (Arrays.asList(i, j));
				
				InitialColor X1 = generatedByAndYields(G1, A1, degree1);
				InitialColor X2 = generatedByAndYields(G2, A2, degree2);
				
				uniqueInitialColors.add(X1);
				uniqueInitialColors.add(X2);
				
				E1.put(A1, X1);
				E2.put(A2, X2);
			}
		}
		
		for (int i = 1; i < order; i++) {
			for (int j = 1; j < order; j++) {
				if (i == j)
					continue;
				
				ArrayList<Integer> A1 = new ArrayList<Integer> (Arrays.asList(i, j));
				ArrayList<Integer> A2 = new ArrayList<Integer> (Arrays.asList(i, j));
				
				InitialColor X1 = E1.get(A1);
				InitialColor X2 = E2.get(A2);
				
				Integer t1 = uniqueInitialColors.entryIndex(X1);
				Integer t2 = uniqueInitialColors.entryIndex(X2);
				
				C1.increment(t1);
				C2.increment(t2);
				
				M1.put(A1, t1);
				M2.put(A2, t2);
			}
		}
	//	End of Round 0
		
		System.out.println("Round " + 0 + ":");
		System.out.println("Number of unique Color Classes (in both Groups): " + uniqueInitialColors.size());
		if (!compareColors(C1, C2, p, false)) {
			roundsTaken = p;
			return false;
		}
		
		TreeMap<ArrayList<Integer>, Color> F1 = new TreeMap<ArrayList<Integer>, Color> (arrayListComparator);
		TreeMap<ArrayList<Integer>, Color> F2 = new TreeMap<ArrayList<Integer>, Color> (arrayListComparator);
		//	Maps from pair of indices to Color (color starting from Round 1)
		
		IndexedTreeSet<Color> uniqueColors = new IndexedTreeSet<Color>();
		//	Set containing all different Colors, to allow mapping Colors to integers
		
		TreeMap<Integer, Integer> colourIndexToNewIndex1 = new TreeMap<Integer, Integer>();
		TreeMap<Integer, Integer> colourIndexToNewIndex2 = new TreeMap<Integer, Integer>();
		//	Maps the old color's index to the new color's index, used to check convergence
		
		IndexedTreeMap2<Integer> D1;
		IndexedTreeMap2<Integer> D2;
		//	IndexedTreeMap<Integer, Integer> with increment
		//	Multisets of the >new< colors in each group, where the colors are referred to by integers
		
		TreeMap<ArrayList<Integer>, Integer> N1;
		TreeMap<ArrayList<Integer>, Integer> N2;
		//	Maps from pair of indices to >new< color index
		
		boolean converges1 = false;
		boolean converges2 = false;
		
		//	Start of Rounds ≥ 1
		for (p = 1; p <= maxRounds; p++) {
			converges1 = true;
			converges2 = true;
			
			D1 = new IndexedTreeMap2<Integer>();
			D2 = new IndexedTreeMap2<Integer>();
			N1 = new TreeMap<ArrayList<Integer>, Integer> (arrayListComparator);
			N2 = new TreeMap<ArrayList<Integer>, Integer> (arrayListComparator);
			
			for (int i = 1; i < order; i++) {
				for (int j = 1; j < order; j++) {
					if (i == j)
						continue;
					
					// IndexedTreeMap<Integer, Integer> with increment
					IndexedTreeMap2<Pair> t1 = new IndexedTreeMap2<Pair>();
					IndexedTreeMap2<Pair> t2 = new IndexedTreeMap2<Pair>();
					for (int k = 1; k < order; k++) {
						if (k != i && k != j) {
							ArrayList<Integer> A1 = new ArrayList<Integer> (Arrays.asList(k, j));
							ArrayList<Integer> A2 = new ArrayList<Integer> (Arrays.asList(i, k));
							t1.increment(new Pair(M1.get(A1), M1.get(A2)));
							t2.increment(new Pair(M2.get(A1), M2.get(A2)));
						}
					}
					
					ArrayList<Integer> A = new ArrayList<Integer> (Arrays.asList(i, j));
					
					Color c1 = new Color(M1.get(A), t1);
					Color c2 = new Color(M2.get(A), t2);
					
					F1.put(A, c1);
					F2.put(A, c2);
					
					uniqueColors.add(c1);
					uniqueColors.add(c2);
				}
			}
			
			for (int i = 1; i < order; i++) {
				for (int j = 1; j < order; j++) {
					if (i == j)
						continue;
					
					ArrayList<Integer> A = new ArrayList<Integer> (Arrays.asList(i, j));
					
					Integer c1_index = uniqueColors.entryIndex(F1.get(A));
					Integer c2_index = uniqueColors.entryIndex(F2.get(A));
					
					if (converges1) {
						Integer temp = colourIndexToNewIndex1.put(M1.get(A), c1_index);
						if (temp != null && !temp.equals(c1_index)) {
							converges1 = false;
						}
					}
					if (converges2) {
						Integer temp = colourIndexToNewIndex2.put(M2.get(A), c2_index);
						if (temp != null && !temp.equals(c2_index)) {
							converges2 = false;
						}
					}
					
					N1.put(A, c1_index);
					N2.put(A, c2_index);
					D1.increment(c1_index);
					D2.increment(c2_index);
				}
			}
			
			System.out.println("Round " + p + ":");
			System.out.println("Number of unique Color Classes (in both Groups): " + uniqueColors.size());
			if (!compareColors(D1, D2, p, converges1 && converges2)) {
				roundsTaken = p;
				return false;
			}
			
		//	Cleanup at the end of a round
			colourIndexToNewIndex1.clear();
			colourIndexToNewIndex2.clear();
			uniqueColors.clear();
			
			M1.clear();
			M2.clear();
			M1 = N1;  // New colors will now be the old colors (for the next round)
			M2 = N2;
			
			C1.clear();
			C2.clear();
			C1 = D1;  // New colors will now be the old colors (for the next round)
			C2 = D2;
			
			F1.clear();
			F2.clear();
			if (converges1 && converges2)
				break;
		}
	//	End of Rounds ≥ 1
		
		if (!converges1 || !converges2)
			p--;
		
		if (converges1 && converges2) {
			System.out.println("Verdict after converging in round " + p + " of 2-dim Weisfeiler-Lehman: No difference was detected (Isomorphic?).");
		}
		else {
			System.out.println("Verdict after round " + p + " of 2-dim Weisfeiler-Lehman: No difference was detected (Isomorphic?).");
		}
		
		roundsTaken = p;
		return true;
	}
	
	public static boolean kWLinv(Permutation[] S, int degree, int index) throws IOException {
		Permutation[] G;
		G = generate(S, degree);
		
		int order = G.length;
		int curRound = 0;
		
		HashSet<InitialColor> uniqueInitialColors = new HashSet<>();
		// Indexed TreeSet containing all the different colors, to give each color an index (a number).		
		
		TreeMap<ArrayList<Integer>, Integer> M = new TreeMap<>(arrayListComparator);
		// TreeMap that maps from a pair of element indices to an index of color.
		
		// Round 0 - Start
		
		System.out.println("Round: 0");
		// Discovering the colors step:
		System.out.println("Generating colors");
		for (int i = 1; i < order; i++) {
			System.out.println("i: " + (i+1));
			for (int j = 1; j < order; j++) {
				if (i == j)
					continue;
				
				ArrayList<Integer> A = new ArrayList<Integer> (Arrays.asList(i, j));
				
				InitialColor X = generatedByAndYields(G, A, degree);
				
				uniqueInitialColors.add(X);
			}
		}
		int numberOfColorClasses = uniqueInitialColors.size();
		ArrayList<InitialColor> uniqueInitialColors_AL = new ArrayList<>(uniqueInitialColors);
		uniqueInitialColors.clear(); uniqueInitialColors = null;
		Collections.sort(uniqueInitialColors_AL);
		HashMap<InitialColor, Integer> HM_initialColors = new HashMap<>();
		for(int i = 0; i < numberOfColorClasses; i++) HM_initialColors.put(uniqueInitialColors_AL.get(i), i);
		int[] C = new int[numberOfColorClasses];
		// Array that maps the index of each color to its count (number of appearances).
		
		// Fill "M", mapping from every pair of element indices to the index of the pair's color.
		// Fill "C", mapping from every index of a color to its count.
		System.out.println("Mapping tuples to color indices");
		for (int i = 1; i < order; i++) {
			System.out.println("i: " + (i+1));
			for (int j = 1; j < order; j++) {
				if (i == j)
					continue;
				
				ArrayList<Integer> A = new ArrayList<Integer> (Arrays.asList(i, j));
				
				InitialColor X = generatedByAndYields(G, A, degree);
				
				int t = HM_initialColors.get(X);
				
				C[t]++;
				M.put(A, t);
			}
		}
		
		// Round 0 - End
		
		Helpers.createDirectory(System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + "0");
		List<String> lines = Arrays.asList(Integer.toString(numberOfColorClasses));
		Path file = Paths.get(System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + "0/numberOfColorClasses.txt");
		Files.write(file, lines, StandardCharsets.UTF_8);
		
		 Helpers.writeObjectToFile(uniqueInitialColors_AL, System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + "0/uniqueInitialColors");
		 // Helpers.writeListOfInitialColorsToTxt(uniqueInitialColors_AL, "Gen. Sequences:" + System.lineSeparator(), ",", ";", true, "Yields:" + System.lineSeparator(), ",", ";", System.lineSeparator(), 8, System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + "0/uniqueInitialColors.txt");
		Helpers.writeObjectToFile(C, System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + "0/C");
		
		// System.out.println("Round " + 0 + ":");
		// System.out.println("Number of unique Color Classes: " + uniqueInitialColors.size());
		
		HashSet<Color> uniqueColors = new HashSet<>();
		// Indexed TreeSet containing all the different colors, to give each color an index (a number).
		
		TreeMap<Integer, Integer> colorIndexToNewIndex = new TreeMap<>();
		// TreeMap that maps the old color's index to the new color's index, used to check convergence.
		
		int[] D;
		// Array that maps the index of each color to its count (number of appearances).
		
		TreeMap<ArrayList<Integer>, Integer> N;
		// TreeMap that maps from a pair of element indices to an index of color.
		
		boolean converges = false;
		
		// Start of Rounds ≥ 1
		
		for (curRound = 1; curRound <= maxRounds; curRound++) {
			converges = true;

			System.out.println();
			System.out.println("Round: " + curRound);
			System.out.println("Generating colors");
			for (int i = 1; i < order; i++) {
				System.out.println("i: " + (i+1));
				for (int j = 1; j < order; j++) {
					if (i == j)
						continue;
					
					IndexedTreeMap2<Pair> t = new IndexedTreeMap2<Pair>();
					for (int k = 1; k < order; k++) {
						if (k != i && k != j) {
							ArrayList<Integer> A1 = new ArrayList<Integer> (Arrays.asList(k, j));
							ArrayList<Integer> A2 = new ArrayList<Integer> (Arrays.asList(i, k));
							t.increment(new Pair(M.get(A1), M.get(A2)));
						}
					}
					
					ArrayList<Integer> A = new ArrayList<>(Arrays.asList(i, j));
					
					Color c = new Color(M.get(A), t);
					
					uniqueColors.add(c);
				}
			}
			
			numberOfColorClasses = uniqueColors.size();
			ArrayList<Color> uniqueColors_AL = new ArrayList<>(uniqueColors);
			Collections.sort(uniqueColors_AL);
			HashMap<Color, Integer> HM = new HashMap<>();
			for(int i = 0; i < numberOfColorClasses; i++) HM.put(uniqueColors_AL.get(i), i);
			D = new int[numberOfColorClasses];
			N = new TreeMap<ArrayList<Integer>, Integer> (arrayListComparator);
			
			System.out.println("Mapping tuples to color indices");
			for (int i = 1; i < order; i++) {
				System.out.println("i: " + (i+1));
				for (int j = 1; j < order; j++) {
					if (i == j)
						continue;
					
					IndexedTreeMap2<Pair> t = new IndexedTreeMap2<Pair>();
					for (int k = 1; k < order; k++) {
						if (k != i && k != j) {
							ArrayList<Integer> A1 = new ArrayList<Integer> (Arrays.asList(k, j));
							ArrayList<Integer> A2 = new ArrayList<Integer> (Arrays.asList(i, k));
							t.increment(new Pair(M.get(A1), M.get(A2)));
						}
					}
					
					ArrayList<Integer> A = new ArrayList<>(Arrays.asList(i, j));
					
					Color c = new Color(M.get(A), t);
					
					Integer c_index = HM.get(c);
					
					if (converges) {
						Integer temp = colorIndexToNewIndex.put(M.get(A), c_index);
						if (temp != null && !temp.equals(c_index)) {
							converges = false;
						}
					}
					
					N.put(A, c_index);
					D[c_index]++;
				}
			}
			
			// System.out.println("Round " + curRound //+ ":");
			// System.out.println("Number of unique Color Classes: " + uniqueColors.size());
			
			Arrays.fill(C, 0);
			C = D;  // New colors will now be the old colors (for the next round)
			
			M.clear();
			M = N;  // New colors will now be the old colors (for the next round)
			
			Helpers.createDirectory(System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + curRound);
			lines = Arrays.asList(Integer.toString(numberOfColorClasses));
			file = Paths.get(System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + curRound + "/numberOfColorClasses.txt");
			Files.write(file, lines, StandardCharsets.UTF_8);
			Helpers.writeObjectToFile(uniqueColors_AL, System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + curRound + "/uniqueColors");
			// Helpers.writeListOfColorsToTxt(uniqueColors_AL, "Color:" + System.lineSeparator(), "Multiset:" + System.lineSeparator(), ",", "=", ";", true, System.lineSeparator(), 3, System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + curRound + "/uniqueColors.txt");

			Helpers.writeObjectToFile(C, System.getProperty("user.dir") + "/Invariants/" + globalOrder + "/" + index + "/" + '\u0023' + curRound + "/C");
			
			// Cleanup at the end of a round
			colorIndexToNewIndex.clear();
			uniqueColors.clear();
			
			if (converges) {
				roundsTaken = curRound;
				break;
			}
		}
		
		// End of Rounds ≥ 1
		
		if (!converges)
			curRound--;
		
		numberOfColorClasses1 = numberOfColorClasses;
		roundsTaken = curRound;
		
		return true;
	}
	
	/**
	 * Generates the whole group from the given array of generators.
	 * 
	 * @param generators  a Permutation array that is the list of generators
	 *                    to use to generate the group.
	 * @param degree      an integer used to denote the degree of the
	 *                    group generated by the given generators.
	 * 
	 * @return Permutation[]  a Permutation array that contains every element of the group
	 *                    represented as a Permutation object
	 */
	public static Permutation[] generate(Permutation[] generators, int degree) {
		TreeSet<Permutation> orbitSet = new TreeSet<Permutation>(); 
		ArrayList<Permutation> generatedGroup = new ArrayList<Permutation>();
		
		Permutation identity = Permutation.identityPermutation(degree);
		orbitSet.add(identity);
		generatedGroup.add(identity);
		
		for (Permutation s : generators) {
			orbitSet.add(s);
			generatedGroup.add(s);
		}
		
		Permutation t;
		for (int i = 1; i < generatedGroup.size(); i++) {
			for (Permutation s : generators) {
				t = generatedGroup.get(i).multiply(s);
				
				if (!orbitSet.contains(t)) {
					orbitSet.add(t);
					generatedGroup.add(t);
				}
			}
		}
		
		return generatedGroup.toArray(new Permutation[generatedGroup.size()]);
	}
	
	/**
	 * In the following method, the generators passed in the 1st argument are referred to as
	 * 1-based indices.
	 * 
	 * This method returns a Pair object which contains an ArrayList<Integer[]> object "genBy"
	 * in its 1st component. The "genBy" object stores for each element in the group a minimum
	 * sequence (Integer array) of generators that generate this element where the generators
	 * are prioritized in the order given (1st generator has highest priority), hence the
	 * generated elements can be considered to be radix ordered in some sense. (a.k.a. shortlex
	 * or length-lexicographic order).
	 * Note that in this order the identity has index 1 followed directly by the generators in
	 * the order they're given.
	 * 
	 * The Pair additionally contains a 2d array "yield", that has (length of generators) many
	 * rows, and in each row there is (order of group - 1) integers, which are the radix indices
	 * of the elements that result from the right multiplication of each element in the group (except
	 * the identity) by the ith generator (element_[j+1] * generator_i in each entry j of row i).
	 * 
	 * generatedByAndYields below is the actual method used by this kWL implementation,
	 * the generators are passed as indices along with a reference to the whole group.
	 */
	public static InitialColor generatedByAndYields(Permutation[] generators, int degree) {
		TreeMap<Permutation, Integer> generatedSubgroupMap = new TreeMap<Permutation, Integer>();
		ArrayList<Permutation> generatedSubgroup = new ArrayList<Permutation>();
		List<int[]> genBy = new ArrayList<int[]>();
		
		Permutation identity = Permutation.identityPermutation(degree);
		generatedSubgroupMap.put(identity, 1);
		generatedSubgroup.add(identity);
		genBy.add(new int[0]);  // Integer array of length 0 denoting the generation of the empty element
		
		for (int i = 0; i < generators.length; i++) {
			generatedSubgroup.add(generators[i]);
			generatedSubgroupMap.put(generators[i], generatedSubgroup.size());
			genBy.add(new int[] {i+1});  // Integer array containing only the 1-based index (i+1)
		}
		
		Permutation t;
		for (int i = 1; i < generatedSubgroup.size(); i++) {
			for (int j = 0; j < generators.length; j++) {
				t = generatedSubgroup.get(i).multiply(generators[j]);
				if (!generatedSubgroupMap.containsKey(t)) {
					generatedSubgroup.add(t);
					generatedSubgroupMap.put(t, generatedSubgroup.size());
					genBy.add(Helpers.copyAndAdd(genBy.get(i), j+1));  // New element is generated by the sequence genBy[i] ○ (j+1)
				}
			}
		}
		
		int[][] yield = new int[generators.length][generatedSubgroup.size()];
		for (int i = 0; i < generators.length; i++) {
			for (int j = 0; j < generatedSubgroup.size(); j++) {
				yield[i][j] = generatedSubgroupMap.get(generatedSubgroup.get(j).multiply(generators[i]));
			}
		}
		
		int[][] genBy2 = new int[generatedSubgroup.size()][];
		for (int i = 0; i < genBy.size(); i++) genBy2[i] = genBy.get(i);
		return new InitialColor(genBy2, yield);
	}
	
	/**
	 * Same as the above method, except it uses the ArrayList indices to access the generators from the
	 * whole group that is given in the 1st argument.
	 */
	public static InitialColor generatedByAndYields(Permutation[] group, List<Integer> indices, int degree) {
		TreeMap<Permutation, Integer> generatedSubgroupMap = new TreeMap<Permutation, Integer>();
		List<Permutation> generatedSubgroup = new ArrayList<Permutation>();
		List<int[]> genBy = new ArrayList<int[]>();
//		
		
		Permutation identity = Permutation.identityPermutation(degree);
		generatedSubgroupMap.put(identity, 1);
		generatedSubgroup.add(identity);
		genBy.add(new int[0]);  // Integer array of length 0 denoting the generation of the empty element
		
		for (int i = 0; i < indices.size(); i++) {
			generatedSubgroup.add(group[indices.get(i)]);
			generatedSubgroupMap.put(group[indices.get(i)], generatedSubgroup.size());
			genBy.add(new int[] {i+1});  // Integer array containing only the 1-based index (i+1)
		}
		
		Permutation t;
		for (int i = 1; i < generatedSubgroup.size(); i++) {
			for (int j = 0; j < indices.size(); j++) {
				t = generatedSubgroup.get(i).multiply(group[indices.get(j)]);
				if (!generatedSubgroupMap.containsKey(t)) {
					generatedSubgroup.add(t);
					generatedSubgroupMap.put(t, generatedSubgroup.size());
					genBy.add(Helpers.copyAndAdd(genBy.get(i), j+1));  // New element is generated by the sequence genBy[i] ○ (j+1)
				}
			}
		}
		
		int[][] yield = new int[indices.size()][generatedSubgroup.size()];
		for (int i = 0; i < indices.size(); i++) {
			for (int j = 0; j < generatedSubgroup.size(); j++) {
				yield[i][j] = generatedSubgroupMap.get(generatedSubgroup.get(j).multiply(group[indices.get(i)]));
			}
		}
		
		int[][] genBy2 = new int[generatedSubgroup.size()][];
		for (int i = 0; i < genBy.size(); i++) genBy2[i] = genBy.get(i);
		return new InitialColor(genBy2, yield);
	}
	
	public static boolean compareColors(IndexedTreeMap2<Integer> C1, IndexedTreeMap2<Integer> C2, int p, boolean converges) {
		NavigableSet<Integer> keySet1 = C1.navigableKeySet();
		NavigableSet<Integer> keySet2 = C2.navigableKeySet();
		
		if (keySet1.size() != keySet2.size()) {
			if (converges) {
				System.out.print("Verdict after converging in round " + p + " of 2-dim Weisfeiler-Lehman: Non-isomorphic\n"
				   + "Color sets don't have the same size");
			}
			else {
				System.out.print("Verdict after round " + p + " of 2-dim Weisfeiler-Lehman: Non-isomorphic\n"
		    	+ "Color sets don't have the same size");
			}
		    System.out.println(" (" + keySet1.size() + " and " + keySet2.size() + ").");
		    roundsTaken = p;
			numberOfColorClasses1 = keySet1.size();
			numberOfColorClasses2 = keySet2.size();
		    return false;
		}
		
		if (!keySet1.equals(keySet2)) {
			if (converges) {
				System.out.println("Verdict after converging in round " + p + " of 2-dim Weisfeiler-Lehman: Non-isomorphic\n"
					+ "Color sets have the same size (" + keySet1.size() + "), but don't have the same colours.");
			}
			else {
				System.out.println("Verdict after round " + p + " of 2-dim Weisfeiler-Lehman: Non-isomorphic\n"
					+ "Color sets have the same size (" + keySet1.size() + "), but don't have the same colours.");
			}
			roundsTaken = p;
			numberOfColorClasses1 = keySet1.size();
			numberOfColorClasses2 = keySet2.size();
		    return false;
		}
		
		Iterator<Integer> it1 = keySet1.iterator();
		Iterator<Integer> it2 = keySet2.iterator();
		int cn = 0;
		while (it1.hasNext()) {
			Integer t1 = it1.next(), t2 = it2.next();
			
			if (!C1.get(t1).equals(C2.get(t2))) {
				if (converges) {
					System.out.println("Verdict after converging in round " + p + " of 2-dim Weisfeiler-Lehman: Non-isomorphic\n"
						+ "Color sets have the same colours, but Colors #" + cn + "'s counts are not equal.");
				}
				else {
					System.out.println("Verdict after round " + p + " of 2-dim Weisfeiler-Lehman: Non-isomorphic\n"
						+ "Color sets have the same colours, but Colors #" + cn + "'s counts are not equal.");
				}
				roundsTaken = p;
				numberOfColorClasses1 = keySet1.size();
				numberOfColorClasses2 = keySet2.size();
				return false;
			}
			
			cn++;
		}
		
		roundsTaken = p;
		numberOfColorClasses1 = keySet1.size();
		numberOfColorClasses2 = keySet2.size();
		return true;
	}
	
	public static void parseKWL(String s, int degree1, int degree2, int index1, int index2, FileWriter csvWriter) throws InvalidPermutationException, IOException {
		int i = 3;
		boolean error = false;
		while (true) {
			if (s.charAt(i) =='(') {
				break;
			}
			else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
				error = true;
				break;
			}
			
			i++;
			if (i == s.length()) {
				error = true;
				break;
			}
		}
		if (error) {
			System.out.println("Error 1: String format is incorrect");
			return;
		}
		i++;
		
		while (true) {
			if (s.charAt(i) =='[') {
				break;
			}
			else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
				error = true;
				break;
			}
			
			i++;
			if (i == s.length()) {
				error = true;
				break;
			}
		}
		if (error) {
			System.out.println("Error 2: String format is incorrect");
			return;
		}
		i++;
		
		int h = i;
		ArrayList<Permutation> a = new ArrayList<Permutation>();
		ArrayList<Permutation> b = new ArrayList<Permutation>();
		int processingInteger = 0;
		boolean processingPerm = false;
		boolean foundOpeningBracket = false;
		boolean doneWithFirstGroup = false;
		
		while (true) {
			if (!foundOpeningBracket) {
				if (s.charAt(i) == '(') {
					foundOpeningBracket = true;
					if (!processingPerm) {  // h holds the index of the start of a permutation
						h = i;
						processingPerm = true;
					}
				}
				else if (s.charAt(i) == ',') {
				// System.out.println(h + " - " + i);
				// For testing
					if (!doneWithFirstGroup) {
						a.add(Representation.parsePermutation(s, h, i, degree1));
					}
					else {
						b.add(Representation.parsePermutation(s, h, i, degree2));
					}
					
					processingPerm = false;
				}
				else if (s.charAt(i) == ']') {
				// System.out.println(h //+ " - " + i);
				// For testing
					if (!doneWithFirstGroup) {
						a.add(Representation.parsePermutation(s, h, i, degree1));
						
						doneWithFirstGroup = true;
						Pattern pattern = Pattern.compile("[ \t\n\r\0]*,[ \t\n\r\0]*\\[");
						Matcher m = pattern.matcher(s);
						if (m.find(i+1)) {
							i = m.end() - 1;
						}
						else {
							error = true;
							break;
						}
					}
					else {
						b.add(Representation.parsePermutation(s, h, i, degree2));
						
						Pattern pattern = Pattern.compile("[ \t\n\r\0]*\\)[ \t\n\r\0]*;?[ \t\n\r\0]*");
						Matcher m = pattern.matcher(s);
						if (m.find(i+1)) {
							i = m.end();
							if (i != s.length())
								error = true;
							break;
						}
						else {
							error = true;
							break;
						}
					}
					
					processingPerm = false;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 0) {  // Didn't start processing integer
				if (Character.isDigit(s.charAt(i))) {
					processingInteger = 1;
				}
				else if (s.charAt(i) == ')') {
					foundOpeningBracket = false;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 1) {  // Started processing integer
				if (Character.isDigit(s.charAt(i))) {
					
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\f') {
					processingInteger = 2;
				}
				else {
					error = true;
					break;
				}
			}
			else if (processingInteger == 2) {  // Whitespaces after integer
				if (Character.isDigit(s.charAt(i))) {
					error = true;
					break;
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\f') {
					
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
				}
				else {
					error = true;
					break;
				}
			}
			
			i++;
			if (i == s.length()) {
				error = true;
				break;
			}
		}
		if (error) {
			System.out.println("Error 3: String format is incorrect");
			return;
		}
		
		long startTime = System.nanoTime();
		boolean areIsomorphic = kWL(a.toArray(new Permutation[a.size()]), b.toArray(new Permutation[b.size()]), degree1, degree2);
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		
		csvWriter.append("" + index1);
		csvWriter.append(",");
		csvWriter.append("" + index2);
		csvWriter.append(",");
		csvWriter.append("" + areIsomorphic);
		csvWriter.append(",");
		csvWriter.append("" + roundsTaken);
		csvWriter.append(",");
		csvWriter.append("" + duration / 1000000.0);
		csvWriter.append(",");
		csvWriter.append("" + numberOfColorClasses1);
		csvWriter.append(",");
		csvWriter.append("" + numberOfColorClasses2);
		csvWriter.append("\n");
		csvWriter.flush();
	}
	
	public static void parseKWLinv(String s, int degree, int index, FileWriter csvWriter) throws IOException, InvalidPermutationException {
		int i = 6;
		boolean error = false;
		while (true) {
			if (s.charAt(i) =='(') {
				break;
			}
			else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
				error = true;
				break;
			}
			
			i++;
			if (i == s.length()) {
				error = true;
				break;
			}
		}
		if (error) {
			System.out.println("Error 1: String format is incorrect");
			return;
		}
		i++;
		
		while (true) {
			if (s.charAt(i) =='[') {
				break;
			}
			else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
				error = true;
				break;
			}
			
			i++;
			if (i == s.length()) {
				error = true;
				break;
			}
		}
		if (error) {
			System.out.println("Error 2: String format is incorrect");
			return;
		}
		i++;
		
		int h = i;
		ArrayList<Permutation> a = new ArrayList<Permutation>();
		int processingInteger = 0;
		boolean processingPerm = false;
		boolean foundOpeningBracket = false;
		
		while (true) {
			if (!foundOpeningBracket) {
				if (s.charAt(i) == '(') {
					foundOpeningBracket = true;
					if (!processingPerm) {  // h holds the index of the start of a permutation
						h = i;
						processingPerm = true;
					}
				}
				else if (s.charAt(i) == ',') {
				// System.out.println(h //+ " - " + i);
				// For testing
					a.add(Representation.parsePermutation(s, h, i, degree));
					
					processingPerm = false;
				}
				else if (s.charAt(i) == ']') {
				// System.out.println(h //+ " - " + i);
				// For testing
					a.add(Representation.parsePermutation(s, h, i, degree));
					
					Pattern pattern = Pattern.compile("[ \t\n\r\0]*\\)[ \t\n\r\0]*;?[ \t\n\r\0]*");
					Matcher m = pattern.matcher(s);
					if (m.find(i+1)) {
						i = m.end();
						if (i != s.length())
							error = true;
						break;
					}
					else {
						error = true;
						break;
					}
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 0) {  // Didn't start processing integer
				if (Character.isDigit(s.charAt(i))) {
					processingInteger = 1;
				}
				else if (s.charAt(i) == ')') {
					foundOpeningBracket = false;
				}
				else if (s.charAt(i) != ' ' && s.charAt(i) != '\t' && s.charAt(i) != '\n' && s.charAt(i) != '\r' && s.charAt(i) != '\f') {
					error = true;
					break;
				}
			}
			else if (processingInteger == 1) {  // Started processing integer
				if (Character.isDigit(s.charAt(i))) {
					
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\f') {
					processingInteger = 2;
				}
				else {
					error = true;
					break;
				}
			}
			else if (processingInteger == 2) {  // Whitespaces after integer
				if (Character.isDigit(s.charAt(i))) {
					error = true;
					break;
				}
				else if (s.charAt(i) == ',') {
					processingInteger = 0;
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\f') {
					
				}
				else if (s.charAt(i) == ')') {
					processingInteger = 0;
					foundOpeningBracket = false;
				}
				else {
					error = true;
					break;
				}
			}
			
			i++;
			if (i == s.length()) {
				error = true;
				break;
			}
		}
		if (error) {
			System.out.println("Error 3: String format is incorrect");
			return;
		}
		
		long startTime = System.nanoTime();
		kWLinv(a.toArray(new Permutation[a.size()]), degree, index);
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		
		csvWriter.append("" + globalOrder);
		csvWriter.append(",");
		csvWriter.append("" + index);
		csvWriter.append(",");
		csvWriter.append("" + numberOfColorClasses1);
		csvWriter.append(",");
		csvWriter.append("" + roundsTaken);
		csvWriter.append(",");
		csvWriter.append("" + duration / 1000000.0);
		csvWriter.append("\n");
		csvWriter.flush();
	}
	
	public static void parse(String s, int degree1, int degree2, int index1, int index2, FileWriter csvWriter) throws IOException, InvalidPermutationException {
		System.out.println("Task #" + globalCounter++);
		// Comment out the previous line in case you want to print-increment the globalCounter
		s = s.replaceAll("^[ \\t]+", "");
		if (s.length() <= 2) {
			System.out.println("Error 0: String format is incorrect\n");
			return;
		}
		
		if (s.substring(0, 3).equalsIgnoreCase("kWL")) {
			parseKWL(s, degree1, degree2, index1, index2, csvWriter);
		}
	}
	
	public static void parse(String s, int degree, int index, FileWriter csvWriter) throws IOException, InvalidPermutationException {
		System.out.println("Task #" + globalCounter++);
		// Comment out the previous line in case you want to print-increment the globalCounter
		s = s.replaceAll("^[ \\t]+", "");
		if (s.length() <= 2) {
			System.out.println("Error 0: String format is incorrect\n");
			return;
		}
		
		if (s.length() >= 6) {
			if (s.substring(0, 6).equalsIgnoreCase("kWLinv")) {
				parseKWLinv(s, degree, index, csvWriter);
			} else if (s.substring(0, 3).equalsIgnoreCase("kWL")) {
				int degree1 = degree, degree2 = index;
				parseKWL(s, degree1, degree2, -1, -1, csvWriter);  // Unknown Group IDs (Their number in the GAP Small Groups Library)
			}
			else {
				System.out.println("Error 0: String format is incorrect\n");
			}
		} else if (s.substring(0, 3).equalsIgnoreCase("kWL")) {
			int degree1 = degree, degree2 = index;
			parseKWL(s, degree1, degree2, -1, -1, csvWriter);  // Unknown Group IDs (Their number in the GAP Small Groups Library)
		} else {
			System.out.println("Error 0: String format is incorrect\n");
		}
	}
	
	public static void compare2Groups(int group_i, int group_j, int degree1, int degree2, boolean printString, FileWriter csvWriter) throws InterruptedException, IOException, InvalidPermutationException {
		StringBuilder sb = new StringBuilder();
		sb.append("kWL(");
		String fileReadByGAP = "StreamJavaToGAP.txt";
		Helpers.appendUsingFileOutputStream(fileReadByGAP, group_i + "\n");
		FileInputStream groupInputStream = null;
		TimeUnit.SECONDS.sleep(1);
		groupInputStream = new FileInputStream("StreamGAPToJava.txt");

		int numberOfBytesRead = 0; byte[] buffer;
		do {
			buffer = new byte[1024];
			numberOfBytesRead = groupInputStream.read(buffer);
			sb.append(new String(buffer, "UTF-8"));
		} while (numberOfBytesRead != -1);
		
		groupInputStream.close();
		sb.append(", ");
			
		Helpers.appendUsingFileOutputStream(fileReadByGAP, group_j + "\n");
		TimeUnit.SECONDS.sleep(1);
		groupInputStream = new FileInputStream("StreamGAPToJava.txt");
		
		do {
			buffer = new byte[1024];
			numberOfBytesRead = groupInputStream.read(buffer);
			sb.append(new String(buffer, "UTF-8"));
		} while (numberOfBytesRead != -1);
		
		groupInputStream.close();
		sb.append(");");
		
		if (printString)
			System.out.println(sb.toString());
		parse(sb.toString(), degree1, degree2, group_i, group_j, csvWriter);
		csvWriter.flush();
	}
	
	public static void genearateInvariants_OfGroups_OfOrdersInRange(int startOrder, int endOrder, int maxRounds_arg, String CSV_fileName, boolean printString) throws IOException, InterruptedException, InvalidPermutationException {
		globalCounter = 1;
		maxRounds = maxRounds_arg;
		FileWriter csvWriter = null;
		csvWriter = new FileWriter(CSV_fileName);
		
		String fileReadByGAP = "StreamJavaToGAP.txt";
		FileInputStream groupInputStream = null;
		StringBuilder sb;
		boolean flag;
		int curGroup;
		for (int curOrder = startOrder; curOrder <= endOrder;) {
			globalOrder = curOrder;
			curGroup = 1;
			sb = new StringBuilder();
			
			while (true) {
				flag = false;
				sb = new StringBuilder();
				sb.append("kWLinv(");
				Helpers.appendUsingFileOutputStream(fileReadByGAP, curGroup + "\n");
				TimeUnit.SECONDS.sleep(1);
				groupInputStream = new FileInputStream("StreamGAPToJava.txt");
				
				int numberOfBytesRead = 0; byte[] buffer;
				do {
					buffer = new byte[1024];
					numberOfBytesRead = groupInputStream.read(buffer);
					sb.append(new String(buffer, "UTF-8"));
					
					if (sb.charAt(7) != '[') {
						// int numberOfGroupsHavingOrder = Integer.parseInt(sb.substring(7).toString().trim());  // Number of Groups having that order returned by the execution of the GAP file.
						System.out.println("Order " + curOrder + " done. (" + (curGroup-1) + " Groups)");
						curOrder++;
						flag = true;
						break;
					}
				} while (numberOfBytesRead != -1);
				groupInputStream.close();
				
				if (!flag) {
					sb.append(");");
					if (printString)
						System.out.println(sb.toString());
					
					parse(sb.toString(), curOrder, curGroup, csvWriter);
					curGroup++;
				}
				else {  // if (flag)
					break;
				}
			}
		}
		
		csvWriter.flush();
		csvWriter.close();
	}
	
	/**
	 * The following code runs the kWL method on the pairs of groups specified below, (All are inputed as
	 * permutation groups), each time, it outputs whether the method detects a difference or not, as well
	 * as the number of color classes in each round.
	 * @throws IOException 
	 * 
	 * @throws InvalidPermutationException 
	 */
	public static void kWLtest1(int maxRounds_arg) throws IOException, InvalidPermutationException {
		globalCounter = 1;
		maxRounds = maxRounds_arg;
		FileWriter csvWriter = null;
		csvWriter = new FileWriter("kWLOutput.csv");
		
		parse("kWL([ (1,2,3,4), (2,4) ], [ (1,5,3,7)(2,8,4,6), (1,2,3,4)(5,6,7,8) ]);", 4, 8, csvWriter);
		System.out.println();
		// Dihedral Group of Order 8, Quaternion Group of Order 8
		
		
		parse("kWL([ (1,2,3,4), (2,4) ],[(5,3,8,6),(3,6)]);", 4, 8, csvWriter);
		System.out.println();
		// Dihedral Group of Order 8, Dihedral Group of Order 8 (renamed the elements while still being max ≤ 8)
		
		parse("kWL([ (1,2,3,4), (2,4) ],[(3  ,  6), ( 5	,		3,8,6)])", 4, 8, csvWriter);
		System.out.println();
		// Dihedral Group of Order 8, DihedralGroup of Order 8 (swapping the generators)
		
		parse("kWL([ (6,22,14,30)(10,34,18,26), (6,10,14,18)(22,26,30,34) ], [ (1,5,3,7)(2,8,4,6), (1,2,3,4)(5,6,7,8) ]) 	", 34, 8, csvWriter);
		System.out.println();
		// Quaternion Group of Order 8, Quaternion Group of Order 8
		
		parse("  kWL([ (1,2,3,4), (2,4) ],[ (1,2,3,4,5,6,7,8) ]);", 4, 8, csvWriter);
		System.out.println();
		// Dihedral Group of Order 8, Cyclic Group of Order 8
		
		parse("	kWL([ (1,2,3,4), (2,4) ],[ (1,2,3,4), (5,6) ]);", 4, 6, csvWriter);
		System.out.println();
		// Dihedral Group of Order 8, Z_4 x Z_2
		
		parse("kWL([ (1,2,3,4), (2,4) ],[ (1,2), (3,4), (5,6) ]);", 4, 6, csvWriter);
		System.out.println();
		// Dihedral Group of Order 8, Elementary Abelian Group of order 8
	}
	
	/**
	 * The following code runs the kWL method similarly to the above code. It runs the kWL method on the
	 * pairs of group IDs specified below. The IDs correspond to the IDs of groups of order 128 in the
	 * GAP Small Groups Library. Each time, it outputs whether the method detects a difference or not, as well
	 * as the number of color classes in each round.
	 * 
	 * @throws IOException 
	 * @throws InvalidPermutationException 
	 * @throws InterruptedException 
	 */
	public static void kWLtest2(int maxRounds_arg, String CSVfileName, boolean printString) throws IOException, InterruptedException, InvalidPermutationException {
		maxRounds = maxRounds_arg;
		FileWriter csvWriter = null;
		csvWriter = new FileWriter(CSVfileName);
		
		ArrayList<Pair> array = new ArrayList<Pair>(Arrays.asList(
				new Pair(175, 1138),  new Pair(164, 999),  new Pair(166, 1014), new Pair(165, 1011),
				new Pair(174, 177),   new Pair(173, 1126), new Pair(167, 1013), new Pair(171, 1122),
				new Pair(1597, 1598), new Pair(831, 832),  new Pair(555, 556),  new Pair(818, 819),
				new Pair(807, 808)
		));
		
		for (Pair p : array) {
			System.out.println("Groups " + p.id1 + " and " + p.id2);
			compare2Groups(p.id1, p.id2, 128, 128, printString, csvWriter);
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, InvalidPermutationException {
		@SuppressWarnings("unused")
		int maxRounds_arg = 1000000000;  // Large number used to signify just running till convergence.
		
		/*
		 * kWL Set of tests #1
		 */
		// kWLtest1(maxRounds_arg);
		
		/*
		 *	Do note that the below test needs the GAP file "OutputGroupsStartingFromOrder128.g"
		 *  to be running in GAP in the same working directory that this Java class is running
		 *  in, so make sure to do so if you're going to uncomment the line to test it.
		 *  For the below test, try setting the last argument "printString" to "true" to see
		 *  how the command String is sent to the parse() method above which starts the kWL
		 *  method, utilizing exactly the 2 lists of generators provided by GAP.
		 *  Do note that this call will overwrite the file "kWLOutput.csv" in the working directory.
		 */
//		 kWLtest2(maxRounds_arg, "kWLOutput.csv", false);
		
		
		/*
		 *	The below test needs the the GAP file "OutputGroupsStartingFromOrder3.g" to be running.
		 *	Generates the whole information generated by the k-dimensional method and stores its data
		 *	as defined in the serializable classes: GroupInvariant and other related .
		 *	It will generate the information of all groups of orders 33 through 127.
		 *  The data will be generated under the directory "Invariants" under the working directory.
		 *  Do note that this call will overwrite the file "kWLOutput.csv" in the working directory.
		 */
//		 genearateInvariants_OfGroups_OfOrdersInRange(3, 32, maxRounds_arg, "kWLOutput.csv", false);
		
		
		/*	
		 *	Uncomment the next lines for further experimentation of the same (just of higher order).
		 *  The below test needs the the GAP file "OutputGroupsStartingFromOrder33.g" and
		 *  "OutputGroupsStartingFromOrder128.g" to be running as explained in the Method call above.
		 */
		// genearateInvariants_OfGroups_OfOrdersInRange(33, 127, maxRounds_arg, "kWLOutput.csv", false);
		// genearateInvariants_OfGroups_OfOrdersInRange(128, 128, maxRounds_arg, "kWLOutput.csv", false);
		
		/*
		 * Testing 2-dim WL for a group of order 512, a relatively high order and the memory
		 * consumption was indeed reduced in kWLinv() (with the downside that all colors have to be
		 * generated twice rather than once).
		 */
		genearateInvariants_OfGroups_OfOrdersInRange(512, 512, maxRounds_arg, "kWLOutput.csv", false);
	}
}
