package group.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NavigableSet;

import datastructure.IndexedTreeMap2;

/**
 * Colour is a Class that is used to hold the colours for rounds starting from round 1.
 * Each colour is as defined in the Weisfeiler-Lehman method: an integer denoting the index of the colour
 * and a multiset of Pairs that groups all the "neighbouring" colours in pairs.
 * A Pair is just a created class that holds 2 integers denoting the 2 colours.
 * 
 * @author Karim Elsheikh
 */
public class Colour implements Comparable<Colour>, Serializable {
	
	/* version ID for serialised form. */
	private static final long serialVersionUID = -8770983009800911040L;
	
	public Integer colour;
	public IndexedTreeMap2<Pair> multiSet;
	
	public Colour(Integer colour, IndexedTreeMap2<Pair> multiSet) {
		this.colour = colour;
		this.multiSet = multiSet;
	}
	
	public int compareTo(Colour o) {
		int cmp = colour - o.colour;
		if (cmp != 0) return cmp;
		
		NavigableSet<Pair> keySet1 = multiSet.navigableKeySet();
		NavigableSet<Pair> keySet2 = o.multiSet.navigableKeySet();
		
		cmp = keySet1.size() - keySet2.size();
		if (cmp != 0) return cmp;
		
		Iterator<Pair> it1 = keySet1.iterator();
		Iterator<Pair> it2 = keySet2.iterator();
		while (it1.hasNext()) {
			Pair t1 = it1.next(), t2 = it2.next();
			cmp = t1.compareTo(t2);
			if (cmp != 0) return cmp; // Comparing the colour (Pair)
			
			cmp = multiSet.get(t1) - o.multiSet.get(t2); // Comparing the count of the colour
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	public boolean equals(Colour o) {
		return compareTo(o) == 0;
	}
	
}
