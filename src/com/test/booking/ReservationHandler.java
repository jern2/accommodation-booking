package com.test.booking;

public class ReservationHandler {
    private static int accommodationId;
    private static String checkInDate;
    private static String checkOutDate;
    private static int numGuests;
    private static int totalPrice;

    public static void setReservationDetails(int id, String checkIn, String checkOut, int guests, int price) {
        accommodationId = id;
        checkInDate = checkIn;
        checkOutDate = checkOut;
        numGuests = guests;
        totalPrice = price;

        // 디버깅 로그 추가
        System.out.println("Reservation Details Set:");
        System.out.println("AccommodationId: " + accommodationId);
        System.out.println("CheckInDate: " + checkInDate);
        System.out.println("CheckOutDate: " + checkOutDate);
        System.out.println("NumGuests: " + numGuests);
        System.out.println("TotalPrice: " + totalPrice);
    }

    public static int getAccommodationId() {
        return accommodationId;
    }

    public static String getCheckInDate() {
        return checkInDate;
    }

    public static String getCheckOutDate() {
        return checkOutDate;
    }

    public static int getNumGuests() {
        return numGuests;
    }

    public static int getTotalPrice() {
        return totalPrice;
    }
}

