package kWL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * ArrayListComparator is a Class that implements the Comparator interface for ArrayLists
 * of integers. Used mainly for comparison between initial colours of the group.
 * 
 * @author Karim Elsheikh
 */
public class ArrayListComparator implements Comparator<ArrayList<Integer>>, Serializable {
	
	/* version ID for serialised form. */
	private static final long serialVersionUID = -2270921455737146094L;
	
	/**
	 * Compare Method that compares between 2 given ArrayLists of Integers lexicographically.
	 * (i.e., finds the first index where the Integers differ and compares them)
	 * 
	 * Assumes both ArrayLists have the same number of elements.
	 * 
	 * @param	array1	first ArrayList of Integers
	 * @param	array2	second ArrayList of Integers with the same size as the first Array
	 * @return	int		result of the comparison
	 */
	public int compare(ArrayList<Integer> array1, ArrayList<Integer> array2) 
	{
		int cmp;
		for (int i = 0; i < array1.size(); i++) {
			cmp = array1.get(i) - array2.get(i);
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
	/**
	 * Compare Method that additionally compares the lengths of both ArrayLists first.
	 * 
	 * @param	array1	first ArrayList of Integers
	 * @param	array2	second ArrayList of Integers
	 * @return	int		result of the comparison
	 */
	/* Note: Unused and hence need not be compiled
	public int compare(ArrayList<Permutation> array1, ArrayList<Permutation> array2) 
	{
		int cmp = array1.size() - array2.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < array1.size(); i++) {
			cmp = array1.get(i).compareTo(array2.get(i));
			if (cmp != 0) return cmp;
		}
		
		return 0;
    }
	*/
	
}
