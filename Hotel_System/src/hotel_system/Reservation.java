package hotel_system;

public class Reservation {
	private int id;
	private int guestID;
	private int roomNum;
	private String arrival;
	private String departure;
	private String rsvStatus;
	private double rate;
	private String channel;
	private String creditCard;
	private String expDate;

	public static class Builder {
		private int id;
		private int guestID;
		private int roomNum;
		private String arrival;
		private String departure;
		private String rsvStatus;
		private double rate;
		private String channel;
		private String creditCard;
		private String expDate;
		
		public Builder(int guestID, int roomNum, String arrival, String departure) {
			this.guestID = guestID;
			this.roomNum = roomNum;
			this.arrival = arrival;
			this.departure = departure;
		}
		
		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Builder guestID(int guestID) {
			this.guestID = guestID;
			return this;
		}
		
		public Builder roomNum(int roomNum) {
			this.roomNum = roomNum;
			return this;
		}
		
		public Builder arrival(String arrival) {
			this.arrival = arrival;
			return this;
		}
		
		public Builder departure(String departure) {
			this.departure = departure;
			return this;
		}
		
		public Builder rsvStatus(String rsvStatus) {
			this.rsvStatus = rsvStatus;
			return this;
		}
		
		public Builder rate(double rate) {
			this.rate = rate;
			return this;
		}
		
		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}
		
		public Builder creditCard(String creditCard) {
			this.creditCard = creditCard;
			return this;
		}
		
		public Builder expDate(String expDate) {
			this.expDate = expDate;
			return this;
		}
		
		public Reservation build() {
			return new Reservation(this);
		}
		
	}
	
	private Reservation(Builder builder) {
		this.id = builder.id;
		this.guestID = builder.guestID;
		this.roomNum = builder.roomNum;
		this.arrival = builder.arrival;
		this.departure = builder.departure;
		this.rsvStatus = builder.rsvStatus;
	    this.rate = builder.rate;
	    this.channel = builder.channel;
	    this.creditCard = builder.creditCard;
	    this.expDate = builder.expDate;
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getGuestID() {
		return this.guestID;
	}
	
	public int getRoomNum() {
		return this.roomNum;
	}
	
	public String getArrivalDate() {
		return this.arrival;
	}
	
	public String getDepartureDate() {
		return this.departure;
	}
	
	public String getRsvStatus() {
		return this.rsvStatus;
	}
	
	public double getRate() {
		return this.rate;
	}
	
	public String getChannel() {
		return this.channel;
	}
	
	public String getCreditCard() {
		return this.creditCard;
	}
	
	public String getExpDate() {
		return this.expDate;
	}
}
