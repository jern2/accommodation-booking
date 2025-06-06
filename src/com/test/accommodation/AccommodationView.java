package com.test.accommodation;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.test.util.SysoutUtil.banner;
import static com.test.util.SysoutUtil.nextpage;

public class AccommodationView {
	
			 Scanner scanner = new Scanner(System.in);
			 
			 public void reservation() throws IOException, InterruptedException {
				 while(true) {
					 nextpage();
					 banner();

//					 System.out.print("\033[47m\033[30m");
					 System.out.println("┃\t\t                       ┏━━━━━━━━━━┓                      \t\t┃");
			         System.out.println("┃\t\t  ┏━━━━━━━━━━━━━━━━━━━━┃  숙소예약  ┃━━━━━━━━━━━━━━━━━━━┓  \t\t┃ ");
			         System.out.println("┃\t\t  ┃                    ┗━━━━━━━━━━┛                   ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓  ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┃ ┃[1] 숙소 추천\t\t   ┃┃ [2] 숙소 검색\t\t   ┃  ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛  ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                          ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┃ ┃[3] 초기화면\t\t\t   ┃                          ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                          ┃  \t\t┃ ");
			         System.out.println("┃\t\t  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  \t\t┃ ");
					 System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//			         System.out.print("\033[0m");
			  	       System.out.println();
			         System.out.print("✔️선택: ");
			         
			         int sel = -1;
		             try {
		                    sel = scanner.nextInt();
		                    scanner.nextLine(); // 버퍼 비우기
		             } catch (InputMismatchException e) {
		                    System.err.println("⚠️잘못된 입력입니다. 숫자를 입력해주세요.");
		                    scanner.nextLine(); // 버퍼 비우기
		                    continue;
		             }
		             
		             switch (sel) {
	                    case 1:
	                    	AccommodationService.randomList();
	                        break;
	                    case 2:
	                    	AccommodationService.groupRandomlist2();
	                        break;
	                    case 3:
	                        return;	                      
	                    default:
	                        System.err.println("⚠️잘못된 입력입니다. 다시 시도하세요.");
	                }
				 }
			 }

}

