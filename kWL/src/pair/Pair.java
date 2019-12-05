package pair;

import java.util.ArrayList;

public class Pair implements Comparable<Pair> {
	
	ArrayList<Integer[]> generatedBy;
	Integer[][] yield;
	
	public Pair(ArrayList<Integer[]> generatedBy, Integer[][] yield) {
		this.generatedBy = generatedBy;
		this.yield = yield;
	}
	
	public int compareTo(Pair p) {
		int cmp = generatedBy.size() - p.generatedBy.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < generatedBy.size(); i++) {
			int length1 = generatedBy.get(i).length;
			int length2 = p.generatedBy.get(i).length;
			cmp = length1 - length2;
			if (cmp != 0) return cmp;
			
			for (int j = 0; j < length1; j++) {
				cmp = generatedBy.get(i)[j] - p.generatedBy.get(i)[j];
				if (cmp != 0) return cmp;
			}
		}
		
		// yield.length is not compared
		// i.e., number of generators is assumed to be the same
		// yield[i].length is not compared
		// i.e., order of groups is assumed to be the same
		for (int i = 0; i < yield.length; i++) {			
			for (int j = 0; j < yield[i].length; j++) {
				cmp = yield[i][j] - p.yield[i][j];
				if (cmp != 0) return cmp;
			}
		}
		
		return 0;
	}
	
	public boolean equals(Pair p) {
		return compareTo(p) == 0;
	}
	
}
