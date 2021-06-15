package presentation;

import controller.AirportController;
import data.Aircraft;
import data.Flight;
import data.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

public class AirportView {
    private final AirportController controller;

    public AirportView(){
        controller = new AirportController();
    }

    public void displayMenu() {
        var input = new Scanner(System.in);
        int option;

        do {
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
                default -> System.out.println("Come back soon!");
            }

        } while(option != 7);
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
        else flights.forEach(System.out::print);
    }

    public void displayFlightStatusMenu() {
        var input = new Scanner(System.in);
        int flight = readNumber("Enter flight id: ", Integer.MAX_VALUE);

        if(!controller.flightExists(flight)) {
            System.out.println("The flight with the given id does not exist");
            return;
        }

        int statusOption = readNumber("Select the new status for the flight:\n1-On time\n2-Delayed\n3-Cancelled\n4-Landed\n5-Return to menu", 5);

        switch(statusOption) {
            case 1:
                var newDateTime = readDateTime("Enter the new arrival date and time:");
                controller.updateFlightStatus(flight, "On time", newDateTime);
                System.out.println("The flight's status was updated successfully!");
                break;
            case 2:
                var newArrivalDateTime = readDateTime("Enter the new arrival date and time:");
                controller.updateFlightStatus(flight, "Delayed", newArrivalDateTime);
                System.out.println("The flight's status was updated successfully!");
                break;
            case 3:
                String reason = readText("Enter the specific reason why the flight was cancelled: ", 5, 100);
                controller.updateFlightStatus(flight, "Cancelled", reason);
                System.out.println("The flight's status was updated successfully!");
                break;
            case 4:
                String incidents = readText("\"Enter the list of flight incidents(if any) separated by comma: ", 5, 100);
                var incidentsList = Arrays.stream(incidents.trim().split(",")).toList();
                controller.updateFlightStatus(flight, "Landed", incidentsList);
                System.out.println("The flight's status was updated successfully!");
                break;
            default: break;
        }

    }

    public void displayReportsMenu() {
        int reportsOption;
        var email = readEmail();

       do {
           reportsOption = readNumber("Select an option:\n1-Generate and send flight's report\n2-Generate and send flights' report by date\n3-Cancel", 3);

           if(reportsOption == 1) {
               int flight = readNumber("Enter flight ID: ", Integer.MAX_VALUE);
               if(!controller.flightExists(flight)) {
                   System.out.println("There are no flights registered with that ID");
                   continue;}
               // Invocar metodo de la clase que genere y/o envie el reporte en base a email y flightId
               System.out.println("The report of the flight " + flight + " was sent to " + email + " successfully!!!");
           } else if(reportsOption == 2) {
               var filterDate = readDate();
               // Invocar metodo de la clase que genere y/o envie el reporte en base a email y date
               System.out.println("The report of the flights registered on " + filterDate + " was sent to " + email + " successfully!!!");
           }
       } while(reportsOption != 3);

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

    private String readEmail() {
        String input;
        var userInput = new Scanner(System.in);
        final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";

        while(true) {
            System.out.println("Enter report's destination email: ");
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
            System.out.println("Enter the date with format YYYY-MM-DD");
            date = userInput.nextLine().trim();

            if(date.matches(DATE_REGEX) )
                return LocalDate.parse(date);

            System.out.println("You must enter a valid date.");
        }
    }

    private LocalTime readTime() {
        var userInput = new Scanner(System.in);
        final String TIME_REGEX = "(\\d{2}):(\\d{2})";
        String time;

        while(true) {
            System.out.println("Enter the time with format HH:MM");
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
        var flightId = verifyFlightId();
        var status = "On time";
        var countryOrigin = readText("Enter country of origin: ", 2, 50);
        var cityOrigin = readText("Enter city of origin: ", 2, 50);
        var countryDestination = readText("Enter country of destination: ", 2, 50);
        var cityDestination = readText("Enter city of destination: ", 2, 50);
        var origin = new Location(countryOrigin, cityOrigin);
        var destination = new Location(countryDestination, cityDestination);
        var departureDateTime = readDateTime("Enter departure information");
        var arrivalDateTime = readDateTime("Enter arrival information");
        var airline = readText("Enter Airline name: ", 2, 50);
        var aircraftModel = readText("Enter the aircraft model: ", 2, 20);
        var aircraftRange = readNumber("Enter the maximum range with full tanks (in Km): ", 17000);
        var aircraftPassengerCapacity = readNumber("Enter the passengers maximum capacity: ", 900);
        var isArrival = destination.equals(controller.getAirportLocation());
        var aircraft = new Aircraft(aircraftModel, aircraftPassengerCapacity, aircraftRange);
        controller.addAircraft(aircraft);
        return new Flight(flightId, status, origin, destination, departureDateTime, arrivalDateTime, airline, aircraft, isArrival);
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
