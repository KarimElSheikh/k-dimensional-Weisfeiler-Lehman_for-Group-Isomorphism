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

package group.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;

import datastructure.IndexedTreeMap2;
import datastructure.IndexedTreeSet;

public class GroupInvariant implements Comparable<GroupInvariant>, Serializable {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = 9649186623056496L;
	
	public int id;
	public int order;
	public int rounds;
	public IndexedTreeSet<InitialColor> initialColors;
	public ArrayList<Integer> numberOfColorClassesArray;
	public ArrayList<IndexedTreeSet<Color>> uniqueColorsArray;
	public ArrayList<IndexedTreeMap2<Integer>> colourCountsArray;
	
	public GroupInvariant(int id, int order, int rounds, IndexedTreeSet<InitialColor> initialColors,  ArrayList<Integer> numberOfColorClassesArray, ArrayList<IndexedTreeSet<Color>> uniqueColorsArray, ArrayList<IndexedTreeMap2<Integer>> colourCountsArray) {
		this.id = id;
		this.order = order;
		this.rounds = rounds;
		this.initialColors = initialColors;
		this.numberOfColorClassesArray = numberOfColorClassesArray;
		this.uniqueColorsArray = uniqueColorsArray;
		this.colourCountsArray = colourCountsArray;
	}
	
	public int compareTo(GroupInvariant anotherGroupInvariant) {
		int cmp = order - anotherGroupInvariant.order;
		if (cmp != 0) return cmp;
		
		cmp = rounds - anotherGroupInvariant.rounds;
		if (cmp != 0) return cmp;
		
		cmp = numberOfColorClassesArray.get(0).compareTo(anotherGroupInvariant.numberOfColorClassesArray.get(0));
		if (cmp != 0) return cmp;
		
		// should be the same as the above
		cmp = initialColors.size() - anotherGroupInvariant.initialColors.size();
		if (cmp != 0) return cmp;
		
		Iterator<InitialColor> it1 = initialColors.iterator();
		Iterator<InitialColor> it2 = anotherGroupInvariant.initialColors.iterator();
		while (it1.hasNext()) {
			InitialColor t1 = it1.next(), t2 = it2.next();
			cmp = t1.compareTo(t2);
			if (cmp != 0) return cmp;
		}
		
		// Also should be the same as numberOfColorClassesArray.get(0).size()
		cmp = colourCountsArray.get(0).size() - anotherGroupInvariant.colourCountsArray.get(0).size();
		if (cmp != 0) return cmp;
		
		NavigableSet<Integer> keySet1 = colourCountsArray.get(0).navigableKeySet();
		NavigableSet<Integer> keySet2 = anotherGroupInvariant.colourCountsArray.get(0).navigableKeySet();
		
		Iterator<Integer> iit1 = keySet1.iterator();
		Iterator<Integer> iit2 = keySet2.iterator();
		while (iit1.hasNext()) {
			Integer t1 = iit1.next(), t2 = iit2.next();
			cmp = t1 - t2;
			if (cmp != 0) return cmp;
			
			cmp = colourCountsArray.get(0).get(t1) - anotherGroupInvariant.colourCountsArray.get(0).get(t2);
			if (cmp != 0) return cmp;
		}
		
		for (int i = 1; i <= rounds; i++) {
			cmp = numberOfColorClassesArray.get(i).compareTo(anotherGroupInvariant.numberOfColorClassesArray.get(i));
			if (cmp != 0) return cmp;
			
			// should be the same as the above
			cmp = uniqueColorsArray.get(i-1).size() - anotherGroupInvariant.uniqueColorsArray.get(i-1).size();
			if (cmp != 0) return cmp;
			
			Iterator<Color> cit1 = uniqueColorsArray.get(i-1).iterator();
			Iterator<Color> cit2 = anotherGroupInvariant.uniqueColorsArray.get(i-1).iterator();
			
			while (cit1.hasNext()) {
				Color t1 = cit1.next(), t2 = cit2.next();
				cmp = t1.compareTo(t2);
				if (cmp != 0) return cmp;
			}
			
			// Also should be the same as numberOfColorClassesArray.get(i).size()
			cmp = colourCountsArray.get(i).size() - anotherGroupInvariant.colourCountsArray.get(i).size();
			if (cmp != 0) return cmp;
			
			keySet1 = colourCountsArray.get(i).navigableKeySet();
			keySet2 = anotherGroupInvariant.colourCountsArray.get(i).navigableKeySet();
			
			iit1 = keySet1.iterator();
			iit2 = keySet2.iterator();
			while (iit1.hasNext()) {
				Integer t1 = iit1.next(), t2 = iit2.next();
				cmp = t1 - t2;
				if (cmp != 0) return cmp;
				
				cmp = colourCountsArray.get(i).get(t1) - anotherGroupInvariant.colourCountsArray.get(i).get(t2);
				if (cmp != 0) return cmp;
			}
		}
		
		return 0;
	}
	
	public boolean equals(GroupInvariant o) {
		return compareTo(o) == 0;
	}
}
