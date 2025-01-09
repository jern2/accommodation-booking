package com.test.payment;

import com.test.booking.Booking;

import java.io.*;
import java.util.*;

public class BookingRepository {

	public static List<Booking> loadBookings(String filePath) {
		List<Booking> bookings = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("■");
				
				// BookingRepository.java에서 Booking 객체 생성 부분 수정
				if (parts.length >= 7) {
				    int bookingId = Integer.parseInt(parts[0]);
				    int userId = Integer.parseInt(parts[1]);
				    int accommodationId = Integer.parseInt(parts[2]);  // 추가
				    String checkInDate = parts[3];
				    String checkOutDate = parts[4];
				    int numberOfGuests = Integer.parseInt(parts[5]);
				    int totalPrice = Integer.parseInt(parts[6]);
				    
				    Booking booking = new Booking(bookingId, userId, accommodationId, checkInDate, 
				                                checkOutDate, numberOfGuests, totalPrice);
				    bookings.add(booking);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bookings;
	}
}
