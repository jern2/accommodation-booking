package com.test;

import com.test.booking.BookingService;
import com.test.util.LoginSystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        BookingService bookingService = new BookingService();
        //booking Test
        //1. 예약 추가
        //bookingService.addBooking(1,1,"2025-01-06","2025-01-07", 4,  220000);
        //2. 예약 변경
        //bookingService.modifyBooking(4,"2025-01-15", "2025-01-20", 10);
        //3. 예약 조회(테스트 실패)
        //bookingService.getUserBookings(5);
        //4. 예약 취소
        //bookingService.cancelBooking(4,1);

        //login System test
        //1. 로그인
        LoginSystem.login("40ef167m", "3cg62srxmrma");
        LoginSystem.logout();
    }
}
