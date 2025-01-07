package com.test.accommodation;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AccommodationService {
    private List<Accommodation> accommodations;
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
}



