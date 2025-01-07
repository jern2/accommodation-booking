package com.test.booking;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;
import com.test.util.LoginSystem;

public class BookingView {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BookingService bookingService = new BookingService();
        AccommodationService accommodationService = new AccommodationService();
        Scanner scanner = new Scanner(System.in);
        
        
        // 로그인된 사용자 ID 예시
        int loggedInUserId = Integer.parseInt(LoginSystem.getUserIndex());

        // 사용자 예약 목록 가져오기
        List<Booking> userBookings = bookingService.getUserBookings(loggedInUserId);

        if (userBookings.isEmpty()) {
            System.out.println("예약된 숙소가 없습니다.");
            return;
        }

        int index = 1;

        System.out.println("+" + "-".repeat(50) + "+");
        System.out.println("|" + " ".repeat(18) + "예약한 숙소 리스트" + " ".repeat(18) + "|");
        System.out.println("+" + "-".repeat(50) + "+");
        System.out.printf("[번호]\t [지역]\t [숙소명]\t [최대 인원]\t [가격]\n");

        // 예약된 숙소 리스트 출력
        for (Booking booking : userBookings) {
            Accommodation accommodation = accommodationService.getAccommodationById(booking.getAccommodationId());
            if (accommodation != null) {
                System.out.printf("| %d\t %s\t\t %s\t %d\t %d\n", index, accommodation.getArea(), accommodation.getAccommodationName(), accommodation.getMaxGuest(), accommodation.getPrice());
                System.out.println("|" + " ".repeat(50));
                index++;
            } else {
                System.out.println("|   숙소 정보를 찾을 수 없습니다.                  |");
            }
        }

        System.out.println("+" + "-".repeat(50) + "+");
        System.out.println("0. 뒤로 가기");
        System.out.print("숙소 번호를 선택하세요: ");

        // 사용자 입력 처리
        int selectedIndex = scanner.nextInt();

        // 뒤로 가기 선택
        if (selectedIndex == 0) {
            System.out.println("뒤로 이동합니다.");
            return;
        }

        // 선택한 숙소 상세페이지로 이동
        if (selectedIndex > 0 && selectedIndex <= userBookings.size()) {
            Booking selectedBooking = userBookings.get(selectedIndex - 1);
            Accommodation selectedAccommodation = accommodationService.getAccommodationById(selectedBooking.getAccommodationId());

            if (selectedAccommodation != null) {
                // 숙소 상세정보 출력
                System.out.println("+" + "-".repeat(50) + "+");
                System.out.println("|" + " ".repeat(19) + "숙소 상세정보" + " ".repeat(19) + "|");
                System.out.println("+" + "-".repeat(50) + "+");
                System.out.printf(" 숙소 이름: %-40s \n", selectedAccommodation.getAccommodationName());
                System.out.printf(" 지역: %-42s \n", selectedAccommodation.getArea());
                System.out.printf(" 주소: %-42s \n", selectedAccommodation.getAddress());
                System.out.printf(" 최대 인원: %-36d \n", selectedAccommodation.getMaxGuest());
                System.out.printf(" 가격: %-40d \n", selectedAccommodation.getPrice());
                System.out.println(" 공지사항");
                printFormattedNotice(selectedAccommodation.getNotice(), 30);
                System.out.println("+" + "-".repeat(50) + "+");
            } else {
                System.out.println("선택한 숙소 정보를 찾을 수 없습니다.");
            }
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }
    
    //문자열 자르는 메서드
    public static void printFormattedNotice(String notice, int maxLength) {
        int start = 0;
        while (start < notice.length()) {
            // 현재 출력할 부분 계산
            int end = Math.min(start + maxLength, notice.length());
            String line = notice.substring(start, end);
            System.out.printf(" %-35s \n", line); // 좌측 정렬 및 너비 조정
            start = end; // 다음 부분으로 이동
        }
    }
}
