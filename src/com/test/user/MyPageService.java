package com.test.user;

import java.util.List;

public class MyPageService extends UserService {

    private UserService userService;

	public MyPageService(UserService userService) {
        this.userService = userService;
    }

    // 사용자 회원가입
	public boolean registerUser(String userId, String userPassword, String userName, String userEmail, String userPhone) {
	    List<User> userList = readMemberFile();  // 파일에서 사용자 목록 읽어오기
	    
	    // 동일한 ID가 이미 존재하는지 확인
	    for (User user : userList) {
	        if (user.getUserId().equals(userId)) {
	            System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
	            return false;  // 동일한 아이디가 존재하면 회원가입 진행하지 않음
	        }
	    }

	    // 새 userIndex 할당
	    int newUserIndex = getNextUserIndex(userList);  // 가장 큰 userIndex에 1을 더하여 새 인덱스 생성
	    
	    // 새 사용자 객체 생성
	    User newUser = new User(newUserIndex, userId, userPassword, userName, userEmail, userPhone, 0); // 기본 포인트 0
	    
	    // 사용자 목록에 추가
	    userList.add(newUser);
	    
	    // 사용자 목록을 파일에 저장
	    writeMemberFile(userList);  // 사용자 목록을 파일에 덮어쓰기
	    
	    System.out.println("회원가입이 완료되었습니다.");
	    return true;  // 회원가입 성공
	}

    // 사용자 포인트 조회
    public int getUserPoints(String userId) {
        List<User> userList = readMemberFile();

        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                return user.getUserPoints();  // 포인트 반환
            }
        }

        System.out.println("사용자를 찾을 수 없습니다.");
        return 0;
    }

    // 포인트 충전
    public void chargePoints(String userId, int userPoints) {
        List<User> userList = readMemberFile();
        boolean isUpdated = false;

        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                int updatedPoints = user.getUserPoints() + userPoints;
                user.setUserPoints(updatedPoints);

                isUpdated = true;
                System.out.println(userPoints + " 포인트가 충전되었습니다. 현재 포인트: " + updatedPoints);
                break;
            }
        }

        if (isUpdated) {
            writeMemberFile(userList);
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
        }
    }

    // 사용자 정보 수정
    public boolean updateUserInfo(String userId, String newUserName, String newUserEmail, String newUserPhoneNum) {
        List<User> userList = readMemberFile();
        boolean isUpdated = false;

        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                user.setUserName(newUserName);
                user.setUserEmail(newUserEmail);
                user.setUserPhone(newUserPhoneNum);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            writeMemberFile(userList);
            System.out.println("사용자 정보가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("해당 ID를 가진 사용자를 찾을 수 없습니다.");
        }

        return isUpdated;
    }

    // 회원 탈퇴
    public boolean deleteUser(String userPassword) {
        List<User> userList = readMemberFile();
        boolean isDeleted = userList.removeIf(user -> user.getUserPassword().equals(userPassword)); // 비밀번호 확인

        if (isDeleted) {
            writeMemberFile(userList);
            System.out.println("사용자가 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("비밀번호가 올바르지 않습니다.");
        }

        return isDeleted;
    }
}
