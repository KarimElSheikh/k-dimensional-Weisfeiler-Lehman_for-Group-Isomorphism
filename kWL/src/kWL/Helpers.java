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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import datastructure.IndexedTreeMap2;
import group.data.Color;
import group.data.InitialColor;
import utilities.Pair;

/**
 * Helpers is a class of static helper methods of various utility.
 * 
 * Currently, I am considering moving some of the methods from here to the Representation class.
 * 
 * @author Karim Elsheikh
 */
public class Helpers {
	
	static Random random = new Random();
	
	/**
	 * Copies an integer array to a new array of size = the original array's size + 1.
	 * The new integer array additionally has a new a integer added at the last
	 * index and the integer element is given as an argument.
	 * The method is used in kWLClass when extending generation sequences.
	 * 
	 * @param	elementToAdd	an int which is the element added at the last index of the new array.
	 * 
	 * @return	int[]		an integer array.
	 */
	public static int[] copyAndAdd(int[] array, int elementToAdd) {
		int[] arrayOut = new int[array.length+1];
		
		for (int i = 0; i < array.length; i++) {
			arrayOut[i] = array[i];
		}
		
		arrayOut[array.length] = elementToAdd;
		return arrayOut;
	}
	

	/**
	 * creates a directory in the given String path.
	 * 
	 * @param   directoryPath  a String which is the path of the directory to create.
	 * 
	 * @throws  IOException    an IOException is thrown in case the method fails to create a
	 *                         directory at the specified path.
	 */
	public static void createDirectory(String directoryPath) throws IOException {
		
		Path path = Paths.get(directoryPath);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}
	
	/**
	 * Reads a serialized Java Object that was written to the hard drive and returns it.
	 * 
	 * @param	filePath	a string that is the path to the file that is to be read.
	 * 
	 * @return	Object		a java class instance that is simply casted as an "Object".
	 */
	public static Object ReadObjectFromFile(String filePath) {
		
		try {
			ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filePath));
			Object obj = objectIn.readObject();
			// System.out.println("The Object has been read from the file");
			
			objectIn.close();
			return obj;
			
		} catch (IOException | ClassNotFoundException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public static void writeObjectToFile(Object serObj, String filePath) {
		
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath));
			objectOut.writeObject(serObj);
			// System.out.println("The Object was successfully written to a file");
			
			objectOut.close();
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void appendUsingFileOutputStream(String fileName, String data) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(fileName), true);
			// The above true boolean in 2nd arg tells the FileOutputStream instance to append instead of write to the file
			os.write(data.getBytes(), 0, data.length());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] countLinesAndChars(String str){
		// String[] lines = str.split("\r\n|\r|\n");
		String systemLineSeparator = System.lineSeparator();
		String[] lines = str.split(systemLineSeparator);
		int linesLength = lines.length;
		
		if(str.endsWith(systemLineSeparator)) {
			if(str.startsWith(systemLineSeparator))
				return new int[] {1, 0};
			return new int[] {linesLength, 0};
		}
		
		return new int[] {linesLength-1, lines[linesLength-1].length()};
	}
	
	public static int[] advanceIandJ(int i, int j, String str){
		// String[] lines = str.split("\r\n|\r|\n");
		String systemLineSeparator = System.lineSeparator();
		String[] lines = str.split(systemLineSeparator, -1);
		int linesLength = lines.length;
		
		if (lines.length == 1)
			return new int[] {i, j + lines[linesLength-1].length()};
		return new int[] {i + linesLength-1, lines[linesLength-1].length()};
	}
	
	public static boolean atIandJequalsString(int i, int j, List<String> lines, String s) {
		String systemLineSeparator = System.lineSeparator();
		int linesSize = lines.size();
		
		String[] sLines = s.split(systemLineSeparator, -1);
		if(sLines[sLines.length-1].length() != 0) {
			for (int p = 0; p < sLines.length - 1; p++) {
				if (i < linesSize && j + sLines[p].length() <= lines.get(i).length() && lines.get(i).substring(j).equals(sLines[p])) {
					i++; j = 0;
				} else {
					return false;
				}
			}
			
			int p = sLines.length - 1;
			if (i < linesSize && j + sLines[p].length() <= lines.get(i).length() && lines.get(i).substring(j, j + sLines[p].length()).equals(sLines[p])) {
			} else {
				return false;
			}
			
			return true;
		} else if (s.length() == 0) {
			return i < linesSize && j <= lines.get(i).length();
		} else if (s.equals(systemLineSeparator)) {
			return i < linesSize && j == lines.get(i).length();
		} else {
			for (int p = 0; p < sLines.length - 2; p++) {
				if (i < linesSize && j + sLines[p].length() <= lines.get(i).length() && lines.get(i).substring(j).equals(sLines[p])) {
					i++; j = 0;
				} else {
					return false;
				}
			}
			
			int p = sLines.length - 2;
			if (i < linesSize && j + sLines[p].length() == lines.get(i).length() && lines.get(i).substring(j, j + sLines[p].length()).equals(sLines[p])) {
			} else {
				return false;
			}
			
			return true;
		}
	}
	
	/**
	 * According to the answer by YAMM at StackOverflow https://stackoverflow.com/questions/19486077/java-fastest-way-to-read-through-text-file-with-2-million-lines,
	 * this is the fastest method of reading a text file among several methods tested, all of which don't use a library.
	 * @param path
	 * @throws IOException
	 */
	public static List<InitialColor> readListOfInitialColorsFromTxt(String path, String startOfColor, String sep1, String sep2, boolean sep2AtEndOfLine, String startOfYields, String sep3, String sep4, String sepBetweenColors, int genSequencesPerLine) throws IOException {
		List<String> lines = new ArrayList<>();
		
		try {
			final BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		List<InitialColor> list = new ArrayList<>();
		ArrayList<int[]> genSequences = new ArrayList<>();
		int[][] yield = new int[0][];
		
		int i = 0, j = 0, linesSize = lines.size();
		int genSequencesReadInCurLine = -1;
		int cn1 = 0, cn2 = 0;
		int maximumGenNumber = -1;
		while (i < linesSize) {
			
			if (genSequencesReadInCurLine == -1) {
				if (i != 0) { int[] intArr = advanceIandJ(i, j, sepBetweenColors); i = intArr[0]; j = intArr[1]; }
				int[] intArr = advanceIandJ(i, j, startOfColor); i = intArr[0]; j = intArr[1];
				maximumGenNumber = -1;
				genSequences.clear();
				genSequencesReadInCurLine = 0;
			}
			
			String line = lines.get(i);
			int lineLen = line.length();
			
			if(genSequencesReadInCurLine > -1 && !atIandJequalsString(i, j, lines, startOfYields)) {
				while (true) {  // Keep reading generator sequences.
					
					List<Integer> genSequence = new ArrayList<>();
					boolean first = true;
					while (j < lineLen && !line.substring(j, j+sep2.length()).equals(sep2)) {
						
						if (first) {
							first = false;
							
							int startOfInteger = j;
							while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
							
							if(startOfInteger != j) {
								int temp = Integer.parseInt(line.substring(startOfInteger, j));
								maximumGenNumber = Math.max(maximumGenNumber, temp);
								genSequence.add(temp);
							}
						} else {

							int[] intArr = advanceIandJ(i, j, sep1); i = intArr[0]; j = intArr[1];
							
							int startOfInteger = j;
							while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
							
							if(startOfInteger != j) {
								int temp = Integer.parseInt(line.substring(startOfInteger, j));
								maximumGenNumber = Math.max(maximumGenNumber, temp);
								genSequence.add(temp);
							}
						}
					}
					
					int[] arr = new int[genSequence.size()];
					for (int y = 0; y < genSequence.size(); y++) arr[y] = genSequence.get(y);
					genSequences.add(arr);
					genSequencesReadInCurLine++;
					
					if (genSequencesReadInCurLine != genSequencesPerLine && j + sep2.length() < lineLen)  {
						j += sep2.length();
					} else {
						if (sep2AtEndOfLine) {
							j += sep2.length();
						}
						if (j != lineLen) {
							System.out.println("Invalid format detected at line " + (i+1));
						}
						genSequencesReadInCurLine = 0;
						i++;
						j = 0;
						break;
					}
				}
			} else if (genSequencesReadInCurLine > -1) {
				genSequencesReadInCurLine = -2;
				int[] intArr = advanceIandJ(i, j, startOfYields); i = intArr[0]; j = intArr[1];
				cn1 = 0;
				cn2 = 0;
				yield = new int[maximumGenNumber][genSequences.size()];
			} else if (genSequencesReadInCurLine == -2) {
				
				boolean first = true;
				
				while (j < lineLen && !line.substring(j, j+sep4.length()).equals(sep4)) {
					if (first) {
						first = false;
						
						int startOfInteger = j;
						while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
						yield[cn1][cn2++] = Integer.parseInt(line.substring(startOfInteger, j));
					} else {
						int[] intArr = advanceIandJ(i, j, sep3); i = intArr[0]; j = intArr[1];
						
						int startOfInteger = j;
						while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
						yield[cn1][cn2++] = Integer.parseInt(line.substring(startOfInteger, j));
					}
				}
				
				cn1++; cn2 = 0;
				if(cn1 == maximumGenNumber) {
					int [][]genSequences2 = new int[genSequences.size()][];
					for (int y = 0; y < genSequences.size(); y++) {
						genSequences2[y] = new int[genSequences.get(y).length];
						for (int z = 0; z < genSequences.get(y).length; z++)
							genSequences2[y][z] = genSequences.get(y)[z];
					}
					list.add(new InitialColor(genSequences2, yield));
					genSequencesReadInCurLine = -1;
				}
				i++;
				j = 0;
			}
		}
		
		return list;
	}
	
	public static void printErrorMessage(int i, int j, int nOfLines, String line, String compare) {
		if (j + compare.length() <= line.length())
			System.out.println(String.join("", "Invalid format file, error detected at Ln ", Integer.toString(i+1), ", Col ",
					Integer.toString(j+1), ": Substring \"", line.substring(j, j + compare.length()),
					"\" doesn't match substring \"", compare, "\"."));
		else {
			if(i != nOfLines - 1)
				System.out.println(String.join("", "Invalid format file, error detected at Ln ", Integer.toString(i+1), ", Col ",
						Integer.toString(j+1), ": Substring \"", line.substring(j, line.length()),
						" ...\" doesn't match substring \"", compare, "\"."));
			else
				System.out.println(String.join("", "Invalid format file, error detected at Ln ", Integer.toString(i+1), ", Col ",
						Integer.toString(j+1), ": Substring \"", line.substring(j, line.length()),
						"\" doesn't match substring \"", compare, "\"."));
		}
	}
	
	public static List<Color> readListOfColorsFromTxt(String path, String startOfColor, String startOfMultiSet, String sep1, String sep2, String sep3, boolean sep3AtEndOfLine, String sepBetweenColors, int entriesPerLine) throws IOException {
		
		List<String> lines = new ArrayList<>();
		
		try {
			final BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		List<Color> list = new ArrayList<>();
		int color = -1;
		IndexedTreeMap2<Pair> multiset = null;
		
		int i = 0, j = 0, linesSize = lines.size();
		int entriesReadInCurLine = -1;
		String line = lines.get(i);
		while (i < linesSize) {
			
			if (entriesReadInCurLine == -1) {  // Stage #1: Reading the start of the color (the main index).
				
				if (i != 0) {
					if (!atIandJequalsString(i, j, lines, sepBetweenColors)) {
						printErrorMessage(i, j, linesSize, lines.get(i), sepBetweenColors);
						break;
					}
					int[] intArr = advanceIandJ(i, j, sepBetweenColors); i = intArr[0]; j = intArr[1];
					if (i < linesSize) line = lines.get(i);
				}
				
				if (!atIandJequalsString(i, j, lines, startOfColor)) {
//					System.out.println("Invalid format file, first error detected at line " + (i+1));
					printErrorMessage(i, j, linesSize, lines.get(i), startOfColor);
					break;
				}
				int[] intArr = advanceIandJ(i, j, startOfColor); i = intArr[0]; j = intArr[1];
				if (i < linesSize) line = lines.get(i);
				
				entriesReadInCurLine = -2;
				
				int startOfInteger = j;
				while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
				
				color = Integer.parseInt(line.substring(startOfInteger, j));
				multiset = new IndexedTreeMap2<>();
				
				if(j != line.length()) {
					System.out.println("Invalid format file, error detected at Ln " + (i+1) + ", Col " + (j+1) + ": Expected a line break.");
					break;
				}
				
				i++; j = 0;
				
				if (!atIandJequalsString(i, j, lines, startOfMultiSet)) {
					printErrorMessage(i, j, linesSize, lines.get(i), startOfMultiSet);
					break;
				}
				
				intArr = advanceIandJ(i, j, startOfMultiSet); i = intArr[0]; j = intArr[1];
				if (i < linesSize) line = lines.get(i);
				
			} else if (entriesReadInCurLine == -2) {  // Stage #2: Reading the multiset's entries.
				
				entriesReadInCurLine = 0;
				boolean error = false;
				
//				while (j < line.length() && !line.substring(j, j+sep3.length()).equals(sep3)) {
//				while (i < linesSize && !atIandJequalsString(i, j, lines, sepBetweenColors + startOfColor)) {
				while (true) {
					
					int startOfInteger = j;
					while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
					int firstInteger = Integer.parseInt(line.substring(startOfInteger, j));
					
					if (!atIandJequalsString(i, j, lines, sep1)) {
						printErrorMessage(i, j, linesSize, lines.get(i), sep1);
						error = true;
						break;
					}
					int[] intArr = advanceIandJ(i, j, sep1); i = intArr[0]; j = intArr[1];
					if (i < linesSize) line = lines.get(i);
					
					startOfInteger = j;
					while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
					int secondInteger = Integer.parseInt(line.substring(startOfInteger, j));
					
					if (!atIandJequalsString(i, j, lines, sep2)) {
						printErrorMessage(i, linesSize, j, lines.get(i), sep2);
						error = true;
						break;
					}
					intArr = advanceIandJ(i, j, sep2); i = intArr[0]; j = intArr[1];
					if (i < linesSize) line = lines.get(i);
					
					startOfInteger = j;
					while (j < line.length() && Character.isDigit(line.charAt(j))) j++;
					multiset.put(new Pair(firstInteger, secondInteger), Integer.parseInt(line.substring(startOfInteger, j)));
					
					entriesReadInCurLine++;
					if (entriesReadInCurLine != entriesPerLine)  {
						
						if (!atIandJequalsString(i, j, lines, sep3)) {
							printErrorMessage(i, j, linesSize, lines.get(i), sep3);
							error = true;
							break;
						}
						else {
							intArr = advanceIandJ(i, j, sep3); i = intArr[0]; j = intArr[1];
							if (i < linesSize) line = lines.get(i);
							
//							entriesReadInCurLine = 0;
							if (j >= line.length()) {
								i++; j = 0;
								if (i < linesSize) line = lines.get(i);
								break;
							}
						}
					} else {
						if (sep3AtEndOfLine) {
							
							if (!atIandJequalsString(i, j, lines, sep3)) {
								printErrorMessage(i, j, linesSize, lines.get(i), sep3);
								error = true;
								break;
							}
							intArr = advanceIandJ(i, j, sep3); i = intArr[0]; j = intArr[1];
							if (i < linesSize) line = lines.get(i);
							
							if(j != line.length()) {
								System.out.println("Invalid format file, error detected at Ln " + (i+1) + ", Col " + (j+1) + ": Expected a line break.");
								error = true;
								break;
							}
						}
						
						if(j != line.length()) {
							System.out.println("Invalid format file, error detected at Ln " + (i+1) + ", Col " + (j+1) + ": Expected a line break.");
							error = true;
							break;
						}
						
						i++; j = 0;
						if (i < linesSize) line = lines.get(i);
//						intArr = advanceIandJ(i, j, systemLineSeparator); i = intArr[0]; j = intArr[1];
//						if (i < linesSize) line = lines.get(i);
						
						entriesReadInCurLine = 0;
						if (atIandJequalsString(i, 0, lines, sepBetweenColors + startOfColor)) {
							break;
						}
					}
				}
				
				if (error) break;
				list.add(new Color(color, multiset));
				entriesReadInCurLine = -1;
				
//				int[] intArr = advanceIandJ(i, j, systemLineSeparator); i = intArr[0]; j = intArr[1];
//				if (i < linesSize) line = lines.get(i);  // This condition is only necessary here if file is assumed to be in correct format.
//				System.out.println("YSE");
//				System.out.println(i);
			}
		}
		
		return list;
	}
	
	public static void writeListOfInitialColorsToTxt(List<InitialColor> initialColors, String startOfColor, String sep1, String sep2, boolean sep2AtEndOfLine, String startOfYields, String sep3, String sep4, String sepBetweenColors, int genSequencesPerLine, String outPath) throws IOException {
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < initialColors.size(); i++) {
			
			InitialColor ic = initialColors.get(i);
			int genSequencesInCurLine = 0;
			StringBuilder sb = new StringBuilder();
			if (i != 0) sb.append(sepBetweenColors);
			sb.append(startOfColor);
			
			for (int j = 0; j < ic.generatedBy.length; j++) {
				
				int[] a = ic.generatedBy[j];
				boolean first = true;
				
				for (int gen : a) {
					
					if (first) {
						sb.append(gen);
						first = false;
					} else {
						sb.append(sep1);
						sb.append(gen);
					}
					
				}
				
				genSequencesInCurLine++;
				if (genSequencesInCurLine < genSequencesPerLine) {
					if (j != ic.generatedBy.length-1) {
						sb.append(sep2);
					} else {
						if (sep2AtEndOfLine)
							sb.append(sep2);
						sb.append(System.lineSeparator());
					}
				} else {
					if (sep2AtEndOfLine)
						sb.append(sep2);
					sb.append(System.lineSeparator());
					genSequencesInCurLine = 0;
				}
			}
			
			sb.append(startOfYields);
			
			for (int j = 0 ; j < ic.yield.length; j++) {
				
				int[] a = ic.yield[j];
				
				for (int p = 0; p < a.length; p++) {
					sb.append(a[p]);
					if (p != a.length-1) {
						sb.append(sep3);
					} else {
						sb.append(sep4);
					}
				}
				
				if (j != ic.yield.length-1)
					sb.append(System.lineSeparator());  // Last line separator is added implicitely (sb is added to the list "lines").
			}
			
			lines.add(sb.toString());
		}
		
		Files.write(Paths.get(outPath), lines, StandardCharsets.UTF_8);
	}
	
	public static void writeListOfColorsToTxt(List<Color> colors, String startOfColor, String startOfMultiSet, String sep1, String sep2, String sep3, boolean sep3AtEndOfLine, String sepBetweenColors, int entriesPerLine, String outPath) throws IOException {
		
		ArrayList<String> lines = new ArrayList<>();
		String systemLineSeparator = System.lineSeparator();
		
		for (int i = 0; i < colors.size(); i++) {
			
			Color c = colors.get(i);
			StringBuilder sb = new StringBuilder();
			if (i != 0) sb.append(sepBetweenColors);
			sb.append(startOfColor);
			sb.append(c.color+1);     // colors are stored 0-based, write them to text file as 1-based.
			sb.append(systemLineSeparator);
			
		    Iterator<Pair> it = c.multiSet.navigableKeySet().iterator();
		    Iterator<Integer> valuesIt = c.multiSet.values().iterator();
		    sb.append(startOfMultiSet);
		    int entriesInCurLine = 0;
		    
			while (it.hasNext()) {
				
				Pair pairOfInts = it.next();
				sb.append(pairOfInts.id1+1);
				sb.append(sep1);
				sb.append(pairOfInts.id2+1);
				sb.append(sep2);
				sb.append(valuesIt.next());
				entriesInCurLine++;
				
				if (entriesInCurLine < entriesPerLine) {
					sb.append(sep3);
				} else {
					if (sep3AtEndOfLine) {
						sb.append(sep3);
					}
					if(it.hasNext()) {
						sb.append(systemLineSeparator);
					}
					entriesInCurLine = 0;
				}
			}
			
			lines.add(sb.toString());
		}
		
		Files.write(Paths.get(outPath), lines, StandardCharsets.UTF_8);
	}
	
	public static void printListOfInitialColorsCannonically(List<InitialColor> ics) {
		for (int i = 0; i < ics.size(); i++) {
			
			if (i != 0) System.out.println();
			System.out.println("Gen. Sequences:");
			
			for (int j = 0; j < ics.get(i).generatedBy.length; j++) {
				
				boolean first = true;
				
				for (int k = 0; k < ics.get(i).generatedBy[j].length; k++) {
					
					if (first) {
						first = false;
						System.out.print(ics.get(i).generatedBy[j][k]);
					} else {
						System.out.print(',');
						System.out.print(ics.get(i).generatedBy[j][k]);
					}
				}
				System.out.print(';');
			}
			
			System.out.println();
			System.out.println("Yields:");
			
			for (int j = 0; j < ics.get(i).yield.length; j++) {
				
				boolean first = true;
				
				for (int k = 0; k < ics.get(i).yield[j].length; k++) {
					
					if (first) {
						first = false;
						System.out.print(ics.get(i).yield[j][k]);
					} else {
						System.out.print(',');
						System.out.print(ics.get(i).yield[j][k]);
					}
				}
				
				System.out.println(';');
			}
		}
	}
	
	public static void printListOfColorsCannonically(List<Color> cs) {
		for (int i = 0; i < cs.size(); i++) {
			
			if (i != 0) System.out.println();
			System.out.println("Color:");
			System.out.println(cs.get(i).color);
			
		    Iterator<Pair> it = cs.get(i).multiSet.navigableKeySet().iterator();
		    Iterator<Integer> valuesIt = cs.get(i).multiSet.values().iterator();
		    System.out.println("Multiset:");
		    
			while (it.hasNext()) {
				Pair pairOfInts = it.next();
				System.out.print(pairOfInts.id1 + "," + pairOfInts.id2 + '=' + valuesIt.next() + ';');
			}
			
			System.out.println();
		}
	}
	
	public static void main( String[] args ) throws IOException{
		// System.out.println("Gen. Sequences:".length());
		String filePath1 = "Invariants/8/1/#0/uniqueInitialColors.txt";
		String filePath2 = "Invariants/8/1/#1/uniqueColors.txt";
		String filePath3 = "Invariants/8/1/#1/uniqueColors_corrupted1.txt";
		String filePath4 = "Invariants/8/1/#1/uniqueColors_corrupted2.txt";
		String filePath5 = "Invariants/8/1/#1/uniqueColors_corrupted3.txt";
		String filePath6 = "Invariants/8/1/#1/uniqueColors_corrupted4.txt";
		String filePath7 = "Invariants/8/1/#1/uniqueColors_corrupted5.txt";
		String filePath8 = "Invariants/8/1/#1/uniqueColors_corrupted6.txt";
		String filePath9 = "Invariants/8/1/#1/uniqueColors_corrupted7.txt";
//		String x = "\r\nabc\r\ndfg";
//		String[] y = x.split(System.lineSeparator());
//		System.out.println(y.length);
//		System.out.println(y[0]);
//		System.out.println(y[1]);
//		System.out.println(y[2]);
//		int[] intArr = countLinesAndChars(filePath);
//		System.out.println(intArr[0]);
//		System.out.println(intArr[1]);
//		List<InitialColor> ics = readListOfInitialColorsFromTxt(filePath1, "Gen. Sequences:" + System.lineSeparator(), ",", ";", true, "Yields:" + System.lineSeparator(), ",", ";", System.lineSeparator(), 128);
//		printListOfInitialColorsCannonically(ics);
		
		List<Color> cs = readListOfColorsFromTxt(filePath2, "Color:" + System.lineSeparator(), "Multiset:" + System.lineSeparator(), ",", "=", ";", true, System.lineSeparator(), 3);
		printListOfColorsCannonically(cs);	
	}
}
