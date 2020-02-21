package kWL;

/**
 * Class Permutation defines a permutation that acts on a subset of the natural numbers as an Integer array.
 * Each integer stored at a specific index in the array permutes this index to that integer.
 * We ignore the index 0 when dealing with the array (for example for the method multiply()).
 * 
 * @author Karim
 */
public class Permutation implements Comparable<Permutation> {
	
	public Integer[] array;
	
	public Permutation(Integer[] array) {
		this.array = array;
	}
	
	public static Permutation identityPermutation(int degree) {
		Integer[] array = new Integer[degree+1];
		for (int i = 1; i <= degree; i++)
			array[i] = i;
		
		return new Permutation(array);
	}
	
	/**
	 * Multiplies this Permutation with another Permutation p and returns the result.
	 * Assumes both arrays' lengths are equal which is the case if they've been initialised
	 * with the same degree.
	 * 
	 * @param	p			the 2nd multiplicand of the multiplication operation.
	 * @return	Permutation	that is the result of multiplying this Permutation by p.
	 */
	public Permutation multiply(Permutation p) {
		Integer[] arrayOut = new Integer[array.length];
		
		for (int i = 1; i < array.length; i++) {
			arrayOut[i] = p.array[array[i]];
		}
		
		return new Permutation(arrayOut);
	}
	
	/**
	 * Copies the Permutation starting from index 0 (index 0 is supposed to be irrelevant in my usage,
	 * but we copy it too anyway).
	 *  
	 * @return	Permutation	that is an exact copy of this Permutation		
	 */
	public Permutation copy() {
		Integer[] arrayCopy = new Integer[array.length];
		
		for (int i = 0; i < array.length; i++) {
			arrayCopy[i] = array[i];
		}
		
		return new Permutation(arrayCopy);
	}

	/**
	 * Compares this Permutation with another Permutation p.
	 * Assumes both arrays' lengths are equal which is the case if they've been initialised
	 * with the same degree.
	 * Compares starting from index 1.
	 * 
	 * @param	p	the Permutation to compare to
	 * @return	int	which is the result of the comparison (0 if equal).
	 */
	public int compareTo(Permutation p) {
		int cmp;
		
		for (int i = 1; i < array.length; i++) {
			cmp = array[i] - p.array[i];
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	public boolean equals(Permutation p) {
		return compareTo(p) == 0;
	}
	
}
