package com.test.booking;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;
import com.test.util.LoginSystem;

public class BookingDetailView {
    public static void main(String[] args) throws NumberFormatException, IOException {
        // 예제: 사용자 예약 목록 가져오기
        BookingService bookingService = new BookingService();
        AccommodationService accommodationService = new AccommodationService();
        Scanner scanner = new Scanner(System.in);

        // 로그인된 사용자 ID 예시
	    int loggedInUserId = Integer.parseInt(LoginSystem.getUserIndex());

        // 사용자 예약 목록 가져오기
        List<Booking> userBookings = bookingService.getUserBookings(loggedInUserId);

        // 예약된 숙소가 없는 경우 처리
        if (userBookings.isEmpty()) {
            System.out.println("예약된 숙소가 없습니다.");
            return;
        }

        // 예약된 숙소 리스트 출력
        System.out.println("+" + "-".repeat(50) + "+");
        System.out.println("|" + " ".repeat(18) + "예약한 숙소 리스트" + " ".repeat(18) + "|");
        System.out.println("+" + "-".repeat(50) + "+");

        int index = 1;
        for (Booking booking : userBookings) {
            Accommodation accommodation = accommodationService.getAccommodationById(booking.getAccommodationId());
            if (accommodation != null) {
                System.out.printf("| %d. 숙소 이름: %-35s |\n", index, accommodation.getAccommodationName());
                System.out.printf("|    체크인: %s, 체크아웃: %s         |\n", booking.getCheckInDate(), booking.getCheckOutDate());
                System.out.println("+" + "-".repeat(50) + "+");
                index++;
            }
        }

        // 사용자에게 선택 요청
        System.out.println("1. 숙소 상세정보 보기");
        System.out.println("2. 뒤로 가기");
        System.out.print("번호 입력: ");
        int userChoice = scanner.nextInt();

        // 뒤로 가기 선택
        if (userChoice == 2) {
            System.out.println("뒤로 이동합니다.");
            return;
        }

        // 숙소 선택
        System.out.print("숙소 번호를 선택하세요: ");
        int selectedIndex = scanner.nextInt();
        if (selectedIndex < 1 || selectedIndex > userBookings.size()) {
            System.out.println("잘못된 입력입니다. 프로그램을 종료합니다.");
            return;
        }

        // 선택된 숙소 상세정보 출력
        Booking selectedBooking = userBookings.get(selectedIndex - 1);
        Accommodation selectedAccommodation = accommodationService.getAccommodationById(selectedBooking.getAccommodationId());

        if (selectedAccommodation != null) {
            System.out.println("+" + "-".repeat(50) + "+");
            System.out.println("|" + " ".repeat(19) + "숙소 상세정보" + " ".repeat(19) + "|");
            System.out.println("+" + "-".repeat(50) + "+");
            System.out.printf("| 숙소 이름: %-40s |\n", selectedAccommodation.getAccommodationName());
            System.out.printf("| 주소: %-42s |\n", selectedAccommodation.getAddress());
            System.out.printf("| 최대 인원: %-36d |\n", selectedAccommodation.getMaxGuest());
            System.out.printf("| 가격: %-40.2f |\n", selectedAccommodation.getPrice());
            System.out.printf("| 공지사항: %-35s |\n", selectedAccommodation.getNotice());
            System.out.println("+" + "-".repeat(50) + "+");
        } else {
            System.out.println("선택한 숙소 정보를 찾을 수 없습니다.");
        }
    }
}
