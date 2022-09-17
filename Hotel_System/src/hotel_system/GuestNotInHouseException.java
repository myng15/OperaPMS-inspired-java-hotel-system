package hotel_system;

public class GuestNotInHouseException extends Exception {
	public GuestNotInHouseException(String message) {
		super(message);
	}
}
