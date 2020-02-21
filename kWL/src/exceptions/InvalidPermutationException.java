package exceptions;

/**
 * InvalidPermutationException is thrown during parsing a Permutation String if
 * it is (as is apparent) invalid.
 */
public class InvalidPermutationException extends Exception {
	
	/* version ID for serialised form. */
	private static final long serialVersionUID = -7898722555145938677L;
	
	public InvalidPermutationException(String errorMessage) {
		super(errorMessage);
	}
	
}
