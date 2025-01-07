package com.test.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAuthService {
    private static final String PATH = "C:\\class\\code\\project\\accomodation-booking\\data\\member.txt";

    // 파일에서 사용자 데이터 읽기
    private List<User> readDataFromFile() {
        List<User> userList = new ArrayList<>();
        File file = new File(PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("■");
                if (data.length == 6) {
                    String userId = data[1];
                    String userPassword = data[2];
                    String userName = data[3];
                    String userEmail = data[4];
                    String userPhoneNum = data[5];
                    userList.add(new User(userId, userPassword, userName, userEmail, userPhoneNum, 0));
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
        }

        return userList;
    }

    // 사용자 데이터 파일에 쓰기
    private void writeDataToFile(List<User> userList) {
        File file = new File(PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int index = 1;
            for (User user : userList) {
                String line = String.join("■",
                        String.valueOf(index++),
                        user.getUserId(),
                        user.getUserPassword(),
                        user.getUserName(),
                        user.getUserEmail(),
                        user.getUserPhoneNum());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일 쓰기 중 오류 발생: " + e.getMessage());
        }
    }

    // 사용자 비밀번호 변경
    public boolean updatePassword(String userId, String oldPassword, String newPassword) {
        List<User> userList = readDataFromFile();
        boolean isUpdated = false;

        for (User user : userList) {
            if (user.getUserId().equals(userId) && user.getUserPassword().equals(oldPassword)) {
                user.setUserPassword(newPassword);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            writeDataToFile(userList);
            System.out.println("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            System.out.println("사용자 ID 또는 기존 비밀번호가 올바르지 않습니다.");
        }

        return isUpdated;
    }

    // 사용자 삭제
    public boolean deleteUser(String userId) {
        List<User> userList = readDataFromFile();
        boolean isDeleted = userList.removeIf(user -> user.getUserId().equals(userId));

        if (isDeleted) {
            writeDataToFile(userList);
            System.out.println("사용자가 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("해당 ID를 가진 사용자를 찾을 수 없습니다.");
        }

        return isDeleted;
    }
}