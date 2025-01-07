package com.test.booking;

import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;
import com.test.util.FileUtil;
import com.test.util.LoginSystem;
import com.test.util.ValidationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BookingService {

	private List<Booking> bookings = new ArrayList<>();
//	private static final String FILE_PATH = ".\\data\\booking_list.txt"; // 윈도우 환경
	private static final String FILE_PATH = "./data/booking_list.txt";   // 맥 환경

	// Constructor to load bookings from file
	public BookingService() {
		loadBookings();
	}

	// 예약 추가
	public Booking addBooking(int userIndex, int accommodationId, String checkInDate, String checkOutDate, int numGuests, int totalPrice) {
		//reservationID 자동생성(size() + 1)
		int newId = generateBookingId();
		Booking newBooking = new Booking(newId, userIndex, accommodationId, checkInDate, checkOutDate, numGuests, totalPrice);
		bookings.add(newBooking);
		saveBookings();
		System.out.println("예약이 완료되었습니다. 예약 ID: " + newId);
		return newBooking;
	}

	// 예약 변경
	public boolean modifyBooking(int bookingId, String newCheckInDate, String newCheckOutDate, int newNumGuests) {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId) {
				// 숙박 기간 계산
				long stayDuration = ValidationUtil.calculateDaysBetween(newCheckInDate, newCheckOutDate);
				if (stayDuration <= 0) {
					System.out.println("숙박 기간이 유효하지 않습니다.");
					return false;
				}

				// 숙소 정보 가져오기
				AccommodationService accommodationService = new AccommodationService();
				Accommodation accommodation = accommodationService.getAccommodationById(booking.getAccommodationId());
				if (accommodation == null) {
					System.out.println("숙소 정보를 찾을 수 없습니다.");
					return false;
				}

				// 총 금액 계산 (int로 처리)
				int totalPrice = (int) (stayDuration * accommodation.getPrice());

				// 예약 정보 수정
				booking.setCheckInDate(newCheckInDate);
				booking.setCheckOutDate(newCheckOutDate);
				booking.setNumGuests(newNumGuests);
				booking.setTotalPrice(totalPrice); // 총 금액 저장

				// 예약 정보 파일로 저장
				saveBookings();
				System.out.println("예약이 성공적으로 변경되었습니다.");
				System.out.printf("새로운 총 금액: %d원\n", totalPrice);
				return true;
			}
		}
		System.out.println("예약 ID를 찾을 수 없습니다.");
		return false;
	}


//	// 예약 변경
//	public boolean modifyBooking(int bookingId, String newCheckInDate, String newCheckOutDate, int newNumGuests,) {
//		for (Booking booking : bookings) {
//			if (booking.getBookingId() == bookingId) {
//				booking.setCheckInDate(newCheckInDate);
//				booking.setCheckOutDate(newCheckOutDate);
//				booking.setNumGuests(newNumGuests);
//
//				saveBookings();
//				System.out.println("예약이 성공적으로 변경되었습니다.");
//				return true;
//			}
//		}
//		System.out.println("예약 ID를 찾을 수 없습니다.");
//		return false;
//	}

	// 예약 취소
	public boolean cancelBooking(int bookingId, int userIndex, String password) throws IOException {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId && Integer.parseInt(LoginSystem.getUserIndex()) == userIndex && LoginSystem.getUserPassword().equals(password)) {
				bookings.remove(booking);
				saveBookings();
				System.out.println("예약이 취소되었습니다.");
				return true;
			}
		}
		System.out.println("예약 ID를 찾을 수 없거나 사용자 정보가 일치하지 않습니다.");
		return false;
	}
	
	//예약 조회
	public List<Booking> getUserBookings(int userIndex) {
	    List<Booking> userBookings = new ArrayList<>();
	    for (Booking booking : bookings) {
//	        System.out.println("Booking UserID: " + booking.getUserId() + ", Expected UserIndex: " + userIndex);
	        if (booking.getUserId() == userIndex) { // 타입 일치 확인
	            userBookings.add(booking);
	        }
	    }
	    if (userBookings.isEmpty()) {
	        System.out.println("해당 사용자의 예약이 없습니다.");
	    } 
	    return userBookings;
	}

	// 파일에서 예약 로드
	private void loadBookings() {
		List<String> lines = FileUtil.readFromFile(FILE_PATH);
		for (String line : lines) {
			bookings.add(Booking.fromFile(line));
		}
	}

	// 예약 정보를 파일에 저장
	private void saveBookings() {
		List<String> lines = new ArrayList<>();
		for (Booking booking : bookings) {
			lines.add(booking.toFileFormat());
		}
		FileUtil.writeToFile(FILE_PATH, lines);
	}

	// 예약 ID 생성
	private int generateBookingId() {
		return bookings.size() == 0 ? 1 : bookings.get(bookings.size() - 1).getBookingId() + 1;
	}

	public boolean addReview(int bookingId, int loggedInUserId, String reviewContent, int rating) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean updateBookingTotalPrice(int bookingId, int totalPrice) {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId) {
				booking.setTotalPrice(totalPrice); // 총 금액 업데이트
				saveBookings(); // 파일로 저장
				return true;
			}
		}
		return false;
	}
}
