package hotel_system;

public class GuestAlreadyCheckedInException extends Exception {
	public GuestAlreadyCheckedInException(String message) {
		super(message);
	}
}
