package com.test;

import java.io.IOException;
import java.util.Scanner;

import com.test.admin.AdminView;
import com.test.booking.BookingView;
import com.test.payment.PaymentView;
import com.test.user.UserView;
import com.test.util.LoginSystem;

public class Main {
	
	Scanner scanner = new Scanner(System.in);
	
    public static void main(String[] args) throws IOException {
    	
    	 Scanner scanner = new Scanner(System.in);
         UserView userView = new UserView();
         AdminView adminView = new AdminView();
         BookingView bookingView = new BookingView();
         PaymentView paymentView = new PaymentView();
         LoginSystem loginSystem = new LoginSystem();

         while (true) {

             System.out.println("====== 숙소 예약 프로그램 ======");
             System.out.println("[1] 사용자 메뉴");
             System.out.println("[2] 관리자 메뉴");
             System.out.println("[3] 종료");
             System.out.print("선택: ");
             int choice = scanner.nextInt();
             scanner.nextLine(); // 버퍼 비우기
             
             
             switch (choice) {
                 case 1:
                     userView.start(); // UserView 진입
                     break;
                 case 2:
                     adminView.main(args); // AdminView 진입
                     break;
                 case 3:
                     System.out.println("프로그램을 종료합니다.");
                     System.exit(0);
                     break;
                 default:
                     System.out.println("잘못된 입력입니다. 다시 시도하세요.");
             }
         }
     }
 
    	
    	
    	
    	
    	
//    	UserView userview = new UserView();
//    	BookingView bookingView = new BookingView();
//        int loggedInUserId = Integer.parseInt(LoginSystem.getUserIndex());


    }


