package com.test.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class ValidationUtil {


    // 날짜 형식 검증 메서드
    public static boolean isValidDate(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateRegex);
    }

    // 체크인 날짜와 체크아웃 날짜 비교
    public static boolean isCheckOutDateAfterCheckIn(String checkInDate, String checkOutDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
            LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);

            // 체크아웃 날짜가 체크인 날짜보다 이후인지 확인
            return checkOut.isAfter(checkIn);
        } catch (DateTimeParseException e) {
            return false; // 날짜 파싱 오류 시 false 반환
        }
    }

    // 과거 날짜인지 확인
    public static boolean isDateInPast(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();

            return inputDate.isBefore(today);
        } catch (DateTimeParseException e) {
            return true; // 잘못된 형식은 과거로 간주
        }
    }
    
    //미래 날짜인지 확인 
    public static boolean isDateInFuture(String date) {
    	try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();

            return inputDate.isAfter(today);
        } catch (DateTimeParseException e) {
            return true; // 잘못된 형식은 미래로 간주
        }
    }
    

    // 두 날짜 사이의 일수 계산
    public static int calculateDaysBetween(String startDate, String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            return (int) ChronoUnit.DAYS.between(start, end); // 두 날짜 사이의 일수 계산
        } catch (DateTimeParseException e) {
            return -1; // 잘못된 형식일 경우 -1 반환
        }
    }

    // 총 금액 계산 (숙박 기간 * 1박 요금)
    public static int calculateTotalPrice(int stayDuration, int pricePerNight) {
        return stayDuration * pricePerNight;
    }

    // 최소 숙박 기간 검증 (1박 이상)
    public static boolean isMinimumStayValid(long stayDuration) {
        return stayDuration >= 1; // 최소 1박 이상
    }
    
    //
}


