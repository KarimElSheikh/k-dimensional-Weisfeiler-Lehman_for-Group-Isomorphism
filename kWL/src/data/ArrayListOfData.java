package data;

import java.util.ArrayList;

/**
 * ArrayListOfData is just a class that holds 'Data' instances in an ArrayList along with a unique int id.
 * 
 * In the main method of the class 'Checking', each instance of this class (ArrayListOfData)
 * is used to associate a list of files with a group defined by 'id'.
 * For each file in the list of files associated with the group, we associate a Data instance
 * holding a crc32 hash and the file size.
 * 
 * @author Karim Elsheikh
 */
public class ArrayListOfData implements Comparable<ArrayListOfData> {
	
	public int id;
	public ArrayList<Data> array;
	
	public ArrayListOfData(int id) {
		this.id = id;
		array = new ArrayList<Data>();
	}
	
	public ArrayListOfData(int id, ArrayList<Data> array) {
		this.id = id;
		this.array = array;
	}
	
	public int compareTo(ArrayListOfData o) {
		int cmp = array.size() - o.array.size();
		if (cmp != 0) return cmp;
		
		for (int i = 0; i < array.size(); i++) {
			cmp = array.get(i).compareTo(o.array.get(i));
			if (cmp != 0) return cmp;
		}
		
		return 0;
	}
	
}
