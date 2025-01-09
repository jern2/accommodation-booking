package com.test.user;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.test.accommodation.AccommodationView;
import com.test.booking.BookingView;
import com.test.payment.Member;
import com.test.payment.PaymentProcessor;
import com.test.util.LoginSystem;

public class UserView {
	BookingView bookingView = new BookingView();
    private UserService userService = new UserService();
    private UserAuthService userAuthService = new UserAuthService();
    private UserInfoService userInfoService = new UserInfoService();
    private MyPageService myPageService = new MyPageService(userService);
    private AccommodationView accommodationView = new AccommodationView();
    private PaymentProcessor paymentProcessor = new PaymentProcessor();
    private LoginSystem loginSystem = new LoginSystem();
    
    List<Member> members = new ArrayList<>();
    
    Scanner scanner = new Scanner(System.in);

    public void start() throws IOException {
    	
            while (true) {
                System.out.println("              ┏━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┏━━━━━━━━━━━━━┃ 숙소 예약 프로그램 ┃━━━━━━━━━━━━━━┓");
                System.out.println("┃             ┗━━━━━━━━━━━━━━━━━━━━┛              ┃");
                System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.println("┃ ┃[1] 회원가입          ┃┃[2] 로그인          ┃  ┃");
                System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.println("┃ ┃[3] 아이디 찾기       ┃┃[4] 비밀번호 찾기   ┃  ┃");
                System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                        ┃");
                System.out.println("┃ ┃[5] 종료              ┃                        ┃");
                System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                        ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.print("선택: ");

                int sel = -1;
                try {
                    sel = scanner.nextInt();
                    scanner.nextLine(); // 버퍼 비우기
                } catch (InputMismatchException e) {
                    System.err.println("잘못된 입력입니다. 숫자를 입력해주세요.");
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
                        findAccount();
                        break;
                    case 4:
                        findPassword();
                        break;
                    case 5:
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.err.println("잘못된 입력입니다. 다시 시도하세요.");
                }
            }
        }
    	
        private void findAccount() {
            System.out.println("\n=== 계정 찾기 ===");
            System.out.print("이름: ");
            String userName = scanner.nextLine();
            System.out.print("이메일: ");
            String userEmail = scanner.nextLine();
            System.out.print("전화번호: ");
            String userPhone = scanner.nextLine();


            List<User> userList = userService.readMemberFile();
            User user = userList.stream()
                                .filter(u -> u.getUserName().equals(userName) 
                                		&& u.getUserEmail().equals(userEmail)
                                		&& u.getUserPhone().equals(userPhone))
                                .findFirst()
                                .orElse(null);

            if (user != null) {
                System.out.println("┏━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃ 계정 찾기 성공 ┃");
                System.out.println("┃ 아이디: " + user.getUserId() + " ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━┛");
            } else {
                System.err.println("입력한 정보와 일치하는 계정을 찾을 수 없습니다.");
            }
        }
        private void findPassword() {
            System.out.println("\n=== 비밀번호 찾기 ===");
            System.out.print("아이디: ");
            String userId = scanner.nextLine();
            System.out.print("이메일: ");
            String userEmail = scanner.nextLine();
            System.out.print("전화번호: ");
            String userPhone = scanner.nextLine();

            List<User> userList = userService.readMemberFile();
            User user = userList.stream()
                                .filter(u -> u.getUserId().equals(userId) 
                                		&& u.getUserEmail().equals(userEmail)
                                		&& u.getUserPhone().equals(userPhone))
                                .findFirst()
                                .orElse(null);

            if (user != null) {
                System.out.println("┏━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃ 비밀번호 찾기 성공  ┃");
                System.out.println("┃ 비밀번호: " + user.getUserPassword() + " ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━┛");
            } else {
                System.err.println("아이디 또는 이메일이 일치하지 않습니다.");
            }
        }
        public void registerUser() {
            System.out.println("\n=== 회원가입 ===");

            // 1. 아이디 입력 후 중복 검사
            System.out.print("아이디: ");
            String userId = scanner.nextLine();

            // 기존 사용자 목록 읽기
            List<User> userList = userService.readMemberFile();

            // 동일한 ID가 이미 존재하는지 확인
            for (User user : userList) {
                if (user.getUserId().equals(userId)) {
                    System.err.println("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
                    return; // 동일한 ID가 존재하면 메서드 종료
                }
            }

            // 2. 비밀번호 입력
            System.out.print("비밀번호: ");
            String userPassword = scanner.nextLine();

            // 3. 이름 입력
            System.out.print("이름: ");
            String userName = scanner.nextLine();

            // 4. 이메일 입력 후 형식 검사
            System.out.print("이메일: ");
            String userEmail = scanner.nextLine();
            
            if (!isValidEmail(userEmail)) {
                System.out.println("이메일 형식이 잘못되었습니다./n 이메일은 영문자와 숫자 조합으로 'ssangyong@paper.com' 형식이어야 합니다.");
                return; // 유효하지 않으면 메서드 종료
            }

            // 5. 전화번호 입력 후 형식 검사
            System.out.print("전화번호: ");
            String userPhoneNum = scanner.nextLine();
            
            if (!isValidPhoneNumber(userPhoneNum)) {
                System.out.println("전화번호 형식이 잘못되었습니다. 전화번호는 '010'으로 시작하며 8자리 숫자로 구성되어야 합니다.('-' 제외");
                return; // 유효하지 않으면 메서드 종료
            }

            // 6. 새로운 userIndex 계산
            int newUserIndex = userList.stream()
                .mapToInt(User::getUserIndex)
                .max()
                .orElse(0) + 1;

            // 7. 새로운 사용자 생성
            User newUser = new User(newUserIndex, userId, userPassword, userName, userEmail, userPhoneNum, 0);

            // 8. 새로운 사용자 추가
            userList.add(newUser);

            // 9. 업데이트된 사용자 목록을 파일에 저장
            userService.writeMemberFile(userList);

            System.out.println("회원가입이 완료되었습니다.\n");
        }

        // 이메일 유효성 검사
        private boolean isValidEmail(String email) {
            return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$");
        }

        // 전화번호 유효성 검사
        private boolean isValidPhoneNumber(String phoneNumber) {
            return phoneNumber.matches("^010\\d{8}$");
        }



    private void saveUsersToFile(List<User> users) {
        String filePath = ".\\data\\members.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // true로 append 모드 활성화
            for (User user : users) {
                // 각 사용자를 파일에 저장할 형식으로 변환
                String userLine = String.format("%d■%s■%s■%s■%s■%s■%d", 
                    user.getUserIndex(), 
                    user.getUserId(), 
                    user.getUserPassword(),
                    user.getUserName(), 
                    user.getUserEmail(), 
                    user.getUserPhone(),
                    user.getUserPoints());

                writer.write(userLine);
                writer.newLine(); // 줄 바꿈
            }
        } catch (IOException e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //로그인
    private void login() throws IOException {
        System.out.println("\n=== 로그인 ===");
        System.out.print("아이디: ");
        String userId = scanner.nextLine();
        System.out.print("비밀번호: ");
        String userPassword = scanner.nextLine();

        // LoginSystem의 login 메서드 호출
        LoginSystem.login(userId, userPassword);
        

//        // 로그인 성공 여부 확인
//        String loggedInUserIndex = LoginSystem.getUserIndex();
//        if (loggedInUserIndex != null) {
//            System.out.println("┏━━━━━━━━━━━━━┓");
//            System.out.println("┃ 로그인 성공 ┃");
//            System.out.println("┗━━━━━━━━━━━━━┛");
//
//            // memberMenu로 이동
//            User user = new User(
//                Integer.parseInt(loggedInUserIndex),
//                userId,
//                userPassword,
//                LoginSystem.getUserName(),
//                null,
//                null,
//                0
//            );
//            memberMenu(user);
//        } else {
//            System.err.println("로그인 실패: 다시 시도해주세요.");
//        }
    }


    public void memberMenu(User user) throws IOException {
    	int loggedInUserId = Integer.parseInt(LoginSystem.getUserIndex());
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
                System.err.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                    myPage(user);
                    break;
                case 2:
                    accommodationView.reservation();
                    break;
                case 3:
                    LoginSystem.logout();
    				System.out.println("┏━━━━━━━━━━━━━┓");
    				System.out.println("┃로그아웃 성공┃");
    				System.out.println("┗━━━━━━━━━━━━━┛");
                    return;
                case 4:
                    LoginSystem.logout();
    				System.out.println("┏━━━━━━━━━━━━━┓");
    				System.out.println("┃프로그램 종료┃");
    				System.out.println("┗━━━━━━━━━━━━━┛");
                    System.exit(0);
                default:
                    System.err.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void myPage(User user) throws IOException {
    	int loggedInUserId = Integer.parseInt(LoginSystem.getUserIndex());
        while (true) {

			System.out.println("                     ┏━━━━━━━━━━━━━┓");
			System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃  마이페이지 ┃━━━━━━━━━━━━━━━━━━┓");
			System.out.println("┃                    ┗━━━━━━━━━━━━━┛                  ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
			System.out.println("┃ ┃[1] 예약한 숙소 확인  ┃┃[2] 쌍용 머니 조회/충전 ┃  ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
			System.out.println("┃ ┃[3] 회원정보 수정     ┃┃[4] 회원탈퇴            ┃  ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                            ┃");
			System.out.println("┃ ┃[5] 뒤로가기          ┃                            ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                            ┃");
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			System.out.print("선택: ");
            
            int sel = -1;
            try {
                sel = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (InputMismatchException e) {
                System.err.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                	bookingView.showUserBookings(loggedInUserId);
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
                        System.out.println("안녕히 가세요.");
                        start();
                    }
                    break;
                case 5:
                    return;
                default:
                    System.err.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void pointManagement(User user) {
        while (true) {

			System.out.println("                     ┏━━━━━━━━━━━━━━━┓");
			System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃ 쌍용머니 관리 ┃━━━━━━━━━━━━━━━━┓");
			System.out.println("┃                    ┗━━━━━━━━━━━━━━━┛                ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓    ┃");
			System.out.println("┃ ┃[1] 쌍용머니 조회     ┃┃[2] 쌍용머니 충전     ┃    ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛    ┃");
			System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                            ┃");
			System.out.println("┃ ┃[3] 뒤로가기          ┃                            ┃");
			System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                            ┃");
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			System.out.print("선택: ");

            int sel = -1;
            try {
                sel = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (InputMismatchException e) {
                System.err.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine(); // 버퍼 비우기
                continue;
            }

            switch (sel) {
                case 1:
                    int points = myPageService.getUserPoints(user.getUserId());
                    System.out.printf("[현재 포인트: %,d원]", points);
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
                    System.err.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void updateUserInfo(User user) {
    	
        System.out.println("\n=== 회원정보 수정 ===");
        
        System.out.print("현재 비밀번호를 입력하세요: ");
        String currentPassword = scanner.nextLine();
        
        if (!user.getUserPassword().equals(currentPassword)) {
            System.out.println("현재 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
            return;  // 비밀번호가 일치하지 않으면 메서드 종료
        }
        
        System.out.print("새 비밀번호: ");
        String newPassword = scanner.nextLine();
        
        // 비밀번호 확인
        System.out.print("비밀번호를 한번 더 입력해주세요: ");
        String confirmPassword = scanner.nextLine();


        if (!newPassword.equals(confirmPassword)) {
            System.out.println("비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
            return;  // 비밀번호가 일치하지 않으면 메서드 종료
        }
        
        System.out.print("새 이름: ");
        String newName = scanner.nextLine();
        System.out.print("새 이메일: ");
        String newEmail = scanner.nextLine();
        System.out.print("새 전화번호: ");
        String newPhoneNum = scanner.nextLine();
        


        if (userInfoService.updateUserInfo(user.getUserId(), newPassword, newName, newEmail, newPhoneNum)) {
            System.out.println("회원정보가 성공적으로 수정되었습니다.");
            
            // 수정 후 최신 데이터를 읽어와서 새로운 User 객체로 업데이트
            List<User> updatedUserList = userService.readMemberFile();
            User updatedUser = updatedUserList.stream()
                                              .filter(u -> u.getUserId().equals(user.getUserId()))
                                              .findFirst()
                                              .orElse(user); // 갱신된 데이터를 가져오거나 기존 사용자 유지

            // user에 값 갱신
            user.setUserPassword(updatedUser.getUserPassword());
            user.setUserName(updatedUser.getUserName());
            user.setUserEmail(updatedUser.getUserEmail());
            user.setUserPhone(updatedUser.getUserPhone());
        } else {
            System.err.println("회원정보 수정에 실패했습니다.");
        }
    }

    public static void main(String[] args) throws IOException {
        UserView userView = new UserView();
        userView.start();
    }
}
