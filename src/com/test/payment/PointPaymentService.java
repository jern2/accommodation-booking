package com.test.payment;

import java.io.*;
import java.util.*;
import com.test.booking.Booking;

public class PointPaymentService {

    private static final String MEMBERS_FILE = "./data/members.txt";
    private static final String BOOKING_LIST_FILE = "./data/booking_list.txt";

    public boolean processPointPayment(int userId, int totalPrice, Booking booking) throws IOException {
        List<Member> members = loadMembers();
        for (Member member : members) {
            if (member.getId() == userId) {
                if (member.getBalance() >= totalPrice) {
                    member.setBalance(member.getBalance() - totalPrice);
                    saveMembers(members);
                    addBooking(booking);
                    return true;
                }
            }
        }
        return false;
    }

    // 포인트 충전 메서드
    public void chargeUserPoints(int userId, int chargeAmount) throws IOException {
        List<Member> members = loadMembers();

        for (Member member : members) {
            if (member.getId() == userId) {
                // 포인트 충전
                member.setBalance(member.getBalance() + chargeAmount);
                saveMembers(members);
                System.out.println("포인트가 성공적으로 충전되었습니다. 현재 포인트: " + member.getBalance() + "P");
                return;
            }
        }

        System.out.println("사용자를 찾을 수 없습니다.");
    }

    public List<Member> loadMembers() throws IOException {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("■");
                members.add(new Member(
                        Integer.parseInt(parts[0]), parts[1], parts[2], parts[3],
                        parts[4], parts[5], Integer.parseInt(parts[6])
                ));
            }
        }
        return members;
    }

    public void saveMembers(List<Member> members) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                writer.write(member.toFileFormat());
                writer.newLine();
            }
        }
    }

    public void addBooking(Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_LIST_FILE, true))) {
            writer.write(booking.toFileFormat());
            writer.newLine();
        }
    }

    public int generateBookingIndex() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKING_LIST_FILE))) {
            String line;
            int maxIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("■");
                maxIndex = Math.max(maxIndex, Integer.parseInt(parts[0]));
            }
            return maxIndex + 1;
        } catch (IOException e) {
            return 1;
        }
    }
}
