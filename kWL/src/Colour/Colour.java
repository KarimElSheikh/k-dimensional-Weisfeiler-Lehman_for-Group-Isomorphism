package Colour;

import java.util.Iterator;
import java.util.NavigableSet;

import datastructure.IndexedTreeMap3;

public class Colour implements Comparable<Colour> {
	
	Integer colour;
	IndexedTreeMap3 multiSet;
	
	public Colour(Integer colour, IndexedTreeMap3 multiSet) {
		this.colour = colour;
		this.multiSet = multiSet;
	}
	
	public int compareTo(Colour p) {
		int cmp = colour - p.colour;
		if (cmp != 0) return cmp;
		
		NavigableSet<Integer> keySet1 = multiSet.navigableKeySet();
		NavigableSet<Integer> keySet2 = p.multiSet.navigableKeySet();
		
		Iterator<Integer> it1 = keySet1.iterator();
		Iterator<Integer> it2 = keySet2.iterator();
		while (it1.hasNext() || it2.hasNext()) {
			if (!it1.hasNext()) return -1;
			if (!it2.hasNext()) return 1;
			
			Integer t1 = it1.next(), t2 = it2.next();
			cmp = t1 - t2;
			if (cmp != 0) return cmp;
			
			cmp = multiSet.get(t1) - p.multiSet.get(t2);
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	public boolean equals(Colour c) {
		return compareTo(c) == 0;
	}
	
}
