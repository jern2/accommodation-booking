package com.test.accommodation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccommodationView {
	
			 Scanner scanner = new Scanner(System.in);
			 
			 public void reservation() {
				 while(true) {
					 System.out.println("                     ┏━━━━━━━━━━┓                    ");
			         System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃ 숙소예약 ┃━━━━━━━━━━━━━━━━━━━┓");
			         System.out.println("┃                    ┗━━━━━━━━━━┛                   ┃");
			         System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
			         System.out.println("┃ ┃[1] 숙소 추천         ┃┃ [2] 숙소 검색         ┃  ┃");
			         System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
			         System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                          ┃");
			         System.out.println("┃ ┃[3] 초기화면          ┃                          ┃");
			         System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                          ┃");
			         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
			         System.out.print("선택: ");
			         
			         int sel = -1;
		             try {
		                    sel = scanner.nextInt();
		                    scanner.nextLine(); // 버퍼 비우기
		             } catch (InputMismatchException e) {
		                    System.err.println("잘못된 입력입니다. 숫자를 입력해주세요.");
		                    scanner.nextLine(); // 버퍼 비우기
		                    continue;
		             }
		             
		             switch (sel) {
	                    case 1:
	                    	AccommodationService.randomList();
	                        break;
	                    case 2:
	                    	AccommodationService.groupRandomlist();
	                        break;
	                    case 3:
	                        System.out.println("구현안됨");
	                        break;
	                    default:
	                        System.err.println("잘못된 입력입니다. 다시 시도하세요.");
	                }
				 }
			 }
		

//	         
//	         System.out.println("                     ┏━━━━━━━━━━┓");
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃ 숙소추천 ┃━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃                    ┗━━━━━━━━━━┛                   ┃");
//	         System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
//	         System.out.println("┃ ┃[1] 숙소 추천         ┃┃ [2] 숙소 검색        ┃ ┃");
//	         System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
//	         System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                          ┃");
//	         System.out.println("┃ ┃[3] 초기화면          ┃                          ┃");
//	         System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                          ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//	         System.out.print("선택: ");
//	         
//	         
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃         추천 숙소 리스트          ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//	         System.out.print("선택: ");
//	         
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃           여행지 검색             ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//	         System.out.print("검색할 지역을 입력하세요.");
//	         System.out.print("(서울, 대구, 대전, 부산, 제주, 강릉, 경주, 속초 중 선택):  ");
//	         
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃     체크인 날짜를 입력하세요     ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//	         System.out.print("체크인 날짜를 입력하세요 (예: 2025-01-01):");
//	        
//	         
//	         
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃     체크인 날짜를 입력하세요     ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//	         System.out.println("날짜 형식이 잘못되었습니다. 올바른 형식: YYYY-MM-DD");
//	         System.out.print("체크인 날짜를 입력하세요 (예: 2025-01-01):");
//	         
//
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃    예약 가능한 추천 숙소 리스트  ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//	        
//	         System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//	         System.out.println("┃         선택한 숙소 정보         ┃");
//	         System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
	         


	         
}

