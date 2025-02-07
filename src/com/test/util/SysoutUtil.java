package com.test.util;
import java.util.Calendar;

public class SysoutUtil {

    // 배너
    public static void banner() throws InterruptedException {
        String bWHITE = "\u001B[47m";
        Calendar now = Calendar.getInstance();
//        System.out.print("\033[47m\033[30m");
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃"+"\t⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣲⣷⣷⣷⣷⣷⡦⢸⣿⡄⣿⣿⣿⣿⣶⠀⠀⣾⣿⠀⢠⣿⣿⣿⣧⠀⠀⣿⣷⠀⣾⣿⣿⣿⡿⢼⣿⢸⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀\t"+"┃");
        System.out.println("┃"+"\t⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠁⡁⣸⣿⡆⠀⠀⠀⣿⣟⠀⠀⣾⣿⠀⢼⣿⠁⢸⣿⢄⢀⣿⣿⠀⣿⡯⢸⣿⠀⢸⣿⣸⣿⠄⠀⠀⠀⠀⠀⠀⠀⠀\t"+"┃");
        System.out.println("┃"+"\t⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣇⠀⠿⣿⣿⠆⠀⠀⠀⣾⣿⠀⠀⣾⣿⠀⣻⣿⠀⢸⣿⠽⠿⣿⣿⠀⣿⣯⢸⣿⡀⢸⣿⢿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀\t"+"┃");
        System.out.println("┃"+"\t⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣿⡿⣿⣧⠀⢸⣿⡃⠀⠀⠀⣽⣷⠀⠀⣾⣿⠀⢺⣿⡀⣸⣿⠁⠀⣿⣿⠀⣿⣷⢸⣿⡀⢸⣿⢸⣿⠄⠀⠀⠀⠀⠀⠀⠀⠀\t"+"┃");
        System.out.println("┃"+"\t⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡿⠏⠀⠙⣿⡗⢸⣿⡃⠀⠀⠀⣻⡯⠀⠀⢾⣿⠀⠈⠿⣿⡿⠏⠀⠀⣿⡿⠀⠻⠿⠿⠿⠿⢻⣿⢸⣿⠂⠀⠀⠀⠀⠀⠀⠀⠀\t"+"┃");
        System.out.println("┃"+"\t⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\t"+"┃");
        System.out.printf( "┃"+"\t\t\t\t\t\t\t\t\t\t\t%2tY년  %2tm월  %2td일  %2tH시  %2tM분\t"+"┃\r\n",now,now,now,now,now);
//        System.out.print("\033[0m");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
//        System.out.print("\033[0m");

    }


    //화면 넘기기
    public static void nextpage() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
