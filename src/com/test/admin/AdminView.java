package com.test.admin;

import java.util.Scanner;
import com.test.accommodation.AccommodationService;

public class AdminView {
    public static void main(String[] args) {
        AccommodationService accommodationService = new AccommodationService();
        AdminService adminService = new AdminService(accommodationService);
        Scanner scanner = new Scanner(System.in);

        System.out.println("관리자 로그인");
        System.out.print("아이디: ");
        String id = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();

        if (!adminService.login(id, password)) {
            System.out.println("로그인에 실패하였습니다.");
            return;
        }

        System.out.println("관리자 모드에 접속하였습니다.");
        while (true) {
            System.out.println("1. 숙소 추가");
            System.out.println("2. 숙소 수정");
            System.out.println("3. 숙소 삭제");
            System.out.println("4. 로그아웃");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminService.addAccommodation();
                    break;
                case 2:
                    adminService.modifyAccommodation();
                    break;
                case 3:
                    adminService.deleteAccommodation();
                    break;
                case 4:
                    System.out.println("로그아웃되었습니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}

