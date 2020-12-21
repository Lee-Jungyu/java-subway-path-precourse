package subway.utils;

import subway.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IOHandler {
    private final static Scanner scanner = new Scanner(System.in);

    public IOHandler() {}

    public static String getString(String msg) {
        System.out.println("## " + msg);
        String string = scanner.next();
        System.out.println();

        return string;
    }

    public static void printString(String msg) {
        System.out.println("## " + msg);
    }

    public static String printMainMenu() {
        System.out.println("## 메인 화면");
        System.out.println("1. 역 관리");
        System.out.println("2. 노선 관리");
        System.out.println("3. 구간 관리");
        System.out.println("4. 지하철 노선도 출력");
        System.out.println("Q. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "3", "4", "Q"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;

            printError("선택할 수 없는 기능입니다.");
        }
    }

    public String printStationMenu() {
        System.out.println("## 역 관리 화면");
        System.out.println("1. 역 등록");
        System.out.println("2. 역 삭제");
        System.out.println("3. 역 조회");
        System.out.println("B. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "3", "B"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;


            printError("선택할 수 없는 기능입니다.");
        }
    }

    public String printLineMenu() {
        System.out.println("## 노선 관리 화면");
        System.out.println("1. 노선 등록");
        System.out.println("2. 노선 삭제");
        System.out.println("3. 노선 조회");
        System.out.println("B. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "3", "B"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;

            printError("선택할 수 없는 기능입니다.");
        }
    }

    public String printSectionMenu() {
        System.out.println("## 구간 관리 화면");
        System.out.println("1. 구간 등록");
        System.out.println("2. 구간 삭제");
        System.out.println("B. 돌아가기");
        System.out.println();

        while(true) {
            System.out.println("원하는 기능을 선택하세요.");
            String menu = scanner.next();

            System.out.println();

            String[] strings = {"1", "2", "B"};
            boolean check = Validator.checkValidValue(menu, Arrays.asList(strings.clone()));
            if(check) return menu;

            printError("선택할 수 없는 기능입니다.");
        }
    }


    public static void printInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void printError(String msg) {
        System.out.println("[ERROR] " + msg);
        System.out.println();
    }
}
