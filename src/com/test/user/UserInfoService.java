package com.test.user;

import java.util.List;

public class UserInfoService {

    private UserService userService = new UserService();

    // 사용자 정보 수정
    public boolean updateUserInfo(String userId, String newUserName, String newEmail, String newPhoneNum) {
        List<User> users = userService.readMemberFile();
        for (User user : users) {
            if (user.getUserName().equals(userId)) {
                user.setUserName(newUserName);
                user.setUserEmail(newEmail);
                user.setUserPhone(newPhoneNum);
                userService.readMemberFile();
                return true;
            }
        }
        return false;
    }
}
