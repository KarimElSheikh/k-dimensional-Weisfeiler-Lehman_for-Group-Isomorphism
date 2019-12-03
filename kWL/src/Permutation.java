public class Permutation implements Comparable<Permutation> {
	
	Integer[] array;
	
	public Permutation(Integer[] array) {
		this.array = array;
	}
	
	public static Permutation identityPermutation(int degree) {
		Integer[] array = new Integer[degree+1];
		for (int i = 1; i <= degree; i++)
			array[i] = i;
		
		return new Permutation(array);
	}
	
	// Assumes both arrays' lengths are equal
	public Permutation multiply(Permutation p) {
		Integer[] arrayOut = new Integer[array.length];
		
		for (int i = 1; i < array.length; i++) {
			arrayOut[i] = p.array[array[i]];
		}
		
		return new Permutation(arrayOut);
	}
	
	public Permutation copy() {
		Integer[] arrayCopy = new Integer[array.length];
		
		for (int i = 0; i < array.length; i++) {
			arrayCopy[i] = array[i];
		}
		
		return new Permutation(arrayCopy);
	}

	public int compareTo(Permutation p) {
		int cmp;
		
		for (int i = 1; i < array.length; i++) {
			cmp = array[i] - p.array[i];
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
}
