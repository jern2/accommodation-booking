package com.test.accommodation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.test.booking.ReservationHandler;
import com.test.payment.PaymentView;

public class AccommodationBooking {

	 public static void main(String[] args) {
	        String bookingFilePath = "./data/booking_list.txt"; // 예약 리스트 파일 경로
	       //int accommodationId2 = randomList();
	        //int selectedAccommodationId = randomList(); // 선택된 숙소의 ID (예: 4번 숙소)
	        int selectedAccommodationId = randomList(); 
	        Set<LocalDate> reservedDates = new HashSet<>();
	        
	        
	        
	        // 파일에서 예약 정보 읽기
	        loadBookingData(bookingFilePath, selectedAccommodationId, reservedDates);

	        // 체크인 및 체크아웃 날짜 선택
	        LocalDate checkInDate = selectCheckInDate(reservedDates);
	        LocalDate checkOutDate = selectCheckOutDate(checkInDate, reservedDates);
	        
	        int guestNum = -1;

	        System.out.println("\n[선택한 예약 정보]");
	        System.out.println("체크인 날짜: " + checkInDate);
	        System.out.println("체크아웃 날짜: " + checkOutDate);

	        // 상세 입력 여부 확인
	        Scanner scanner = new Scanner(System.in);
	        while (true) {
	            System.out.println("\n이대로 예약을 진행하시겠습니까? \n1. 네\n2. 아니요\n입력: ");
	            String input = scanner.nextLine().trim().toUpperCase();
	            if (input.equals("1")) {
	                System.out.println("상세 입력 페이지로 이동합니다.");
	                break;
	            } else if (input.equals("2")) {
	                System.out.println("예약을 종료합니다.");
	                break;
	            } else {
	                System.out.println("유효하지 않은 입력입니다. 1 또는 2을 입력해주세요.");
	            }
	        }
	    }

	    private static int randomList() {
			// TODO Auto-generated method stub
			return 0;
		}

		private static void loadBookingData(String filePath, int accommodationId, Set<LocalDate> reservedDates) {
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split("■");
	                if (parts.length >= 7) {
	                    int fileAccommodationId = Integer.parseInt(parts[2].trim());
	                    LocalDate checkInDate = LocalDate.parse(parts[3].trim(), dateFormatter);
	                    LocalDate checkOutDate = LocalDate.parse(parts[4].trim(), dateFormatter);

	                    if (fileAccommodationId == accommodationId) {
	                        LocalDate currentDate = checkInDate;
	                        while (!currentDate.isAfter(checkOutDate.minusDays(1))) {
	                            reservedDates.add(currentDate);
	                            currentDate = currentDate.plusDays(1);
	                        }
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading booking data: " + e.getMessage());
	        }
	    }

	    private static LocalDate selectCheckInDate(Set<LocalDate> reservedDates) {
	        LocalDate today = LocalDate.now();
	        LocalDate selectedDate;

	        while (true) {
	            System.out.println("체크인 날짜를 선택하세요:");
	            selectedDate = selectDateFromCalendar(reservedDates);

	            if (selectedDate.isBefore(today)) {
	                System.out.println("※ 체크인 날짜는 오늘 이후로만 선택할 수 있습니다. 다시 선택해주세요.");
	            } else if (reservedDates.contains(selectedDate)) {
	                System.out.println("※ 선택한 날짜는 이미 예약되어 있습니다. 다시 선택해주세요.");
	            } else {
	                break;
	            }
	        }
	        return selectedDate;
	    }

	    private static LocalDate selectCheckOutDate(LocalDate checkInDate, Set<LocalDate> reservedDates) {
	        LocalDate selectedDate;

	        while (true) {
	        	System.out.println("\n현재 선택된 체크인 날짜: " + checkInDate);
	            System.out.println("체크아웃 날짜를 선택하세요:");
	            selectedDate = selectDateFromCalendar(reservedDates);

	            final LocalDate finalSelectedDate = selectedDate; // effectively final로 만들기 위해 새로운 변수에 저장

	            if (finalSelectedDate.isBefore(checkInDate.plusDays(1))) {
	                System.out.println("※ 체크아웃 날짜는 체크인 날짜 이후여야 합니다. 다시 선택해주세요.");
	            } else if (reservedDates.stream()
	                    .anyMatch(date -> !date.isBefore(checkInDate) && !date.isAfter(finalSelectedDate))) {
	                System.out.println("※ 체크아웃 날짜 사이에 예약된 날짜가 있습니다. 다시 선택해주세요.");
	            } else {
	                break;
	            }
	        }
	        return selectedDate;
	    }
	    
	    
	    private static int inputGuestNum() {
	        Scanner scanner = new Scanner(System.in);
	        int guestNum = -1;

	        while (true) {
	            System.out.print("숙박할 게스트 수를 입력하세요: ");
	            if (scanner.hasNextInt()) {
	                guestNum = scanner.nextInt();
	                if (guestNum > 0) {
	                    break; // 유효한 값 입력 시 종료
	                } else {
	                    System.out.println("게스트 수는 1명 이상이어야 합니다. 다시 입력해주세요.");
	                }
	            } else {
	                System.out.println("유효한 숫자를 입력해주세요.");
	                scanner.next(); // 잘못된 입력 제거
	            }
	        }
	        return guestNum;
	    }

	    private static LocalDate selectDateFromCalendar(Set<LocalDate> reservedDates) {
	        LocalDate today = LocalDate.now();
	        LocalDate calendarMonth = today.withDayOfMonth(1); // 달력의 첫날 설정
	        Scanner scanner = new Scanner(System.in);

	        LocalDate selectedDate;

	        while (true) {
	            System.out.println("\n====================================================================");
	            System.out.printf("                     %d년 %02d월\n", calendarMonth.getYear(), calendarMonth.getMonthValue());
	            System.out.println("====================================================================\n");
	            displayCalendar(calendarMonth, reservedDates);
	            System.out.println("\n====================================================================\n");

	            System.out.print("[다음 달 : + / 지난 달 : - / 날짜 선택 : 숫자] \n\n입력 : ");
	            String input = scanner.nextLine().trim();

	            if (input.equals("+")) {
	                calendarMonth = calendarMonth.plusMonths(1); // 다음 달로 이동
	            } else if (input.equals("-")) {
	                calendarMonth = calendarMonth.minusMonths(1); // 저번 달로 이동
	            } else if (input.matches("\\d{1,2}")) {
	                int day = Integer.parseInt(input);
	                try {
	                    selectedDate = calendarMonth.withDayOfMonth(day);
	                    return selectedDate;
	                } catch (DateTimeException e) {
	                    System.out.println("유효하지 않은 날짜입니다. 다시 선택해주세요.");
	                }
	            } else {
	                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
	            }
	        }
	    }

	    private static void displayCalendar(LocalDate calendarMonth, Set<LocalDate> reservedDates) {
	        LocalDate firstDay = calendarMonth.withDayOfMonth(1);
	        int firstDayOfWeek = firstDay.getDayOfWeek().getValue() % 7; // 첫날의 요일 (0=일, 6=토)
	        int daysInMonth = calendarMonth.lengthOfMonth();

	        System.out.println("[일]\t[월]\t[화]\t[수]\t[목]\t[금]\t[토]");

	        for (int i = 0; i < firstDayOfWeek; i++) {
	            System.out.print("\t");
	        }

	        for (int day = 1; day <= daysInMonth; day++) {
	            LocalDate currentDate = calendarMonth.withDayOfMonth(day);

	            if (reservedDates.contains(currentDate)) {
	                System.out.printf("%2d■\t", day); // 예약된 날짜
	            } else {
	                System.out.printf("%2d□\t", day); // 예약 가능 날짜
	            }

	            if ((day + firstDayOfWeek) % 7 == 0) {
	                System.out.println();
	            }
	        }
	        System.out.println();
	    }
	
	
	
	    public static void runBooking(Accommodation selectedAccommodation) {
	        String bookingFilePath = "./data/booking_list.txt";
	        Set<LocalDate> reservedDates = new HashSet<>();

	        // 예약 데이터 로드
	        loadBookingData(bookingFilePath, selectedAccommodation.getId(), reservedDates);

	        // 체크인 및 체크아웃 날짜 선택
	        LocalDate checkInDate = selectCheckInDate(reservedDates);
	        LocalDate checkOutDate = selectCheckOutDate(checkInDate, reservedDates);
	        
	        //게스트 수 입력
	        int guestNum = inputGuestNum();

	        System.out.println("\n[선택한 예약 정보]");
	        System.out.println("숙소 이름: " + selectedAccommodation.getAccommodationName());
	        System.out.println("체크인 날짜: " + checkInDate);
	        System.out.println("체크아웃 날짜: " + checkOutDate);

	        // 결제 창으로 이동
	        PaymentView paymentView = new PaymentView();
	        ReservationHandler.setReservationDetails(
	                selectedAccommodation.getId(),
	                checkInDate.toString(),
	                checkOutDate.toString(),
	                guestNum,
	                selectedAccommodation.getPrice()
	        );
	        paymentView.showPaymentOptions();
	    }
    
    public static void printFormattedNotice(String notice, int maxLength) {
        int start = 0;
        while (start < notice.length()) {
            // 현재 출력할 부분 계산
            int end = Math.min(start + maxLength, notice.length());
            String line = notice.substring(start, end);
            System.out.println(line);
            start = end; // 다음 부분으로 이동
        }
    }
}

