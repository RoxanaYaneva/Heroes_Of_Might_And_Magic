package homm.exceptions;

public class InsufficientAmountOfMoneyException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientAmountOfMoneyException(String message) {
		super(message);
	}
}
