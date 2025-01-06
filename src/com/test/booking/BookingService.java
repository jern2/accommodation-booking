package com.test.booking;

import com.test.accommodation.Review;
import com.test.user.User;


public class BookingService {
	
	ReviewManager reviewManager = new ReviewManager();
	
	
	//예약 생성 전체 처리
	public Booking createBooking(String bookingId, User user, String accommodationId, String checkInDate, String checkOutDate, int GuestNum) {
		return null;
	}
	
	//유저의 예약 조회
	public void getBookings(User user) {
		
	}
	
	//예약 변경
	public void updateBooking(String bookingId, String newCheckIn, String newCheckOut, String newNumGuest) {
		
	}
	
	//예약 취소
	public void cancelBooking(String bookinId, String password) {
		
	}
	
	//리뷰 작성
	public void addReview(int bookingId, String content, int rating) {
		Review review = new Review(rating, content, rating, content, rating);
		
	}
}
