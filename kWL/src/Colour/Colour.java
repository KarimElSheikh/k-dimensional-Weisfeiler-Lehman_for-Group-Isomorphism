package Colour;

import java.util.Iterator;
import java.util.NavigableSet;

import datastructure.IndexedTreeMap2;

public class Colour implements Comparable<Colour> {
	
	Integer colour;
	IndexedTreeMap2<Integer> multiSet;
	
	public Colour(Integer colour, IndexedTreeMap2<Integer> multiSet) {
		this.colour = colour;
		this.multiSet = multiSet;
	}
	
	public int compareTo(Colour p) {
		int cmp = colour - p.colour;
		if (cmp != 0) return cmp;
		
		NavigableSet<Integer> keySet1 = multiSet.navigableKeySet();
		NavigableSet<Integer> keySet2 = p.multiSet.navigableKeySet();
		
		cmp = keySet1.size() - keySet2.size();
		if (cmp != 0) return cmp;
		
		Iterator<Integer> it1 = keySet1.iterator();
		Iterator<Integer> it2 = keySet2.iterator();
		while (it1.hasNext()) {
			Integer t1 = it1.next(), t2 = it2.next();
			cmp = t1 - t2;
			if (cmp != 0) return cmp; // Comparing colour (integer)
			
			cmp = multiSet.get(t1) - p.multiSet.get(t2); // Comparing count of the colour
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	public boolean equals(Colour c) {
		return compareTo(c) == 0;
	}
	
}
