package ca.utoronto.utm.mcs.exception;

public class ValidationFailedException extends Exception {

	private static final long serialVersionUID = 9017138461689425355L;
	
	public ValidationFailedException(String msg) {
		super(msg);
	}
	
}
