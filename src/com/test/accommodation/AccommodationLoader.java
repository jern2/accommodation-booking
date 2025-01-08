package com.test.accommodation;

import java.util.List;

import com.test.util.FileUtil;

public class AccommodationLoader {
	public static void main(String[] args) {
		
		//숙소 데이터 출력 확인 테스트
        List<List<String>> accommodations = FileUtil.readAndSplitFile("./data/accommodation_list.txt", "■");
        for (List<String> accommodation : accommodations) {
            System.out.println(accommodation); 
            
        }
        
	}
}
