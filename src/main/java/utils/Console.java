package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public final class Console {

    private Console() {}

    private static final Scanner userInput = new Scanner(System.in);

    public static int readNumber(String prompt) {
        final var NUMBER_REGEX = "[0-9]+";
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
        final var DATE_REGEX = "(\\d{4})-(\\d{2})-(\\d{2})";
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
        final var TIME_REGEX = "(\\d{2}):(\\d{2})";
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
        var date = readDate();
        var time = readTime();
        return LocalDateTime.of(date, time);
    }

    public static String readEmail(String prompt) {
        String input;
        final var EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

        while(true) {
            System.out.println(prompt);
            input = userInput.nextLine().trim();

            if(input.matches(EMAIL_REGEX))
                return input;

            System.out.println("Please enter a valid email");
        }
    }
}
