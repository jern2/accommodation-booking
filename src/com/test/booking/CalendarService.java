package com.test.booking;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CalendarService {
	public void showCalendar(int accommodationId, int year, int month, BookingService bookingService) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        List<LocalDate> bookedDates = bookingService.getBookedDatesByAccommodationId(accommodationId);

        System.out.println("=== " + year + "년 " + month + "월 ===");
        System.out.println("일\t월\t화\t수\t목\t금\t토");

        // 첫 날 요일에 따라 공백 출력
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1(월) ~ 7(일)
        for (int i = 1; i < dayOfWeek; i++) {
            System.out.print("\t");
        }

        // 날짜 출력
        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            if (bookedDates.contains(date)) {
                // 예약된 날짜는 대괄호로 표시
                System.out.print("[" + date.getDayOfMonth() + "]\t");
            } else {
                // 예약 가능한 날짜는 일반 숫자로 표시
                System.out.print(date.getDayOfMonth() + "\t");
            }

            // 줄 바꿈 처리 (토요일 다음에 줄 바꿈)
            if (date.getDayOfWeek().getValue() == 7) {
                System.out.println();
            }
        }

        System.out.println(); // 마지막 줄 바꿈
    }
}
