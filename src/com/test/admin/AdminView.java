package com.test.admin;

import java.util.Scanner;
import com.test.accommodation.AccommodationService;

import static com.test.util.SysoutUtil.banner;
import static com.test.util.SysoutUtil.nextpage;

public class AdminView {
    public static void main(String[] args) throws InterruptedException {
        AccommodationService accommodationService = new AccommodationService();
        AdminService adminService = new AdminService(accommodationService);
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n관리자 로그인");
        System.out.print(" 아이디: ");
        String id = scanner.next();
        System.out.print(" 비밀번호: ");
        String password = scanner.next();

        if (!adminService.login(id, password)) {
            System.out.println("\n⚠️로그인에 실패하였습니다.");
            Thread.sleep(2000);
            return;
        }

        System.out.println("\n✔️관리자 모드에 접속하였습니다.");
        Thread.sleep(2000);
        while (true) {

            nextpage();
            banner();
//        	System.out.print("\033[47m\033[30m");
            System.out.println("┃\t\t                      ┏━━━━━━━━━━━━━┓                    \t\t┃");
            System.out.println("┃\t\t ┏━━━━━━━━━━━━━━━━━━━━┃\t관리자 모드\t┃━━━━━━━━━━━━━━━━━━┓ \t\t┃");
            System.out.println("┃\t\t ┃                    ┗━━━━━━━━━━━━━┛                  ┃ \t\t┃");
            System.out.println("┃\t\t ┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃ \t\t┃");
            System.out.println("┃\t\t ┃ ┃[1] 숙소 추가\t\t\t  ┃┃[2] 숙소 수정 \t\t\t┃  ┃ \t\t┃");
            System.out.println("┃\t\t ┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃ \t\t┃");
            System.out.println("┃\t\t ┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃ \t\t┃");
            System.out.println("┃\t\t ┃ ┃[3] 숙소 삭제\t\t\t  ┃┃[4] 로그아웃\t\t\t\t┃  ┃ \t\t┃");
            System.out.println("┃\t\t ┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃ \t\t┃");
            System.out.println("┃\t\t ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛ \t\t┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//			System.out.print("\033[0m");
            System.out.println();
            System.out.print("\n✅선택: ");
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
                    System.out.println("✔️로그아웃되었습니다.");
                    return;
                default:
                    System.out.println("⚠️잘못된 입력입니다.");
            }
        }
    }
}

