import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListComparator implements Comparator<ArrayList<Integer>> {
	
	// Compare Method that doesn't compares lengths
	public int compare(ArrayList<Integer> array1, ArrayList<Integer> array2) 
	{
		int cmp;
		for (int i = 0; i < array1.size(); i++) {
			cmp = array1.get(i) - array2.get(i);
			if (cmp != 0) return cmp;
		}
		
		return 0;
    }
	
	// Compare Method that compares lengths
//	public int compare(ArrayList<Permutation> array1, ArrayList<Permutation> array2) 
//	{
//		int cmp = array1.size() - array2.size();
//		if (cmp != 0) return cmp;
//		
//		for (int i = 0; i < array1.size(); i++) {
//			cmp = array1.get(i).compareTo(array2.get(i));
//			if (cmp != 0) return cmp;
//		}
//		
//		return 0;
//    }
	
}
