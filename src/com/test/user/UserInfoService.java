package com.test.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoService {
	
    private static final String PATH = "C:\\class\\code\\project\\accomodation-booking\\data\\member.txt";

    // 파일에서 데이터 읽기
    public List<User> readDataFromFile() {
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

    // 수정된 데이터 파일에 쓰기
    public void writeDataToFile(List<User> userList) {
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

    // 사용자 정보 수정
    public boolean updateUserInfo(String userId, String newUserName, String newUserEmail, String newUserPhoneNum) {
        List<User> userList = readDataFromFile();
        boolean isUpdated = false;

        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                user.setUserName(newUserName);
                user.setUserEmail(newUserEmail);
                user.setUserPhoneNum(newUserPhoneNum);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            writeDataToFile(userList);
            System.out.println("사용자 정보가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("해당 ID를 가진 사용자를 찾을 수 없습니다.");
        }

        return isUpdated;
    }
}