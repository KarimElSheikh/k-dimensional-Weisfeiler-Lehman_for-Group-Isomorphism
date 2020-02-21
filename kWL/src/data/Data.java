package data;

/**
 * Data is a class that is used to hold some information about a file: its size and the crc32 hash.
 * The crc32 hash needs unsigned 32 bits to be stored, we store it in a long data type.
 * 
 * This class is mainly used in the main method of the class 'Checking'.
 * 
 * @author Karim Elsheikh
 */
public class Data implements Comparable<Data> {
	
	public int size;
	public long crc32;
	
	public Data(int size, long crc32) {
		this.size = size;
		this.crc32 = crc32;
	}
	
	public int compareTo(Data other) {
		int cmp = size - other.size;
		if (cmp != 0)
			return cmp;
		
		// compares the highest 32 bits first (note that this is a signed shift and hence if there is any signed
		// contribution in the highest 32 bits it will be preserved)
		long Lcmp = crc32 - other.crc32;
		cmp = (int) (Lcmp >> 32);
		if (cmp != 0)
			return cmp;
		
		// returns the lowest 32 bits (whether equal or different, this will decide as all the others were equal)
		return (int) (Lcmp);
	}
	
	public boolean equals(Data p) {
		return compareTo(p) == 0;
	}
	
	public String toString() {
		return "size: " + size + ", crc32: " + Long.toHexString(crc32); 
	}
	
}
