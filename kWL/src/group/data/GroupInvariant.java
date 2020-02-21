package group.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;

import datastructure.IndexedTreeMap2;
import datastructure.IndexedTreeSet;

public class GroupInvariant implements Comparable<GroupInvariant>, Serializable {
	
	/* version ID for serialised form. */
	private static final long serialVersionUID = 9649186623056496L;
	
	public int id;
	public int order;
	public int rounds;
	public IndexedTreeSet<InitialColour> initialColours;
	public ArrayList<Integer> numberOfColourClassesArray;
	public ArrayList<IndexedTreeSet<Colour>> uniqueColoursArray;
	public ArrayList<IndexedTreeMap2<Integer>> colourCountsArray;
	
	public GroupInvariant(int id, int order, int rounds, IndexedTreeSet<InitialColour> initialColours,  ArrayList<Integer> numberOfColourClassesArray, ArrayList<IndexedTreeSet<Colour>> uniqueColoursArray, ArrayList<IndexedTreeMap2<Integer>> colourCountsArray) {
		this.id = id;
		this.order = order;
		this.rounds = rounds;
		this.initialColours = initialColours;
		this.numberOfColourClassesArray = numberOfColourClassesArray;
		this.uniqueColoursArray = uniqueColoursArray;
		this.colourCountsArray = colourCountsArray;
	}
	
	public int compareTo(GroupInvariant o) {
		int cmp = order - o.order;
		if (cmp != 0) return cmp;
		
		cmp = rounds - o.rounds;
		if (cmp != 0) return cmp;
		
		cmp = numberOfColourClassesArray.get(0).compareTo(o.numberOfColourClassesArray.get(0));
		if (cmp != 0) return cmp;
		
		// should be the same as the above
		cmp = initialColours.size() - o.initialColours.size();
		if (cmp != 0) return cmp;
		
		Iterator<InitialColour> it1 = initialColours.iterator();
		Iterator<InitialColour> it2 = o.initialColours.iterator();
		while (it1.hasNext()) {
			InitialColour t1 = it1.next(), t2 = it2.next();
			cmp = t1.compareTo(t2);
			if (cmp != 0) return cmp;
		}
		
		// Also should be the same as numberOfColourClassesArray.get(0).size()
		cmp = colourCountsArray.get(0).size() - o.colourCountsArray.get(0).size();
		if (cmp != 0) return cmp;
		
		NavigableSet<Integer> keySet1 = colourCountsArray.get(0).navigableKeySet();
		NavigableSet<Integer> keySet2 = o.colourCountsArray.get(0).navigableKeySet();
		
		Iterator<Integer> iit1 = keySet1.iterator();
		Iterator<Integer> iit2 = keySet2.iterator();
		while (iit1.hasNext()) {
			Integer t1 = iit1.next(), t2 = iit2.next();
			cmp = t1 - t2;
			if (cmp != 0) return cmp;
			
			cmp = colourCountsArray.get(0).get(t1) - o.colourCountsArray.get(0).get(t2);
			if (cmp != 0) return cmp;
		}
		
		for (int i = 1; i <= rounds; i++) {
			cmp = numberOfColourClassesArray.get(i).compareTo(o.numberOfColourClassesArray.get(i));
			if (cmp != 0) return cmp;
			
			// should be the same as the above
			cmp = uniqueColoursArray.get(i-1).size() - o.uniqueColoursArray.get(i-1).size();
			if (cmp != 0) return cmp;
			
			Iterator<Colour> cit1 = uniqueColoursArray.get(i-1).iterator();
			Iterator<Colour> cit2 = o.uniqueColoursArray.get(i-1).iterator();
			
			while (cit1.hasNext()) {
				Colour t1 = cit1.next(), t2 = cit2.next();
				cmp = t1.compareTo(t2);
				if (cmp != 0) return cmp;
			}
			
			// Also should be the same as numberOfColourClassesArray.get(i).size()
			cmp = colourCountsArray.get(i).size() - o.colourCountsArray.get(i).size();
			if (cmp != 0) return cmp;
			
			keySet1 = colourCountsArray.get(i).navigableKeySet();
			keySet2 = o.colourCountsArray.get(i).navigableKeySet();
			
			iit1 = keySet1.iterator();
			iit2 = keySet2.iterator();
			while (iit1.hasNext()) {
				Integer t1 = iit1.next(), t2 = iit2.next();
				cmp = t1 - t2;
				if (cmp != 0) return cmp;
				
				cmp = colourCountsArray.get(i).get(t1) - o.colourCountsArray.get(i).get(t2);
				if (cmp != 0) return cmp;
			}
		}
		
		return 0;
	}
	
	public boolean equals(GroupInvariant o) {
		return compareTo(o) == 0;
	}
	
}
