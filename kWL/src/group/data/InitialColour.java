package group.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * InitialColour is a class used to denote the initial colour data: a 'generatedBy' ArrayList which
 * stores a minimum length sequence of generators that generate the specific element, and a 2D
 * Integer array that is used t hold the result of the multiplication of each generated element by
 * a generator.
 * 
 * @author Karim Elsheikh
 */
public class InitialColour implements Comparable<InitialColour>, Serializable {
	
	/* version ID for serialised form. */
	private static final long serialVersionUID = 1766600835179700276L;
	
	public ArrayList<Integer[]> generatedBy;
	public Integer[][] yield;
	
	public InitialColour(ArrayList<Integer[]> generatedBy, Integer[][] yield) {
		this.generatedBy = generatedBy;
		this.yield = yield;
	}
	
	public int compareTo(InitialColour o) {
		int cmp = generatedBy.size() - o.generatedBy.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < generatedBy.size(); i++) {
			int length1 = generatedBy.get(i).length;
			int length2 = o.generatedBy.get(i).length;
			cmp = length1 - length2;
			if (cmp != 0) return cmp;
			
			for (int j = 0; j < length1; j++) {
				cmp = generatedBy.get(i)[j] - o.generatedBy.get(i)[j];
				if (cmp != 0) return cmp;
			}
		}
		
		// yield.length is not compared
		// i.e., number of generators is assumed to be the same
		// yield[i].length is not compared
		// i.e., order of groups is assumed to be the same
		for (int i = 0; i < yield.length; i++) {			
			for (int j = 0; j < yield[i].length; j++) {
				cmp = yield[i][j] - o.yield[i][j];
				if (cmp != 0) return cmp;
			}
		}
		
		return 0;
	}
	
	public boolean equals(InitialColour o) {
		return compareTo(o) == 0;
	}
	
}
