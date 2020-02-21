package group.data;

import java.io.Serializable;

/**
 * Pair is a class used to hold 2 integers, its mainly required by the class 'Colour'.
 * 
 * @author Karim Elsheikh
 */
public class Pair implements Comparable<Pair>, Serializable {
	
	/* version ID for serialised form. */
	private static final long serialVersionUID = 5085418280083257080L;
	
	public int id1;
	public int id2;
	
	public Pair(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public int compareTo(Pair o) {
		int cmp = id1 - o.id1;
		if (cmp != 0) return cmp;
		
		return id2 - o.id2;
	}
	
	public boolean equals(Pair o) {
		return compareTo(o) == 0;
	}
	
}
