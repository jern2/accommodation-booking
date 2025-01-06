package com.test.booking;

public class Booking {
	String bookingId;
	String userId;
	String accommodationId;
	String checkInDate;
	String checkOutDate;
	String numGuests;
	String totalPrice;
	
	public Booking(String bookingId, String userId, String accommodationId, String checkInDate, String checkOutDate,
			String numGuests, String totalPrice) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.accommodationId = accommodationId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numGuests = numGuests;
		this.totalPrice = totalPrice;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(String accommodationId) {
		this.accommodationId = accommodationId;
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

	public String getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(String numGuests) {
		this.numGuests = numGuests;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", userId=" + userId + ", accommodationId=" + accommodationId
				+ ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", numGuests=" + numGuests
				+ ", totalPrice=" + totalPrice + "]";
	}
	
	
	
	
	
}
