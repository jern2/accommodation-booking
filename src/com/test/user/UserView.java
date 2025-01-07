package com.test.user;

import java.util.Scanner;

public class UserView {
	
    private UserService userService;
    private MyPageService myPageService;

    public UserView(UserService userService, MyPageService myPageService) {
        this.userService = userService;
        this.myPageService = myPageService;
    }

    // 사용자 메뉴 표시
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== 사용자 메뉴 ===");
            System.out.println("1. 포인트 조회");
            System.out.println("2. 포인트 충전");
            System.out.println("3. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 버퍼 비우기

            switch (choice) {
                case 1:
                    viewUserPoints(scanner);
                    break;
                case 2:
                    chargeUserPoints(scanner);
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해 주세요.");
            }
        }
    }

    // 사용자 포인트 조회
    private void viewUserPoints(Scanner scanner) {
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.nextLine();

        int points = myPageService.getUserPoints(userId);
        if (points > 0) {
            System.out.println("현재 포인트: " + points);
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
        }
    }

    // 포인트 충전
    private void chargeUserPoints(Scanner scanner) {
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.nextLine();

        System.out.print("충전할 포인트를 입력하세요: ");
        int points = scanner.nextInt();
        scanner.nextLine();  // 버퍼 비우기

        myPageService.chargePoints(userId, points);
    }

    public static void main(String[] args) {
        // UserService와 MyPageService 객체 생성
        UserService userService = new UserService();
        MyPageService myPageService = new MyPageService(userService);

        // UserView 객체 생성 후 메뉴 실행
        UserView userView = new UserView(userService, myPageService);
        userView.displayMenu();
    }
}
