package com.test.booking;

public class Booking {
<<<<<<< HEAD
	
	private String bookingId;
	private String userId;
	private String accommodationId;
	private String checkInDate;
	private String checkOutDate;
	private String numGuests;
	private String totalPrice;
	
	public Booking(String bookingId, String userId, String accommodationId, String checkInDate, String checkOutDate,
			String numGuests, String totalPrice) {
		super();
=======
	private int bookingId;
	private int userId;
	private int accommodationId;
	private String checkInDate;
	private String checkOutDate;
	private int numGuests;
	private int totalPrice;

	// 생성자
	public Booking(int bookingId, int userId, int accommodationId, String checkInDate, String checkOutDate, int numGuests, int totalPrice) {
>>>>>>> 070ade78c3f768770151273ad3e2119adbee1bc1
		this.bookingId = bookingId;
		this.userId = userId;
		this.accommodationId = accommodationId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numGuests = numGuests;
		this.totalPrice = totalPrice;
	}

	// Getters and Setters
	public int getBookingId() {
		return bookingId;
	}

	public int getUserId() {
		return userId;
	}

	public int getAccommodationId() {
		return accommodationId;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	// Convert to file format
	public String toFileFormat() {
		return bookingId + "■" + userId + "■" + accommodationId + "■" + checkInDate + "■" + checkOutDate + "■" + numGuests + "■" + totalPrice;
	}

	// Load from file format
	public static Booking fromFile(String line) {
		String[] parts = line.split("■");
		return new Booking(
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]),
				Integer.parseInt(parts[2]),
				parts[3],
				parts[4],
				Integer.parseInt(parts[5]),
				Integer.parseInt(parts[6])
		);
	}
	
	
	
	
	
}
