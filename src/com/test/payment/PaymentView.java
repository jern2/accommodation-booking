package com.test.payment;

import java.math.BigDecimal;
import java.util.Scanner;

public class PaymentView {
	public static void main(String[] args) {

		PaymentService paymentService = new PaymentService();
		
		Scanner scan = new Scanner(System.in);
		int num = 0;
		boolean loop = true;
		
		 while (loop) {
		System.out.println("결제하실 방법을 선택해주세요.");
		System.out.println("1. 카드결제");
		System.out.println("2. 쌍용머니 결제");
		System.out.println("3. 결제취소");

		System.out.print("숫자 입력: ");
		scan.nextInt();
		
		switch (num) {
        case 1: // 카드결제
            System.out.print("카드사 입력: ");
            String cardcomp = scan.nextLine();
            
            System.out.print("카드 정보 입력 (양식: 0000-0000-0000-0000): ");
            
            String cardNumber;
            while(true) {
            	cardNumber = scan.nextLine();
            
            if (cardNumber.matches("\\d{4}-\\d{4}-\\d{4}")) {
                break; 
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요. (양식: 0000-0000-0000-0000)");
                }
            }
         
            String cardExp;
            while (true) {
                System.out.print("카드 유효기한 입력 (양식: 12/2024): ");
                cardExp = scan.nextLine();

          
                if (cardExp.matches("(0[1-9]|1[0-2])/(\\d{4})")) {
                    break; 
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요. (양식: 12/2024)");
                }
            }
            
            String cardPw;
            while (true) {
                System.out.print("카드 비밀번호 앞 2자리: ");
                cardPw = scan.nextLine();

          
                if (cardPw.matches("^\\\\d{2}$")) {
                    break; 
                } else {
                    System.out.println("잘못된 입력입니다. 입력은 2자리 숫자여야 합니다.");
                }
            }
            
   

//            try {
//                Payment cardPayment = paymentService.processPayment("card", cardAmount, cardInfo);
//                System.out.println("결제 성공: " + cardPayment);
//            } catch (Exception e) {
//                System.out.println("결제 실패: " + e.getMessage());
//            }
//            break;
//
//        case 2: // 쌍용머니 결제
//            System.out.print("결제 금액 입력: ");
//            BigDecimal pointsAmount = new BigDecimal(scan.nextLine());
//
//            try {
//                Payment pointsPayment = paymentService.processPayment("points", pointsAmount, null);
//                System.out.println("결제 성공: " + pointsPayment);
//            } catch (Exception e) {
//                System.out.println("결제 실패: " + e.getMessage());
//            }
//            break;
//
//        case 3: // 결제취소
//            System.out.println("결제를 취소합니다. 감사합니다.");
//            loop = false; // 루프 종료
//            break;
//
//        default: // 잘못된 입력 처리
//            System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
//    }

    System.out.println(); // 빈 줄 추가
}

scan.close(); // Scanner 닫기
		

		

		



	    }
    }
}

