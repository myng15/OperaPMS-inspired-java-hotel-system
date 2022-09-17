package hotel_system;

public class Guest {
	private int id;
	private String fname;
	private String lname;
	private String phone;
	private String email;
	private String company;
	private int accompanying;

	public static class Builder {
		private int id;
		private String fname;
		private String lname;
		private String phone;
		private String email;
		private String company;
		private int accompanying;
		
		public Builder(String fname, String lname, String phone) {
			this.fname = fname;
			this.lname = lname;
			this.phone = phone;
		}
		
		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Builder fname(String fname) {
			this.fname = fname;
			return this;
		}
		
		public Builder lname(String lname) {
			this.lname = lname;
			return this;
		}
		
		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder company(String company) {
			this.company = company;
			return this;
		}
		
		public Builder accompanying(int accompanying) {
			this.accompanying = accompanying;
			return this;
		}
		
		public Guest build() {
//			Example how parameter fields can be validated in the build() method before returning the new Guest object - an advantage of a Builder class over a helper class 			
//			For this project, this exception will however be handled in an if-else clause when the addGuest method is triggered in ManageGuestsForm  
//			if (fname.trim().equals("") || lname.trim().equals("") || phone.trim().equals("")) {
//		        throw new IllegalArgumentException("First Name, Last Name and Phone Number Required");
//		    }
			return new Guest(this);
		}
		
	}
	
	private Guest(Builder builder) {
	    this.id = builder.id;
		this.fname = builder.fname;
	    this.lname = builder.lname;
	    this.phone = builder.phone;
	    this.email = builder.email;
	    this.company = builder.company;
	    this.accompanying = builder.accompanying;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getFName() {
		return this.fname;
	}
	
	public String getLName() {
		return this.lname;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public int getAccompaniedGuest() {
		return this.accompanying;
	}
}
