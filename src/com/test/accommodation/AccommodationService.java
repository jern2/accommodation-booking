package com.test.accommodation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.test.booking.Booking;
import com.test.booking.BookingService;

public class AccommodationService {
    private List<Accommodation> accommodations;
    BookingService bookingService = new BookingService();
    private static final String FILE_PATH = "./data/accommodation_list.txt";
    

    public AccommodationService() {
        accommodations = new ArrayList<>();
        loadAccommodations();
    }


    // 주승
    // 생성자: 파일에서 숙소 데이터를 로드

    // 파일에서 숙소 데이터 로드
    private void loadAccommodations() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                accommodations.add(Accommodation.fromFile(line));
            }
        } catch (Exception e) {
            System.err.println("숙소 데이터를 로드하는 데 실패했습니다: " + e.getMessage());
        }
    }

    // 파일에 숙소 데이터 저장
    public void saveAccommodations() {
        List<String> lines = new ArrayList<>();
        for (Accommodation accommodation : accommodations) {
            lines.add(accommodation.toFileFormat());
        }
        try {
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (Exception e) {
            System.err.println("숙소 데이터를 저장하는 데 실패했습니다: " + e.getMessage());
        }
    }

    // 숙소 목록 반환
    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    // 숙소 추가
    public void addAccommodation(Accommodation accommodation) {
        accommodations.add(accommodation);
        saveAccommodations(); // 파일에 저장
        System.out.println("새로운 숙소가 추가되었습니다.");
    }

    // 숙소 삭제
    public boolean deleteAccommodation(int accommodationId) {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getId() == accommodationId) {
                accommodations.remove(accommodation);
                saveAccommodations(); // 파일에 저장
                return true; // 삭제 성공
            }
        }
        return false; // 삭제 실패 (숙소 ID를 찾을 수 없음)
    }

    // 숙소 ID로 숙소 가져오기
    public Accommodation getAccommodationById(int accommodationId) {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getId() == accommodationId) {
                return accommodation;
            }
        }
        return null; // 숙소를 찾을 수 없는 경우
    }

    // 모든 숙소 출력
    public void showAllAccommodations() {
        System.out.println("+" + "-".repeat(50) + "+");
        System.out.println("|" + " ".repeat(18) + "숙소 리스트" + " ".repeat(19) + "|");
        System.out.println("+" + "-".repeat(50) + "+");

        if (accommodations.isEmpty()) {
            System.out.println("등록된 숙소가 없습니다.");
            return;
        }

        for (Accommodation accommodation : accommodations) {
            System.out.printf("ID: %d, 이름: %s, 지역: %s, 1박 요금: %d원\n",
                    accommodation.getId(),
                    accommodation.getAccommodationName(),
                    accommodation.getArea(),
                    accommodation.getPrice());
        }
    }
//
    
    //지원
    public static void groupRandomlist() {
		
	    String accomfilePath = ".\\data\\accommodation_list.txt";

	    // Accommodation 리스트 생성
	    ArrayList<Accommodation> accommodations = new ArrayList<>();

	    // Booking 데이터를 Map으로 저장
	    Map<Integer, List<Booking>> bookingData = new HashMap<>();

	    // booking_list 읽어서 Map에 저장
	    try (BufferedReader br = new BufferedReader(new FileReader(".\\data\\booking_list.txt"))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split("■");
	            int accommodationId = Integer.parseInt(parts[2].trim()); // 숙소 ID
	            String checkInDate = parts[3].trim();
	            String checkOutDate = parts[4].trim();
	            

	            // Booking 객체 생성
	            Booking booking = new Booking(accommodationId, accommodationId, accommodationId, checkInDate, checkOutDate, accommodationId, accommodationId);

	            // Map에 데이터 추가
	            bookingData.putIfAbsent(accommodationId, new ArrayList<>());
	            bookingData.get(accommodationId).add(booking);
	        }
	    } catch (Exception e) {
	        System.out.println("Error reading the booking list: " + e.getMessage());
	    }

	    // accommodation_list 읽기
	    try (BufferedReader br = new BufferedReader(new FileReader(accomfilePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split("■");

	            if (parts.length > 6) { // 데이터 유효성 검사
	                int accommodationId = Integer.parseInt(parts[0].trim());
	                String username = parts[1].trim();
	                String area = parts[2].trim();
	                String address = parts[3].trim();
	                String accommodationName = parts[4].trim();
	                int maxGuest = Integer.parseInt(parts[5].trim());
	                int price = Integer.parseInt(parts[6].trim());
	                String notice = parts[7].trim();
	               
	                accommodations.add(new Accommodation(accommodationId, username, area, address, accommodationName, maxGuest, price, notice));
	            } else {
	                System.out.println("Invalid line format: " + line);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the file: " + e.getMessage());
	    }

	    Scanner scanner = new Scanner(System.in);
	    Set<String> validRegions = Set.of("서울", "대구", "대전", "부산", "제주", "강릉", "경주", "속초");

	    // 지역 입력받기
	    String searchRegion;
	    while (true) {
	        System.out.print("검색할 지역을 입력하세요\n(서울, 대구, 대전, 부산, 제주, 강릉, 경주, 속초 중 선택): ");
	        searchRegion = scanner.nextLine().trim();

	        if (validRegions.contains(searchRegion)) {
	            break; // 유효한 지역 입력 시 루프 종료
	        } else {
	            System.out.println("검색 가능한 지역이 아닙니다. 다시 입력해주세요.\n");
	        }
	    }
	    String checkInDate;
	    while (true) {
	        System.out.print("체크인 날짜를 입력하세요 (예: 2025-01-01): ");
	        checkInDate = scanner.nextLine().trim();
	        if (isValidDate(checkInDate)) {
	            break; // 유효한 날짜라면 루프 종료
	        }
	    }

	    String checkOutDate;
	    while (true) {
	        System.out.print("체크아웃 날짜를 입력하세요 (예: 2025-01-03): ");
	        checkOutDate = scanner.nextLine().trim();
	        if (isValidDate(checkOutDate) && areValidDates(checkInDate, checkOutDate)) {
	            break; // 유효한 체크아웃 날짜라면 루프 종료
	        }
	    }


	    // 조건에 따라 숙소 필터링
	    List<Accommodation> filteredAccommodations = new ArrayList<>();
	    
	    for (Accommodation accom : accommodations) {
	        if (accom.getArea().equals(searchRegion)) {
	            // 예약 데이터를 확인하여 예약 가능한 숙소인지 필터링
	            List<Booking> bookings = bookingData.getOrDefault(accom.getAccommodationName(), new ArrayList<>());
	            boolean isAvailable = true;

	            for (Booking booking : bookings) {
	                if (booking.overlapsWith(checkInDate, checkOutDate)) {
	                    isAvailable = false;
	                    break;
	                }
	            }

	            if (isAvailable) {
	                filteredAccommodations.add(accom);
	            }
	        }
	    }

	    // 필터링된 숙소 출력
	    if (!filteredAccommodations.isEmpty()) {
	        Collections.shuffle(filteredAccommodations);

	        System.out.println("예약 가능한 추천 숙소 리스트:");
	        System.out.println("[번호]\t[이름]\t\t [최대 인원]\t  [가격]\t[주소]\t");

	        for (int i = 0; i < Math.min(20, filteredAccommodations.size()); i++) {
	            Accommodation accom = filteredAccommodations.get(i);
	            System.out.printf("%d\t%-5s\t\t%2d명\t%,9d원\t%5s\t%n", (i + 1), accom.getAccommodationName(), accom.getMaxGuest(), accom.getPrice(), accom.getAddress());
	        }
	    } else {
	        System.out.println("해당 날짜에 예약 가능한 숙소가 없습니다.");
	    }
	
	    if (!filteredAccommodations.isEmpty()) {
	        // 번호 선택
	        int selectedNumber = -1; // 초기값 설정
	        
	        do {
	            System.out.print("\r\n더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ " + Math.min(20, filteredAccommodations.size()) + "): ");
	            while (!scanner.hasNextInt()) { // 숫자가 아닌 입력 처리
	                System.out.println("유효한 숫자를 입력해주세요.");
	                scanner.next(); // 잘못된 입력 버림
	                System.out.print("\r\n더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ " + Math.min(20, filteredAccommodations.size()) + "): ");
	            }
	            selectedNumber = scanner.nextInt();

	            if (selectedNumber >= 1 && selectedNumber <= filteredAccommodations.size()) {
	                Accommodation selectedAccommodation = filteredAccommodations.get(selectedNumber - 1); // 번호에 해당하는 숙소 선택

	                // 숙소 정보 출력
	                System.out.println("\n[선택한 숙소 정보]");
	                System.out.println("이름: " + selectedAccommodation.getAccommodationName());
	                System.out.println("주소: " + selectedAccommodation.getAddress());
	                System.out.println("최대 인원: " + selectedAccommodation.getMaxGuest() + "명");
	                System.out.println("가격: " + String.format("%,d원", selectedAccommodation.getPrice()));
	                System.out.println("공지사항: ");
	                printFormattedNotice(selectedAccommodation.getNotice(), 30);
	                break; // 유효한 입력으로 루프 종료
	            } else {
	                System.out.println("잘못된 번호를 입력하셨습니다. 다시 입력해주세요.");
	            }
	        } while (true); // 유효한 번호 입력 시까지 반복
	    } else {
	        System.out.println("예약 가능한 숙소가 없습니다.");
	    }
    }

public static void printFormattedNotice(String notice, int maxLength) {
    int start = 0;
    while (start < notice.length()) {
        // 현재 출력할 부분 계산
        int end = Math.min(start + maxLength, notice.length());
        String line = notice.substring(start, end);
        System.out.printf(" %-35s \n", line); // 좌측 정렬 및 너비 조정
        start = end; // 다음 부분으로 이동
    }
}
    
    

private static boolean isValidDate(String date) {
    String datePattern = "^\\d{4}-\\d{2}-\\d{2}$"; // YYYY-MM-DD 형식
    if (!date.matches(datePattern)) {
        System.out.println("\r\n날짜 형식이 잘못되었습니다. 올바른 형식: YYYY-MM-DD");
        return false;
    }

    try {
        // 날짜 형식을 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();

        if (parsedDate.isBefore(today)) {
            System.out.println("\r\n과거 날짜는 입력할 수 없습니다.");
            return false;
        }
        return true;
    } catch (DateTimeParseException e) {
        System.out.println("\r\n유효하지 않은 날짜입니다.");
        return false;
    }
}

// 체크인과 체크아웃 날짜 유효성 검사
private static boolean areValidDates(String checkInDate, String checkOutDate) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);

        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            System.out.println("체크인 날짜는 체크아웃 날짜보다 이전이어야 합니다.");
            return false;
        }
        return true;
    } catch (DateTimeParseException e) {
        System.out.println("유효하지 않은 날짜 형식입니다.");
        return false;
    }
}



//<랜덤 추출>
public static void randomList() {
    
	String filePath = ".\\data\\accommodation_list.txt";

	// Accommodation 리스트 생성
	ArrayList<Accommodation> accommodations = new ArrayList<>();

	// 텍스트 파일 읽기
	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		String line;
		while ((line = br.readLine()) != null) {
			String[] parts = line.split("■");

			if (parts.length > 6) { // 데이터 유효성 검사
				int accommodationId = Integer.parseInt(parts[0].trim());
				String username = parts[1].trim();
				String area = parts[2].trim();
				String address = parts[3].trim();
				String accommodationName = parts[4].trim();
				int maxGuest = Integer.parseInt(parts[5].trim());
				int price = Integer.parseInt(parts[6].trim());
				String notice = parts[7].trim();

				accommodations.add(new Accommodation(accommodationId, username, area, address, accommodationName,
						maxGuest, price, notice));
			} else {
				System.out.println("Invalid line format: " + line);
			}
		}
	} catch (IOException e) {
		System.out.println("Error reading the file: " + e.getMessage());
	}

	Scanner scanner = new Scanner(System.in);

	while (true) {
		// 랜덤 숙소 리스트 출력
		if (!accommodations.isEmpty()) {
			Collections.shuffle(accommodations);

			System.out.println("\n오늘의 추천 숙소 리스트:");
			System.out.println("[번호]\t[지역]\t[숙소이름]\t\t[인원]\t\t[가격]");
			for (int i = 0; i < Math.min(20, accommodations.size()); i++) {
				Accommodation accom = accommodations.get(i);
				System.out.printf("%d\t%s\t%2s\t\t%5d\t%,10d원%n", (i + 1), accom.getArea(),
						accom.getAccommodationName(), accom.getMaxGuest(), accom.getPrice());
			}

			int selectedNumber;
			while (true) {
				System.out.print("\r\n더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ 20): ");
				while (!scanner.hasNextInt()) {
					System.out.println("유효한 숫자를 입력해주세요.");
					scanner.next();
					System.out.print("\r\n더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ 20): ");
				}

				selectedNumber = scanner.nextInt();

				if (selectedNumber >= 1 && selectedNumber <= 20) {
					break; // 유효한 입력 시 루프 탈출
				} else {
					System.out.println("잘못된 번호를 입력하셨습니다. 다시 입력해주세요.");
				}
			}

			// 선택한 숙소 정보 출력
			Accommodation selectedAccommodation = accommodations.get(selectedNumber - 1);
			System.out.println("\n[선택한 숙소 정보]");
			System.out.println("이름: " + selectedAccommodation.getAccommodationName());
			System.out.println("주소: " + selectedAccommodation.getAddress());
			System.out.println("최대 인원: " + selectedAccommodation.getMaxGuest() + "명");
			System.out.println("가격: " + String.format("%,d원", selectedAccommodation.getPrice()));
			System.out.println("공지사항: ");
			printFormattedNotice(selectedAccommodation.getNotice(), 30);

			// 옵션 제공
			int choice;
			while (true) {
				System.out.println("\n1. 숙소 리스트로 다시 돌아가기");
				System.out.println("2. 예약하기");
				System.out.print("\n선택: ");
				while (!scanner.hasNextInt()) {
					System.out.println("유효한 숫자를 입력해주세요.");
					scanner.next();
					System.out.print("선택: ");
				}

				choice = scanner.nextInt();
				if (choice == 1 || choice == 2) {
					break; // 유효한 입력 시 루프 탈출
				} else {
					System.out.println("잘못된 번호를 입력하셨습니다. 다시 입력해주세요.");
				}
			}

			if (choice == 1) {
				System.out.println("\n숙소 리스트로 돌아갑니다.");
				continue; // 리스트로 돌아가기
			} else if (choice == 2) {
				System.out.println("\n예약하기로 이동합니다.");

				return; // 메서드 종료
			}
		} else {
			System.out.println("데이터가 없습니다.");
			return;
		}
	} 
}

}

