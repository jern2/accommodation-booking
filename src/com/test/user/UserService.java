package com.test.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserService {
    private static final String PATH = "C:\\class\\code\\project\\accomodation-booking\\data\\member.txt";

    // 사용자 데이터를 파일에서 읽어오기
    public List<User> readUsersFromFile() {
        List<User> userList = new ArrayList<>();
        File file = new File(PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("■");
                if (data.length == 7) {
                    String userId = data[1];
                    String userPassword = data[2];
                    String userName = data[3];
                    String userEmail = data[4];
                    String userPhoneNum = data[5];
                    int userPoints = Integer.parseInt(data[6]);  // 포인트 값 추가
                    userList.add(new User(userId, userPassword, userName, userEmail, userPhoneNum, userPoints));
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }

        return userList;
    }

    // 수정된 사용자 데이터를 파일에 저장하기
    public void writeUsersToFile(List<User> userList) {
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
                        user.getUserPhoneNum(),
                        String.valueOf(user.getUserPoints()));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일 쓰기 오류: " + e.getMessage());
        }
    }

    // 사용자 정보 수정
    public boolean updateUser(User user) {
        List<User> userList = readUsersFromFile();
        boolean isUpdated = false;

        for (User u : userList) {
            if (u.getUserId().equals(user.getUserId())) {
                u.setUserPoints(user.getUserPoints());  // 포인트 업데이트
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            writeUsersToFile(userList);
            System.out.println("사용자 정보가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("해당 ID의 사용자를 찾을 수 없습니다.");
        }

        return isUpdated;
    }

    public User findUserById(String userId) {
        List<User> userList = readUsersFromFile();
        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}