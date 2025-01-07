package com.test.util;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoginSystem {

    private static final String LOGIN_FILE = ".\\data\\loginUser.txt"; //윈도우 환경
    private static final String USER_FILE = ".\\data\\members.txt"; // 윈도우 환경
//    private static final String LOGIN_FILE = "./data/loginUser.txt"; //맥 환경
//    private static final String USER_FILE = "./data/members.txt";  // 맥 환경


    // 로그인
    public static void login(String userId, String password) throws IOException {
        File file = new File(USER_FILE);

        // 유저 파일이 없으면 오류
        if (!file.exists()) {
            System.out.println("회원 정보가 없습니다. 먼저 회원가입을 해주세요.");
            return;
        }

        // 아이디와 비밀번호 검증
        boolean isValid = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                List<List<String>> users = FileUtil.readAndSplitFile(USER_FILE, "■");
                for (List<String> user : users) {
                    if (user.get(1).equals(userId) && user.get(2).equals(password)) {
                        isValid = true;
                        break;
                    }
                }

            }
        }

        if (isValid) {
            // 로그인 상태 저장
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE))) {
                writer.write("userID=" + userId);
                writer.newLine();
                writer.write("loginTime=" + java.time.LocalDateTime.now());
            }
            System.out.println("로그인 성공: " + userId);
        } else {
            System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    // 로그아웃
    public static void logout() {
        File file = new File(LOGIN_FILE);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("로그아웃 성공.");
            } else {
                System.out.println("로그아웃 실패: 파일을 삭제할 수 없습니다.");
            }
        } else {
            System.out.println("현재 로그인 상태가 아닙니다.");
        }
    }

    public static void main(String[] args) throws IOException {
        // 테스트
        login("0zngmjhx", "at7hgbjkwrsw");  // 로그인
        logout();          // 로그아웃
    }
}


