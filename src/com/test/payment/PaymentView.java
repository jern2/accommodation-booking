package com.test.payment;

import com.test.booking.BookingService;
import com.test.booking.ReservationHandler;
import com.test.booking.Booking;
import com.test.user.User;
import com.test.util.LoginSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentView {

    private PointPaymentService pointPaymentService = new PointPaymentService();
    private BookingService bookingService = new BookingService();
    
    public void showPaymentOptions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("결제 옵션을 선택하세요:");
        System.out.println("[1] 카드결제");
        System.out.println("[2] 포인트결제");

        int choice = scanner.nextInt();
        try {
            if (choice == 1) {
                processCardPayment();
            } else if (choice == 2) {
                processPointPayment();
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCardPayment() {
    	Scanner scan = new Scanner(System.in);
        System.out.print("카드사 입력: ");
        String cardComp = scan.nextLine();

        String cardNumber = getValidInput(scan, "카드 정보 입력 (양식: 0000-0000-0000-0000): ",
                "\\d{4}-\\d{4}-\\d{4}-\\d{4}", "잘못된 입력입니다. 다시 입력해주세요. (양식: 0000-0000-0000-0000)");

        String cardExp = getValidInput(scan, "카드 유효기한 입력 (양식: MM/YYYY): ",
                "(0[1-9]|1[0-2])/(\\d{4})", "잘못된 입력입니다. 다시 입력해주세요. (양식: MM/YYYY)");

        String cardPw = getValidInput(scan, "카드 비밀번호 앞 2자리: ",
                "\\d{2}", "잘못된 입력입니다. 입력은 2자리 숫자여야 합니다.");

        try {
            System.out.println("카드 결제가 완료되었습니다.");

            // 예약 정보 입력받기
            System.out.println("\n예약 정보를 입력해주세요.");
            System.out.print("사용자 ID: ");
            int userId = scan.nextInt();

            System.out.print("숙소 ID: ");
            int accommodationId = scan.nextInt();

            System.out.print("체크인 날짜 (YYYY-MM-DD): ");
            String checkInDate = scan.next();

            System.out.print("체크아웃 날짜 (YYYY-MM-DD): ");
            String checkOutDate = scan.next();

            System.out.print("숙박 인원: ");
            int numGuests = scan.nextInt();

            System.out.print("총 금액: ");
            int totalPrice = scan.nextInt();

            // 예약 추가
            Booking newBooking = bookingService.addBooking(Integer.parseInt(LoginSystem.getUserIndex()), accommodationId, checkInDate, checkOutDate, numGuests, totalPrice);

            System.out.println("예약이 완료되었습니다.");
            System.out.println("예약 ID: " + newBooking.getBookingId());
            System.out.println("즐거운 숙박되세요. 감사합니다.");
            saveBooking();
        } catch (Exception e) {
            System.out.println("결제 실패: " + e.getMessage());
        }
        
        
    }
    
    private String getValidInput(Scanner scan, String prompt, String regex, String errorMessage) {
        System.out.print(prompt);
        String input;
        while (true) {
            input = scan.nextLine();
            if (input.matches(regex)) {
                break;
            } else {
                System.out.println(errorMessage);
            }
        }
        return input;
    }
    
    
//    private void processCardPayment() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("카드 번호를 입력하세요 (예: 1234-5678-9012-3456): ");
//        String cardNumber = scanner.nextLine();
//
//        System.out.print("카드 유효기간 (MM/YY): ");
//        String cardExpiry = scanner.nextLine();
//
//        System.out.print("카드 비밀번호 앞 2자리: ");
//        String cardPassword = scanner.nextLine();
//
//        System.out.println("카드 결제가 완료되었습니다.");
//
//        // 예약 정보 저장
//        saveBooking();
//    }
    
    
    private void processPointPayment() throws IOException {
        int userId = Integer.parseInt(LoginSystem.getUserIndex());
        int totalPrice = ReservationHandler.getTotalPrice();

        // 포인트 결제 처리
        boolean success = pointPaymentService.processPointPayment(userId, totalPrice, createBooking());

        if (success) {
            System.out.println("포인트 결제가 완료되었습니다.");
            System.exit(0);
        } else {
            System.out.println("포인트가 부족합니다.");
            handleInsufficientPoints(userId, totalPrice);
        }
    }

    // 포인트 부족 처리 메서드
    private void handleInsufficientPoints(int userId, int totalPrice) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("포인트가 부족합니다. 다음 옵션 중 선택하세요:");
            System.out.println("[1] 포인트 충전");
            System.out.println("[2] 결제 취소");

            int choice = scanner.nextInt();

            if (choice == 1) {
                chargePoints(userId);
                System.out.println("포인트 충전이 완료되었습니다. 결제를 다시 시도합니다.");

                // 포인트 결제 재시도
                boolean success = pointPaymentService.processPointPayment(userId, totalPrice, createBooking());
                if (success) {
                    System.out.println("포인트 결제가 완료되었습니다.");
                    System.exit(0);
                    return;
                } else {
                    System.out.println("충전된 포인트로도 결제가 불가능합니다. 다시 시도해주세요.");
                }
            } else if (choice == 2) {
                System.out.println("결제가 취소되었습니다.");
                return;
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 포인트 충전 메서드
    private void chargePoints(int userId) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("충전할 포인트 금액을 입력하세요: ");
        int chargeAmount = scanner.nextInt();

        // 포인트 충전
        pointPaymentService.chargeUserPoints(userId, chargeAmount);
    }

    private Booking createBooking() throws NumberFormatException, IOException {
        int userId = Integer.parseInt(LoginSystem.getUserIndex());
        return new Booking(
            pointPaymentService.generateBookingIndex(),
            userId,
            ReservationHandler.getAccommodationId(),
            ReservationHandler.getCheckInDate(),
            ReservationHandler.getCheckOutDate(),
            ReservationHandler.getNumGuests(),
            ReservationHandler.getTotalPrice()
        );
    }

    private void saveBooking() throws IOException {
        Booking booking = createBooking();
        pointPaymentService.addBooking(booking);
        System.out.println("예약이 완료되었습니다. 예약 ID: " + booking.getBookingId());
    }


//    private BookingService bookingService = new BookingService();// BookingService 객체 초기화
//    PaymentProcessor paymentProcessor = new PaymentProcessor();
//    private List<Member> members = new ArrayList<>();
//    private List<Booking> bookings = new ArrayList<>();
//
//    private PaymentService pointPaymentService = new PaymentService();
//
//    public void startPointPayment(int accommodationId, String checkInDate, String checkOutDate, int numGuests, int totalPrice) throws IOException {
//        User user = pointPaymentService.getLoggedInUser();
//
//        if (user == null) {
//            System.out.println("로그인된 사용자 정보가 없습니다. 다시 로그인해주세요.");
//            return;
//        }
//
//        // 예약 생성
//        Booking booking = PaymentService.createBooking(user.getUserIndex(), accommodationId, checkInDate, checkOutDate, numGuests, totalPrice);
//
//        // 포인트 결제 처리
//        boolean success = pointPaymentService.processPointPayment(user, totalPrice, booking);
//
//        if (success) {
//            System.out.println("숙소 예약이 완료되었습니다.");
//            bookingService.addBooking(Integer.parseInt(LoginSystem.getUserIndex()),80, "2026-01-01", "2026-01-02", 2, 890000);
//        } else {
//            System.out.println("숙소 예약에 실패했습니다.");
//        }
//    }
//
//
//    public static void main(String[] args) throws NumberFormatException, IOException {
//        PaymentView paymentView = new PaymentView();
//        paymentView.start();
//    }
//
//    public void start() throws IOException {
//        Scanner scan = new Scanner(System.in);
//
//        boolean loop = true;
//
//        while (loop) {
//            displayMenu();
//
//            int choice = getUserChoice(scan);
//
//            switch (choice) {
//                case 1:
//                    processCardPayment(scan);
//                    break;
//                case 2:
//                    startPointPayment(80, "2026-01-01", "2026-01-02", 2, 890000 );
//                    break;
//                case 3:
//                    System.out.println("결제를 취소합니다. 감사합니다.");
//                    loop = false;
//                    break;
//                default:
//                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
//            }
//
//            System.out.println();
//        }
//
//        scan.close();
//    }
//
//    private void displayMenu() {
//        System.out.println("결제하실 방법을 선택해주세요.");
//        System.out.println("\n[1] 카드결제");
//        System.out.println("[2] 포인트 결제");
//        System.out.println("[3] 결제취소\n");
//        System.out.print("숫자 입력: ");
//    }
//
//    private int getUserChoice(Scanner scan) {
//        while (!scan.hasNextInt()) {
//            System.out.println("숫자를 입력해주세요.");
//            scan.next(); // 잘못된 입력 버퍼 비우기
//        }
//        int choice = scan.nextInt();
//        scan.nextLine(); // 버퍼 비우기
//        return choice;
//    }
//
//    private void processCardPayment(Scanner scan) {
//        System.out.print("카드사 입력: ");
//        String cardComp = scan.nextLine();
//
//        String cardNumber = getValidInput(scan, "카드 정보 입력 (양식: 0000-0000-0000-0000): ",
//                "\\d{4}-\\d{4}-\\d{4}-\\d{4}", "잘못된 입력입니다. 다시 입력해주세요. (양식: 0000-0000-0000-0000)");
//
//        String cardExp = getValidInput(scan, "카드 유효기한 입력 (양식: MM/YYYY): ",
//                "(0[1-9]|1[0-2])/(\\d{4})", "잘못된 입력입니다. 다시 입력해주세요. (양식: MM/YYYY)");
//
//        String cardPw = getValidInput(scan, "카드 비밀번호 앞 2자리: ",
//                "\\d{2}", "잘못된 입력입니다. 입력은 2자리 숫자여야 합니다.");
//
//        try {
//            System.out.println("카드 결제가 완료되었습니다.");
//
//            // 예약 정보 입력받기
//            System.out.println("\n예약 정보를 입력해주세요.");
//            System.out.print("사용자 ID: ");
//            int userId = scan.nextInt();
//
//            System.out.print("숙소 ID: ");
//            int accommodationId = scan.nextInt();
//
//            System.out.print("체크인 날짜 (YYYY-MM-DD): ");
//            String checkInDate = scan.next();
//
//            System.out.print("체크아웃 날짜 (YYYY-MM-DD): ");
//            String checkOutDate = scan.next();
//
//            System.out.print("숙박 인원: ");
//            int numGuests = scan.nextInt();
//
//            System.out.print("총 금액: ");
//            int totalPrice = scan.nextInt();
//
//            // 예약 추가
//            Booking newBooking = bookingService.addBooking(Integer.parseInt(LoginSystem.getUserIndex()), accommodationId, checkInDate, checkOutDate, numGuests, totalPrice);
//
//            System.out.println("예약이 완료되었습니다.");
//            System.out.println("예약 ID: " + newBooking.getBookingId());
//            System.out.println("즐거운 숙박되세요. 감사합니다.");
//            saveBooking();
//        } catch (Exception e) {
//            System.out.println("결제 실패: " + e.getMessage());
//        }
//    }

//    private String getValidInput(Scanner scan, String prompt, String regex, String errorMessage) {
//        System.out.print(prompt);
//        String input;
//        while (true) {
//            input = scan.nextLine();
//            if (input.matches(regex)) {
//                break;
//            } else {
//                System.out.println(errorMessage);
//            }
//        }
//        return input;
//    }
}
