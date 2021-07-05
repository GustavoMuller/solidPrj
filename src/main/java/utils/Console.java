package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public final class Console {

    private static final String DATE_REGEX = "(\\d{4})-(\\d{2})-(\\d{2})";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String TIME_REGEX = "(\\d{2}):(\\d{2})";
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
    private static final Scanner userInput = new Scanner(System.in);

    private Console() {}

    public static int readNumber(String prompt) {
        String input;

        while(true) {
            System.out.println(prompt);
            input = userInput.nextLine();

            if(input.matches(NUMBER_REGEX))
                return Integer.parseInt(input);

            System.out.println("Enter a valid number");
        }
    }

    public static String readText(String prompt, int minLength, int maxLength) {
        String text;

        while(true) {
            System.out.println(prompt);
            text = userInput.nextLine().trim();

            if(text.length() >= minLength && text.length() <= maxLength)
                return text;

            System.out.println("You must enter between " + minLength + " and " + maxLength + " characters.");
        }
    }

    public static LocalDate readDate() {
        String date;

        while(true) {
            System.out.println("Enter the date with format YYYY-MM-DD");
            date = userInput.nextLine().trim();

            if(date.matches(DATE_REGEX) )
                return LocalDate.parse(date);

            System.out.println("You must enter a valid date with the format YYYY-MM-DD.");
        }
    }

    public static LocalTime readTime() {
        String time;

        while(true) {
            System.out.println("Enter the time with format HH:MM");
            time = userInput.nextLine().trim();

            if(time.matches(TIME_REGEX) )
                return LocalTime.parse(time);

            System.out.println("You must enter a valid time with the format HH:MM.");
        }
    }

    public static LocalDateTime readDateTime(String prompt) {
        System.out.println(prompt);
        return LocalDateTime.of( readDate(), readTime());
    }

    public static String readEmail(String prompt) {
        String input;

        while(true) {
            System.out.println(prompt);
            input = userInput.nextLine().trim();

            if(input.matches(EMAIL_REGEX))
                return input;

            System.out.println("Please enter a valid email");
        }
    }
}
