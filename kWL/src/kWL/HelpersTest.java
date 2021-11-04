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

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HelpersTest{

	@Test
	public void testCopyAndAdd(){
		int[] arr = new int[] {3, 12, 9, 8, 11};
		
		int newElementToAdd = 5;
		int[] arr2 = Helpers.copyAndAdd(arr, newElementToAdd);
		
		assert(arr2.length == arr.length + 1);
		for (int i = 0; i < arr.length; i++) {
			assert(arr[i] == arr2[i]);
		}
		assert(arr2[arr.length] == newElementToAdd);
	}
	
	@Test
	public void testAdvanceIandJ(){
		int i = 0, j = 5;
		String s = "\nHello Fredirick";
		s = s.replaceAll("\n", System.lineSeparator());
		int[] intArr = Helpers.advanceIandJ(i, j, s); i = intArr[0]; j = intArr[1];
		
		assert(i == 1);
		assert(j == 15);
		
		i = 0; j = 5;
		s = "\nHello Fredirick\n";
		s = s.replaceAll("\n", System.lineSeparator());
		intArr = Helpers.advanceIandJ(i, j, s); i = intArr[0]; j = intArr[1];
		
		assert(i == 2);
		assert(j == 0);
		
		i = 0; j = 5;
		s = "\n\n";
		s = s.replaceAll("\n", System.lineSeparator());
		intArr = Helpers.advanceIandJ(i, j, s); i = intArr[0]; j = intArr[1];
		
		assert(i == 2);
		assert(j == 0);
		
		i = 0; j = 5;
		s = "";
		s = s.replaceAll("\n", System.lineSeparator());
		intArr = Helpers.advanceIandJ(i, j, s); i = intArr[0]; j = intArr[1];
		
		assert(i == 0);
		assert(j == 5);
		
		i = 0; j = 5;
		s = "\n";
		s = s.replaceAll("\n", System.lineSeparator());
		intArr = Helpers.advanceIandJ(i, j, s); i = intArr[0]; j = intArr[1];
		
		assert(i == 1);
		assert(j == 0);
		
		i = 0; j = 5;
		s = "Hello Fredirick\n";
		s = s.replaceAll("\n", System.lineSeparator());
		intArr = Helpers.advanceIandJ(i, j, s); i = intArr[0]; j = intArr[1];
		
		assert(i == 1);
		assert(j == 0);
	}
	
	@Test
	public void testAtIandJequalsString(){
		
		
		List<String> a = new ArrayList<String>(Arrays.asList(
				"The headache wouldn't go away. She's taken "        ,
				"medicine but even that didn't help. The monstrous " ,
				"throbbing in her head continued. She had this "     ,
				"happen to her only once before in her life and she ",
				"realized that only one thing could be happening."
		));
		
		int i = 0, j = 0;
		String s = "The headache wouldn't go away. She's taken \nmedicine but even that didn't help.";
		s = s.replaceAll("\n", System.lineSeparator());
		
		i = 0; j = 4;
		s = "headache wouldn't go away. She's taken \nmedicine but even that didn't help. The monstrous \nthrobbing in";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(Helpers.atIandJequalsString(i, j, a, s));
		
		i = 3; j = 0;
		s = "happen to her only once before in her life and she \nrealized that only one thing could be happening.";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(Helpers.atIandJequalsString(i, j, a, s));
		
		i = 3; j = 0;
		s = "happen to her only once before in her life and she \nrealized that only one thing could be happening.\n";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(Helpers.atIandJequalsString(i, j, a, s));
		
		i = 3; j = 51;
		s = "";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(Helpers.atIandJequalsString(i, j, a, s));
		
		i = 3; j = 52;
		s = "";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(!Helpers.atIandJequalsString(i, j, a, s));
		
		i = 4; j = 48;
		s = "";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(Helpers.atIandJequalsString(i, j, a, s));
		
		i = 4; j = 49;
		s = "";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(!Helpers.atIandJequalsString(i, j, a, s));
		
		// Boundaries check (Make sure no exception is thrown from these).
		i = 3; j = 14;
		s = "only once before in her life and she \nrealized that only one thing could be happening.\n The trees, therefore, must be such old and primitive\n";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(!Helpers.atIandJequalsString(i, j, a, s));
		
		i = 3; j = 14;
		s = "only once before in her life and she \nrealized that only one thing could be happening.\n\n";
		s = s.replaceAll("\n", System.lineSeparator());
		assert(!Helpers.atIandJequalsString(i, j, a, s));
	}

	@Test
	public void testCreateDirectory_readWriteObjectFromToFile_absPath(){
		int[] array = new int[] {4, 7, 13, 1};
		
		boolean managedToCreateDirectoryInAbsolutePath = false;
		boolean managedToWriteObjectInAbsolutePath = false;
		boolean readArrayObjectIsEqual = false;
		boolean managedToDeleteObjectInAbsolutePath = false;
		boolean managedToDeleteDirectoryInAbsolutePath = false;
		
		for (Path root : FileSystems.getDefault().getRootDirectories()) {
			Path p = root.resolve("test_kWLcreateDirectory_3267025197204177");
			// String pAsString = p.toString().replaceAll("\\\\", "/");
			// System.out.println(pAsString);
			try {
				Helpers.createDirectory(p.toString());
				assert(Files.exists(p));
				managedToCreateDirectoryInAbsolutePath = true;
				Path p2 = p.resolve("Object_3267025197204177");
				try {
					Helpers.writeObjectToFile(array, p2.toString());
					managedToWriteObjectInAbsolutePath = true;
					int[] arrayRead = (int[]) Helpers.ReadObjectFromFile(p2.toString());
					boolean allEqual = true;
					for (int i = 0; i < array.length; i++) if (array[i] != arrayRead[i]) { allEqual = false; break; }
					if (allEqual) readArrayObjectIsEqual = true;
					Files.delete(p2);
					assert(!Files.exists(p2));
					managedToDeleteObjectInAbsolutePath = true;
				} catch (final IOException e) {
				}
				Files.delete(p);
				assert(!Files.exists(p));
				managedToDeleteDirectoryInAbsolutePath = true;
			} catch (final IOException e) {
			}
			break;
		}
		
		assert(managedToCreateDirectoryInAbsolutePath);
		assert(managedToWriteObjectInAbsolutePath);
		assert(readArrayObjectIsEqual);
		assert(managedToDeleteObjectInAbsolutePath);
		assert(managedToDeleteDirectoryInAbsolutePath);
	}
	
	@Test
	public void testCreateDirectory_readWriteObjectFromToFile_relPath(){
		int[] array = new int[] {4, 7, 13, 1};
		
		boolean managedToCreateDirectoryInRelativePath = false;
		boolean managedToWriteObjectInRelativePath = false;
		boolean readArrayObjectIsEqual = false;
		boolean managedToDeleteObjectInRelativePath = false;
		boolean managedToDeleteDirectoryInRelativePath = false;
		
		Path p = Paths.get("test_kWLcreateDirectory_3267025197204177");
		// String pAsString = p.toString().replaceAll("\\\\", "/");
		// System.out.println(pAsString);
		try {
			Helpers.createDirectory(p.toString());
			assert(Files.exists(p));
			managedToCreateDirectoryInRelativePath = true;
			Path p2 = p.resolve("Object_3267025197204177");
			try {
				Helpers.writeObjectToFile(array, p2.toString());
				managedToWriteObjectInRelativePath = true;
				int[] arrayRead = (int[]) Helpers.ReadObjectFromFile(p2.toString());
				boolean allEqual = true;
				for (int i = 0; i < array.length; i++) if (array[i] != arrayRead[i]) { allEqual = false; break; }
				if (allEqual) readArrayObjectIsEqual = true;
				Files.delete(p2);
				assert(!Files.exists(p2));
				managedToDeleteObjectInRelativePath = true;
			} catch (final IOException e) {
			}
			Files.delete(p);
			assert(!Files.exists(p));
			managedToDeleteDirectoryInRelativePath = true;
		} catch (final IOException e) {
		}
		
		assert(managedToCreateDirectoryInRelativePath);
		assert(managedToWriteObjectInRelativePath);
		assert(readArrayObjectIsEqual);
		assert(managedToDeleteObjectInRelativePath);
		assert(managedToDeleteDirectoryInRelativePath);
	}

	@Test
	public void testAppendUsingFileOutputStream(){
//		fail("Not yet implemented");
	}

	@Test
	public void testCountLinesAndChars(){
//		fail("Not yet implemented");
	}

	@Test
	public void testReadListOfInitialColorsFromTxt(){
//		fail("Not yet implemented");
	}

	@Test
	public void testReadListOfColorsFromTxt(){
//		fail("Not yet implemented");
	}

	@Test
	public void testWriteListOfInitialColorsToTxt(){
//		fail("Not yet implemented");
	}

	@Test
	public void testWriteListOfColorsToTxt(){
//		fail("Not yet implemented");
	}

	@Test
	public void testPrintListOfInitialColorsCannonically(){
//		fail("Not yet implemented");
	}

	@Test
	public void testMain(){
//		fail("Not yet implemented");
	}
}
