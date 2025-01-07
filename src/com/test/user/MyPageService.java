package com.test.user;

public class MyPageService {
    private UserService userService;

    public MyPageService(UserService userService) {
        this.userService = userService;
    }

    // 포인트 충전
    public void chargePoints(String userId, int userPoints) {
        User user = userService.findUserById(userId);
        if (user != null) {
            user.setUserPoints(user.getUserPoints() + userPoints);
            if (userService.updateUser(user)) {
                System.out.println(userPoints + " 포인트가 충전되었습니다.");
            } else {
                System.out.println("포인트 충전 중 오류가 발생했습니다.");
            }
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
        }
    }

    // 사용자 포인트 조회
    public int getUserPoints(String userId) {
        User user = userService.findUserById(userId);
        if (user != null) {
            return user.getUserPoints();
        }
        System.out.println("사용자를 찾을 수 없습니다.");
        return 0;
    }
}