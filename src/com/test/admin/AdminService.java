package com.test.admin;

import com.test.accommodation.Accommodation;
import com.test.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
//
//	    private List<Accommodation> accommodations = new ArrayList<>();
//	    private static final String FILE_PATH = ".\\data\\accommodation_list.txt";
//
//	    // Constructor to load accommodations from file
//	    public AdminService() {
//	        loadAccommodations();
//	    }
//
//	    // 관리자 로그인
//	    public boolean loginAdmin(String adminId, String password) {
//	        // 예제: 관리자 계정을 하드코딩 (실제 프로젝트에서는 DB나 안전한 저장소에서 관리)
//	        if (adminId.equals("admin") && password.equals("1234")) {
//	            System.out.println("관리자 로그인 성공!");
//	            return true;
//	        }
//	        System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
//	        return false;
//	    }
//
//	    // 숙소 추가
//	    public boolean addAccommodation(String host, String name, String area, String address, String notice, int price) {
//	        int newId = generateAccommodationId();
//	        Accommodation newAccommodation = new Accommodation(newId, host, name, area, address, notice, price);
//	        accommodations.add(newAccommodation);
//	        saveAccommodations();
//	        System.out.println("숙소가 성공적으로 추가되었습니다. 숙소 ID: " + newId);
//	        return true;
//	    }
//
//	    // 숙소 수정
//	    public boolean updateAccommodation(int accommodationId, String host, String name, String address, String notice, int price) {
//	        for (Accommodation accommodation : accommodations) {
//	            if (accommodation.getId() == accommodationId) {
//	                accommodation.setHost(host.isEmpty() ? accommodation.getHost() : host);
//	                accommodation.setName(name.isEmpty() ? accommodation.getName() : name);
//	                accommodation.setAddress(address.isEmpty() ? accommodation.getAddress() : address);
//	                accommodation.setNotice(notice.isEmpty() ? accommodation.getNotice() : notice);
//	                accommodation.setPrice(price > 0 ? price : accommodation.getPrice());
//	                saveAccommodations();
//	                System.out.println("숙소 정보가 성공적으로 수정되었습니다.");
//	                return true;
//	            }
//	        }
//	        System.out.println("숙소 ID를 찾을 수 없습니다.");
//	        return false;
//	    }
//
//	    // 숙소 삭제
//	    public boolean deleteAccommodation(int accommodationId) {
//	        for (Accommodation accommodation : accommodations) {
//	            if (accommodation.getId() == accommodationId) {
//	                accommodations.remove(accommodation);
//	                saveAccommodations();
//	                System.out.println("숙소가 성공적으로 삭제되었습니다.");
//	                return true;
//	            }
//	        }
//	        System.out.println("숙소 ID를 찾을 수 없습니다.");
//	        return false;
//	    }
//
//	    // 파일에서 숙소 로드
//	    private void loadAccommodations() {
//	        List<String> lines = FileUtil.readFromFile(FILE_PATH);
//	        for (String line : lines) {
//	            accommodations.add(Accommodation.fromFile(line));
//	        }
//	    }
//
//	    // 숙소 정보를 파일에 저장
//	    private void saveAccommodations() {
//	        List<String> lines = new ArrayList<>();
//	        for (Accommodation accommodation : accommodations) {
//	            lines.add(accommodation.toFileFormat());
//	        }
//	        FileUtil.writeToFile(FILE_PATH, lines);
//	    }
//
//	    // 숙소 ID 생성
//	    private int generateAccommodationId() {
//	        return accommodations.size() == 0 ? 1 : accommodations.get(accommodations.size() - 1).getId() + 1;
//	    }
}


