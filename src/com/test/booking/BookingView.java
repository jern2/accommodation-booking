package com.test.booking;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;
import com.test.review.Review;
import com.test.review.ReviewService;
import com.test.util.LoginSystem;
import com.test.util.ValidationUtil;

public class BookingView {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BookingService bookingService = new BookingService();
        ReviewService reviewService = new ReviewService();
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
                System.out.printf(" 숙소명  : %-40s \n", selectedAccommodation.getAccommodationName());
                System.out.printf(" 지역   : %-42s \n", selectedAccommodation.getArea());
                System.out.printf(" 주소   : %-42s \n", selectedAccommodation.getAddress());
                System.out.printf(" 최대 인원: %-36d \n", selectedAccommodation.getMaxGuest());
                System.out.printf(" 가격: %-40d \n\n", selectedAccommodation.getPrice());
                System.out.println(" 공지사항");
                printFormattedNotice(selectedAccommodation.getNotice(), 40);
                System.out.println("\n캘린더");
                System.out.printf("[숙박일] %s\n", selectedBooking.getCheckInDate());
                System.out.printf("[퇴실일] %s\n", selectedBooking.getCheckOutDate());
                System.out.println("+" + "-".repeat(50) + "+");
                // 숙소 리뷰 출력
                System.out.println("+" + "-".repeat(50) + "+");
                System.out.println("|                    숙소 리뷰                   |");
                System.out.println("+" + "-".repeat(50) + "+");

                List<Review> reviews = reviewService.getReviewsByAccommodationId(selectedAccommodation.getId());
                if (reviews.isEmpty()) {
                    System.out.println("리뷰가 없습니다.");
                } else {
                    for (Review review : reviews) {
                        System.out.printf("- [작성자: %s] [평점: %d] %s\n", review.getUserName(), review.getRating(), review.getContent());
                    }
                }
                System.out.println("+" + "-".repeat(50) + "+");


                while (true) {
                    System.out.println("1. 예약 취소");
                    System.out.println("2. 예약 변경");
                    System.out.println("3. 리뷰 작성");
                    System.out.println("4. 뒤로 가기");
                    System.out.print("번호 입력: ");
                    int menuOption = scanner.nextInt();

                    switch (menuOption) {
                        case 1:
                            // 예약 취소
                            System.out.print("예약 취소를 위해 비밀번호를 입력하세요: ");
                            String password = scanner.next();
                            boolean isCancelled = bookingService.cancelBooking(selectedBooking.getBookingId(), loggedInUserId, password);
                            if (isCancelled) {
                                System.out.println("예약이 취소되었습니다.");
                                return;
                            } else {
                                System.out.println("비밀번호가 올바르지 않습니다.");
                            }
                            break;

                        case 2:
                            // 예약 변경
                            String newCheckInDate;
                            String newCheckOutDate;

                            // 체크인 날짜 입력 및 검증
                            while (true) {
                                System.out.print("변경할 체크인 날짜(YYYY-MM-DD): ");
                                newCheckInDate = scanner.next();
                                if (ValidationUtil.isValidDate(newCheckInDate)) {
                                    if (ValidationUtil.isDateInPast(newCheckInDate)) {
                                        System.out.println("체크인 날짜는 과거일 수 없습니다. 다시 입력해주세요.");
                                    } else {
                                        break;
                                    }
                                } else {
                                    System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.");
                                }
                            }

                            // 체크아웃 날짜 입력 및 검증
                            while (true) {
                                System.out.print("변경할 체크아웃 날짜(YYYY-MM-DD): ");
                                newCheckOutDate = scanner.next();
                                if (ValidationUtil.isValidDate(newCheckOutDate)) {
                                    if (ValidationUtil.isCheckOutDateAfterCheckIn(newCheckInDate, newCheckOutDate)) {
                                        break;
                                    } else {
                                        System.out.println("체크아웃 날짜는 체크인 날짜 이후여야 합니다. 다시 입력해주세요.");
                                    }
                                } else {
                                    System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.");
                                }
                            }

                            // 숙박 기간 계산
                            int stayDuration = (int) ValidationUtil.calculateDaysBetween(newCheckInDate, newCheckOutDate);
                            if (stayDuration <= 0) {
                                System.out.println("숙박 기간이 유효하지 않습니다.");
                                break;
                            }

                            System.out.printf("총 숙박 기간: %d박 %d일\n", stayDuration, stayDuration + 1);

                            // 총 금액 계산
                            int totalPrice = (int) ValidationUtil.calculateTotalPrice(stayDuration, (int) selectedAccommodation.getPrice());
                            System.out.printf("1박 요금: %d원, 총 금액: %d원\n", (int) selectedAccommodation.getPrice(), totalPrice);

                            // 총 금액 업데이트
                            boolean isPriceUpdated = bookingService.updateBookingTotalPrice(selectedBooking.getBookingId(), totalPrice);
                            if (isPriceUpdated) {
                                System.out.println("총 금액이 성공적으로 업데이트되었습니다.");
                            } else {
                                System.out.println("총 금액 업데이트에 실패하였습니다.");
                            }

                            // 숙박 인원 입력
                            System.out.print("변경할 숙박 인원: ");
                            int newNumGuests = scanner.nextInt();

                            boolean isModified = bookingService.modifyBooking(selectedBooking.getBookingId(), newCheckInDate, newCheckOutDate, newNumGuests);
                            if (isModified) {
                                System.out.println("예약이 성공적으로 변경되었습니다.");
                            } else {
                                System.out.println("예약 변경에 실패하였습니다.");
                            }
                            break;


                        case 3:
                            // 리뷰 작성
                            System.out.print("리뷰를 입력하세요: ");
                            scanner.nextLine(); // 버퍼 비우기
                            String reviewContent = scanner.nextLine();
                            System.out.print("평점을 입력하세요(1-5): ");
                            int rating = scanner.nextInt();

                            //리뷰 추가
                            String userName = LoginSystem.getUserName(); // 로그인된 사용자의 이름 가져오기
                            boolean isReviewAdded = reviewService.addReview(loggedInUserId, userName, selectedAccommodation.getId(), reviewContent, rating);
                            if (isReviewAdded) {
                                System.out.println("리뷰가 성공적으로 등록되었습니다.");
                            } else {
                                System.out.println("리뷰 작성에 실패하였습니다.");
                            }
                            break;


                        case 4:
                            // 뒤로 가기
                            System.out.println("뒤로 이동합니다.");
                            return;

                        default:
                            System.out.println("잘못된 입력입니다.");
                    }
                }

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
