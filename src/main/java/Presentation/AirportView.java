package Presentation;

import Controler.AirportController;
import Data.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

public class AirportView {
    private AirportController controller;

    public AirportView(){
        controller = new AirportController();
    }

    public void displayMenu() {
        var input = new Scanner(System.in);
        var option = 1;
        while(option != 7) {
            option = readNumber("""
                                              ****************************************
                                              =============== AIRPORT ===============
                                              ****************************************
                                              Select an option:
                                              1-Display flights list
                                              2-Display flight details
                                              3-Add flight
                                              4-Add flights from file (xlsx)
                                              5-Update flight status
                                              6-Reports
                                              7-Exit
                                              ****************************************""", 7);

            switch (option) {
                case 1 -> displayFlightsList();
                case 2 -> displayFlight();
                case 3 -> {
                    var flight = readFlightData();
                    controller.addFlight(flight);
                    System.out.println("The flight was added successfully!!!");
                }
                case 4 -> {
                    System.out.println("Enter Flights' Filename: ");
                    var flightFileURL = input.nextLine().trim();
                    controller.addFlightsFromFile(flightFileURL);
                }
                case 5 -> displayFlightStatusMenu();
                case 6 -> displayReportsMenu();
                default -> System.exit(0);
            }
        }
    }

    public void displayFlight() {
        int flightId = readNumber("Enter the flight ID:", Integer.MAX_VALUE);
        if(controller.flightExists(flightId))
            System.out.println(controller.getFlightDetails(flightId));
        else System.out.println("There are no flights registered with that ID.");
    }

    public void displayFlightsList(){
        var flights = controller.getFlightsList();

        if(flights == null || flights.isEmpty())
            System.out.println("There are no registered flights at the moment");
        else flights.stream().forEach(System.out::print);
    }

    public void displayFlightStatusMenu() {
        var input = new Scanner(System.in);
        int flight = readNumber("Enter flight code/id: ", Integer.MAX_VALUE);
        int statusOption = readNumber("Select the new status for the flight:\n1-On time\n2-Delayed\n3-Cancelled\n4-Landed\n5-Return to menu", 5);

        switch(statusOption) {
            case 1:
                controller.updateFlightStatus(flight, "On time");
                break;
            case 2:
                var newArrivalDateTime = readDateTime("Enter the new arrival date and time:");
                controller.updateFlightStatus(flight, "Delayed", newArrivalDateTime);
                break;
            case 3:
                System.out.println("Enter the specific reason why the flight was cancelled: ");
                String reason = input.nextLine().trim();
                controller.updateFlightStatus(flight, "Cancelled", reason);
                break;
            case 4:
                System.out.println("Enter the list of flight incidents(if any) separated by comma: ");
                String incidents = input.nextLine().trim();

                var incidentsList = Arrays.stream(incidents.trim().split(",")).toList();
                controller.updateFlightStatus(flight, "Landed", incidentsList);
                break;
            default: break;
        }

    }

    public void displayReportsMenu() {
        var email = readEmail("Enter the report destination email: ");

        int reportsOption = readNumber("Select an option:\n1-Generate and send flight's report\n2-Generate and send flights' report by date\n3-Cancel", 3);

        if(reportsOption == 1) {
            int flight = readNumber("Enter flight id: ", Integer.MAX_VALUE);
            // Invocar metodo de la clase que genere y/o envie el reporte en base a email y flightId
        } else if(reportsOption == 2) {
            var filterDateTime = readDateTime("Enter the date and time information to generate the report:");
            // Invocar metodo de la clase que genere y/o envie el reporte en base a email y date
        }
    }

    private int readNumber(String prompt, int max) {
        var userInput = new Scanner(System.in);
        final var NUMBER_REGEX = "[0-9]+";
        String input;

        while(true) {
            System.out.println(prompt);
            input = userInput.nextLine();

            if(input.matches(NUMBER_REGEX) && Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= max)
                return Integer.parseInt(input);

            System.out.println("Enter a valid number between " + 1 + " and " + max);
        }
    }

    private String readEmail(String prompt) {
        String input;
        var userInput = new Scanner(System.in);
        final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]" +
                "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
                "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        while(true) {
            System.out.println(prompt);
            input = userInput.nextLine();

            if(input.matches(EMAIL_REGEX))
                return input;

            System.out.println("Please enter a valid email");
        }
    }

    private String readText(String prompt, int minLength, int maxLength) {
        var userInput = new Scanner(System.in);
        String text;

        while(true) {
            System.out.println(prompt);
            text = userInput.nextLine();

            if(text.trim().length() >= minLength && text.trim().length() <= maxLength)
                return text.trim();

            System.out.println("You must enter between " + minLength + " and " + maxLength + " characters.");
        }
    }

    private LocalDate readDate() {
        var userInput = new Scanner(System.in);
        final String DATE_REGEX = "(\\d{4})-(\\d{2})-(\\d{2})";
        String date;

        while(true) {
            System.out.println("Enter the date with format YYYY-MM-DD HH:MM:SS");
            date = userInput.nextLine().trim();

            if(date.matches(DATE_REGEX) )
                return LocalDate.parse(date);

            System.out.println("You must enter a valid date.");
        }
    }

    private LocalTime readTime() {
        var userInput = new Scanner(System.in);
        final String TIME_REGEX = "(\\d{2})-(\\d{2})-(\\d{2})";
        String time;

        while(true) {
            System.out.println("Enter the time with format HH:MM:SS");
            time = userInput.nextLine().trim();

            if(time.matches(TIME_REGEX) )
                return LocalTime.parse(time);

            System.out.println("You must enter a valid time.");
        }
    }

    private LocalDateTime readDateTime(String prompt) {
        System.out.println(prompt);
        var date = readDate();
        var time = readTime();
        return LocalDateTime.of(date, time);
    }

    private Flight readFlightData() {
        int flightId = verifyFlightId();
        String status = "On time";
        String countryOrigin = readText("Enter country of origin: ", 2, 50);
        String cityOrigin = readText("Enter city of origin: ", 2, 50);
        String countryDestination = readText("Enter country of destination: ", 2, 50);
        String cityDestination = readText("Enter city of destination: ", 2, 50);
        var departureDateTime = readDateTime("Enter departure information");
        var arrivalDateTime = readDateTime("Enter arrival information");
        var airline = readText("Enter Airline name: ", 2, 50);

        // TAMBIEN TENGO QUE PEDIR LOS DATOS DEL Aircraft (model, passengerCapacity, range) ????
        // PARA PODER INSTANCIAR EL Aircraft Y PASARLO AL CONSTRUCTOR DEL Flight
        return new Flight();
    }

    private int verifyFlightId() {
        var userInput = new Scanner(System.in);
        final var NUMBER_REGEX = "[0-9]+";
        String input;

        while(true) {
            System.out.println("Enter flight's ID: ");
            input = userInput.nextLine();

            if(input.matches(NUMBER_REGEX) && Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= Integer.MAX_VALUE) {
                if(!controller.flightExists(Integer.parseInt(input))) return Integer.parseInt(input);
                else System.out.println("The flight id already exists, try another one:");
            }else System.out.println("You must enter a valid flight ID.");
        }
    }
}
