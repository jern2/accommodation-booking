package com.test.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;
import com.test.review.Review;
import com.test.review.ReviewService;
import com.test.util.LoginSystem;
import com.test.util.ValidationUtil;

public class BookingView {
    private BookingService bookingService = new BookingService();
    private ReviewService reviewService = new ReviewService();
    private AccommodationService accommodationService = new AccommodationService();
    private CalendarService calendarService = new CalendarService();
    private Scanner scanner = new Scanner(System.in);

    // 사용자 예약 목록 보여주기
    public void showUserBookings(int loggedInUserId) throws IOException {
        List<Booking> userBookings = bookingService.getUserBookings(loggedInUserId);

        if (userBookings.isEmpty()) {
            System.out.println("예약된 숙소가 없습니다.");
            return;
        }

        System.out.println("+" + "-".repeat(60) + "+");
        System.out.println("|" + " ".repeat(22) + "예약한 숙소 리스트" + " ".repeat(22) + "|");
        System.out.println("+" + "-".repeat(60) + "+");
        System.out.printf("[번호]\t [지역]\t [숙소명]\t [최대 인원]\t [가격]\n");

        int index = 1;
        for (Booking booking : userBookings) {
            Accommodation accommodation = accommodationService.getAccommodationById(booking.getAccommodationId());
            if (accommodation != null) {
                System.out.printf("| %d\t %s\t\t %s\t %d\t %d\n", index, accommodation.getArea(),
                        accommodation.getAccommodationName(), accommodation.getMaxGuest(), accommodation.getPrice());
                System.out.println("|" + " ".repeat(60));
                index++;
            } else {
                System.out.println("|   숙소 정보를 찾을 수 없습니다.                  |");
            }
        }

        System.out.println("+" + "-".repeat(60) + "+");
        System.out.println("0. 뒤로 가기");
        System.out.print("숙소 번호를 선택하세요: ");
        int selectedIndex = scanner.nextInt();

        if (selectedIndex == 0) {
            System.out.println("뒤로 이동합니다.");
            return;
        }

        if (selectedIndex > 0 && selectedIndex <= userBookings.size()) {
            Booking selectedBooking = userBookings.get(selectedIndex - 1);
            Accommodation selectedAccommodation = accommodationService.getAccommodationById(selectedBooking.getAccommodationId());
            showAccommodationDetails(selectedBooking, selectedAccommodation, loggedInUserId);
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    // 숙소 상세 정보 보여주기
    private void showAccommodationDetails(Booking booking, Accommodation accommodation, int loggedInUserId)
            throws IOException {
        if (accommodation == null) {
            System.out.println("숙소 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.println("+" + "-".repeat(60) + "+");
        System.out.println("|" + " ".repeat(29) + "숙소 상세정보" + " ".repeat(29) + "|");
        System.out.println("+" + "-".repeat(60) + "+");
        System.out.printf(" 숙소명  : %-40s \n", accommodation.getAccommodationName());
        System.out.printf(" 지역   : %-42s \n", accommodation.getArea());
        System.out.printf(" 주소   : %-42s \n", accommodation.getAddress());
        System.out.printf(" 최대 인원: %-36d \n", accommodation.getMaxGuest());
        System.out.printf(" 가격: %-40d \n\n", accommodation.getPrice());

        // 평균 평점
        double averageRating = reviewService.calculateAverageRating(accommodation.getId());
        System.out.printf(" 평균 평점: %-36.1f \n", averageRating);

        System.out.println(" 공지사항");
        printFormattedNotice(accommodation.getNotice(), 40);

        // 캘린더
        System.out.println("\n 예약 현황");
        LocalDate today = LocalDate.now();
        calendarService.showCalendar(accommodation.getId(), today.getYear(), today.getMonthValue(), bookingService);

        System.out.println("\n캘린더");
        System.out.printf("[숙박일] %s\n", booking.getCheckInDate());
        System.out.printf("[퇴실일] %s\n", booking.getCheckOutDate());
        System.out.println("+" + "-".repeat(60) + "+");

        // 숙소 리뷰
        showAccommodationReviews(accommodation.getId());
        showBookingActions(booking, accommodation, loggedInUserId);
    }

    // 숙소 리뷰 보여주기
    private void showAccommodationReviews(int accommodationId) {
        System.out.println("+" + "-".repeat(60) + "+");
        System.out.println("|                         숙소 리뷰                           |");
        System.out.println("+" + "-".repeat(60) + "+");

        List<Review> reviews = reviewService.getReviewsByAccommodationId(accommodationId);
        if (reviews.isEmpty()) {
            System.out.println("리뷰가 없습니다.");
        } else {
            for (Review review : reviews) {
                System.out.printf("- [작성자: %s] [평점: %d] %s\n", review.getUserName(), review.getRating(),
                        review.getContent());
            }
        }
        System.out.println("+" + "-".repeat(60) + "+");
    }

    // 예약 관련 작업 (취소, 변경, 리뷰 작성)
    private void showBookingActions(Booking booking, Accommodation accommodation, int loggedInUserId)
            throws IOException {
        while (true) {
            System.out.println("1. 예약 취소");
            System.out.println("2. 예약 변경");
            System.out.println("3. 리뷰 작성");
            System.out.println("4. 뒤로 가기");
            System.out.print("번호 입력: ");
            int menuOption = scanner.nextInt();

            switch (menuOption) {
                case 1:
                    cancelBooking(booking, loggedInUserId);
                    return;
                case 2:
                    modifyBooking(booking, accommodation);
                    return;
                case 3:
                    addReview(booking, accommodation, loggedInUserId);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 예약 취소
    private void cancelBooking(Booking booking, int loggedInUserId) throws IOException {
        System.out.print("예약 취소를 위해 비밀번호를 입력하세요: ");
        String password = scanner.next();
        if (bookingService.cancelBooking(booking.getBookingId(), loggedInUserId, password)) {
            System.out.println("예약이 취소되었습니다.");
        } else {
            System.out.println("비밀번호가 올바르지 않습니다.");
        }
    }

    // 예약 변경
    private void modifyBooking(Booking booking, Accommodation accommodation) throws IOException {
        System.out.print("변경할 체크인 날짜(YYYY-MM-DD): ");
        String newCheckInDate = scanner.next();
        System.out.print("변경할 체크아웃 날짜(YYYY-MM-DD): ");
        String newCheckOutDate = scanner.next();

        int stayDuration = (int) ValidationUtil.calculateDaysBetween(newCheckInDate, newCheckOutDate);
        int totalPrice = (int) ValidationUtil.calculateTotalPrice(stayDuration, accommodation.getPrice());

        System.out.printf("총 금액: %d원\n", totalPrice);
        bookingService.modifyBooking(booking.getBookingId(), newCheckInDate, newCheckOutDate, booking.getNumGuests());
    }

    private void addReview(Booking booking, Accommodation accommodation, int loggedInUserId) throws IOException {
        System.out.print("리뷰를 입력하세요: ");
        scanner.nextLine(); // 버퍼 비우기
        String reviewContent = scanner.nextLine();
        System.out.print("평점을 입력하세요(1-5): ");
        int rating = scanner.nextInt();

        // 체크아웃 날짜 전달
        String checkOutDate = booking.getCheckOutDate();

        if (reviewService.addReview(loggedInUserId, LoginSystem.getUserName(), accommodation.getId(), reviewContent, rating, checkOutDate)) {
            System.out.println("리뷰가 성공적으로 등록되었습니다.");
        } else {
            System.out.println("리뷰 작성에 실패하였습니다.");
        }
    }

    // 문자열 자르는 메서드
    public static void printFormattedNotice(String notice, int maxLength) {
        int start = 0;
        while (start < notice.length()) {
            int end = Math.min(start + maxLength, notice.length());
            System.out.printf(" %-35s \n", notice.substring(start, end));
            start = end;
        }
    }
}
