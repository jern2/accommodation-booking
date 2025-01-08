package com.test.payment;
import java.io.*;
import java.util.*;

public class MemberRepository {
    public static List<Member> loadMembers(String filePath) {
        List<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("■");
                if (data.length == 7) {  // 필드가 7개인지 확인
                    int id = Integer.parseInt(data[0]);
                    String username = data[1];
                    String password = data[2];  // 추가됨
                    String name = data[3];
                    String email = data[4];
                    String phone = data[5];
                    double balance = Double.parseDouble(data[6]);

                    members.add(new Member(id, username, password, name, email, phone, balance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public static Member getMemberById(int userId) {
        String filePath = "data\\members.txt";
        List<Member> members = loadMembers(filePath);
        return members.stream()
                .filter(member -> member.getId() == userId)
                .findFirst()
                .orElse(null);
    }
}