public class Helpers {
	
	public static Integer[] copyAndAdd(Integer[] array, int elementToAdd) {
		Integer[] arrayOut = new Integer[array.length+1];
		
		for (int i = 0; i < arrayOut.length-1; i++) {
			arrayOut[i] = array[i];
		}
		
		arrayOut[array.length] = elementToAdd;
		return arrayOut;
	}
	
}
