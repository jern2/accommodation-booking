package com.test.user;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private UserService userService = new UserService();
    private UserAuthService userAuthService = new UserAuthService();
    private UserInfoService userInfoService = new UserInfoService();
    private MyPageService myPageService = new MyPageService(userService);
    
    Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== 숙소 예약 프로그램 ===");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 종료");
            System.out.print("선택: ");
            
            int sel = -1;
            try {
                sel = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void registerUser() {
        System.out.println("\n=== 회원가입 ===");
        System.out.print("아이디: ");
        String userId = scanner.nextLine();
        System.out.print("비밀번호: ");
        String userPassword = scanner.nextLine();
        System.out.print("이름: ");
        String userName = scanner.nextLine();
        System.out.print("이메일: ");
        String userEmail = scanner.nextLine();
        System.out.print("전화번호: ");
        String userPhoneNum = scanner.nextLine();

        // 새로운 사용자 생성
        User newUser = new User(0, userId, userPassword, userName, userEmail, userPhoneNum, 0);

        // 기존 사용자 목록 읽기
        List<User> userList = userService.readMemberFile();

        // 새로운 사용자 추가
        userList.add(newUser);

        // 업데이트된 사용자 목록을 파일에 저장
        userService.writeMemberFile(userList);

        System.out.println("회원가입이 완료되었습니다.");
    }

    private void login() {
        System.out.println("\n=== 로그인 ===");
        System.out.print("아이디: ");
        String userId = scanner.nextLine();
        System.out.print("비밀번호: ");
        String userPassword = scanner.nextLine();

        List<User> userList = userService.readMemberFile();
        User user = userList.stream()
                            .filter(u -> u.getUserId().equals(userId) && u.getUserPassword().equals(userPassword))
                            .findFirst()
                            .orElse(null);

        if (user != null) {
            System.out.println("로그인 성공!");
            memberMenu(user);
        } else {
            System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    private void memberMenu(User user) {
        while (true) {

			System.out.println("                     ┏━━━━━━━━━━┓");
			System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃ 회원메뉴 ┃━━━━━━━━━━━━━━━━━━━┓");
			System.out.println("┃                    ┗━━━━━━━━━━┛                   ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
			System.out.println("┃ ┃[1] 마이페이지        ┃┃[2] 숙소예약          ┃  ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
			System.out.println("┃ ┃[3] 로그아웃          ┃┃[4] 프로그램 종료     ┃  ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			System.out.print("선택: ");
			
            int sel = -1;
            try {
                sel = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                    myPage(user);
                    break;
                case 2:
                    System.out.println("숙소 예약 기능은 아직 구현되지 않았습니다.");
                    break;
                case 3:
    				System.out.println("┏━━━━━━━━━━━━━┓");
    				System.out.println("┃로그아웃 성공┃");
    				System.out.println("┗━━━━━━━━━━━━━┛");
                    return;
                case 4:
    				System.out.println("┏━━━━━━━━━━━━━┓");
    				System.out.println("┃프로그램 종료┃");
    				System.out.println("┗━━━━━━━━━━━━━┛");
                    System.exit(0);
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void myPage(User user) {
        while (true) {
            System.out.println("\n=== 마이페이지 ===");
            System.out.println("1. 예약한 숙소 확인");
            System.out.println("2. 포인트 조회/충전");
            System.out.println("3. 회원정보 수정");
            System.out.println("4. 회원탈퇴");
            System.out.println("5. 뒤로가기");
            System.out.print("선택: ");

            int sel = -1;
            try {
                sel = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                    System.out.println("예약한 숙소 확인 기능은 아직 구현되지 않았습니다.");
                    break;
                case 2:
                    pointManagement(user);
                    break;
                case 3:
                    updateUserInfo(user);
                    break;
                case 4:
                    if (userAuthService.deleteUser(user.getUserPassword())) {
                        System.out.println("회원탈퇴가 완료되었습니다.");
                        return;
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void pointManagement(User user) {
        while (true) {
            System.out.println("\n=== 포인트 관리 ===");
            System.out.println("1. 포인트 조회");
            System.out.println("2. 포인트 충전");
            System.out.println("3. 뒤로가기");
            System.out.print("선택: ");

            int sel = -1;
            try {
                sel = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                    int points = myPageService.getUserPoints(user.getUserId());
                    System.out.println("현재 포인트: " + points);
                    break;
                case 2:
                    System.out.print("충전할 포인트 금액: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine(); // 버퍼 비우기
                    myPageService.chargePoints(user.getUserId(), amount);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void updateUserInfo(User user) {
        System.out.println("\n=== 회원정보 수정 ===");
        System.out.print("새 이름: ");
        String newName = scanner.nextLine();
        System.out.print("새 이메일: ");
        String newEmail = scanner.nextLine();
        System.out.print("새 전화번호: ");
        String newPhoneNum = scanner.nextLine();

        if (userInfoService.updateUserInfo(user.getUserId(), newName, newEmail, newPhoneNum)) {
            System.out.println("회원정보가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("회원정보 수정에 실패했습니다.");
        }
    }

    public static void main(String[] args) {
        UserView userView = new UserView();
        userView.start();
    }
}
