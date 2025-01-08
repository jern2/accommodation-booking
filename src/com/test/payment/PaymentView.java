package com.test.payment;

import java.io.IOException;
import java.util.*;


public class PaymentView {
    public static void main(String[] args) throws NumberFormatException, IOException {
        Scanner scan = new Scanner(System.in);
        
        String membersFile = "data\\members.txt";
        String bookingFile = "data\\booking_list.txt";

        List<Member> members = MemberRepository.loadMembers(membersFile);
        List<Booking> bookings = BookingRepository.loadBookings(bookingFile);

		PaymentService paymentService = new PaymentService();
		PaymentProcessor paymentProcessor = new PaymentProcessor();
        
        boolean loop = true;

        while (loop) {
            System.out.println("결제하실 방법을 선택해주세요.");
            System.out.println("\n");
            System.out.println("[1] 카드결제");
            System.out.println("[2] 쌍용머니 결제");
            System.out.println("[3] 결제취소");
            System.out.println("\n");
            System.out.print("숫자 입력: ");
            int num = scan.nextInt();
            scan.nextLine(); // 버퍼 비우기

            switch (num) {
                case 1: // 카드결제
                    System.out.print("카드사 입력: ");
                    String cardComp = scan.nextLine();

                    System.out.print("카드 정보 입력 (양식: 0000-0000-0000-0000): ");
                    String cardNumber;
                    while (true) {
                        cardNumber = scan.nextLine();
                        if (cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
                            break;
                        } else {
                            System.out.println("잘못된 입력입니다. 다시 입력해주세요. (양식: 0000-0000-0000-0000)");
                        }
                    }

                    System.out.print("카드 유효기한 입력 (양식: MM/YYYY): ");
                    String cardExp;
                    while (true) {
                        cardExp = scan.nextLine();
                        if (cardExp.matches("(0[1-9]|1[0-2])/(\\d{4})")) {
                            break;
                        } else {
                            System.out.println("잘못된 입력입니다. 다시 입력해주세요. (양식: MM/YYYY)");
                        }
                    }

                    System.out.print("카드 비밀번호 앞 2자리: ");
                    String cardPw;
                    while (true) {
                        cardPw = scan.nextLine();
                        if (cardPw.matches("\\d{2}")) {
                            break;
                        } else {
                            System.out.println("잘못된 입력입니다. 입력은 2자리 숫자여야 합니다.");
                        }
                    }

                    try {
                        Payment cardPayment = paymentService.processCardPayment(cardComp, cardNumber, cardExp, cardPw);
                        System.out.println("카드 결제가 완료되었습니다.");
                        System.out.println("즐거운 숙박되세요. 감사합니다.");
                        System.exit(0);
                    } catch (Exception e) {
                        System.out.println("결제 실패");
                    }
                    break;

                case 2: // 쌍용머니 결제
                	paymentProcessor.processPointsPayment(scan, members, bookings);
                    break;

                case 3: // 결제취소
                    System.out.println("결제를 취소합니다. 감사합니다.");
                    loop = false; // 루프 종료
                    break;

                default: // 잘못된 입력 처리
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }

            System.out.println(); 
        }

        scan.close(); 
    }
}
