package Presentation;

import Controler.AirportController;
import Data.Flight;

import java.util.Arrays;
import java.util.Scanner;

public class AirportView {
    private AirportController controller;

    public AirportView(){
        controller = new AirportController();
    }

    public void displayMenu() {
        var input = new Scanner(System.in);

        while(true) {
            int option = (int) readNumber("=============== AIRPORT ===============" +
                    "\n****************************************\nSelect an option:\n" +
                    "****************************************\n1-Display flights list\n" +
                    "2-Display flight details\n3-Add flight\n4-Add flights from file (xlsx)\n" +
                    "5-Update flight status\n6-Reports\n7-Exit\n****************************************\n", 7);
            switch (option) {
                case 1 -> {
                    displayFlightsList();
                }
                case 2 -> {
                    int flight = readNumber("Enter flight code/id: ", Integer.MAX_VALUE);
                    System.out.println(controller.getFLightDetails(flight));
                }
                case 3 -> {
                    // Pedir datos del vuelo al usuario y pasarlos al constructor para crear un nuevo Object
                    // Seran varios datos dado que un vuelo se compone de varias cosas
                    var flight = new Flight();
                    // Falta modficar el metodo addFlight para que reciba un Object Flight como argumento
                    controller.addFlight();
                }
                case 4 -> {
                    System.out.println("Enter Flights Filename: ");
                    var flightFileURL = input.nextLine().trim();
                    // Modificar el metodo addFlightsFromFile para que reciba como argumento un String como SRC
                    controller.addFlightsFromFile();
                }
                case 5 -> {
                    // Los estados los podriamos manejar como ENUMS para referenciarlos
                    // FlightStatuses.ON_TIME
                    // FlightStatuses.DELAYED
                    // FlightStatuses.CANCELLED
                    // FlightStatuses.LANDED

                    int flight = readNumber("Enter flight code/id: ", Integer.MAX_VALUE);
                    int statusOption = readNumber("Select the new status for the flight:\n1-On time\n2-Delayed" +
                            "\n3-Cancelled\n4-Landed\n5-Return to menu", 5);

                    switch(statusOption) {
                        case 1: // updateFlight("On time")
                            break;
                        case 2:
                            // pedir nueva fecha y hora de llegada, luego crear un nuevo Date
                            // con esos datos y pasarlo como argumento al metodo updateFlight
                            // updateFlightStatus("Delayed", newArrivalDate);
                            break;
                        case 3:
                            System.out.println("Enter the specific reason why the flight was cancelled: ");
                            String reason = input.nextLine().trim();
                            // updateFlightStatus("Cancelled", reason);
                            break;
                        case 4:
                            System.out.println("Enter the list of flight incidents(if occurred) separated by comma: ");
                            String incidents = input.nextLine();

                            var incidentsList = Arrays.stream(incidents.trim().split(",")).toList();
                            // updateFlightStatus("Landed", incidentsList);
                            break;
                        default: break;
                    }

                }
                case 6 -> {
                    var email = readEmail("Enter the report destination email: ");

                    int reportsOption = readNumber("Select an option:\n1-Generate and send flight report" +
                            "\n2-Generate and send flights by date report" +
                            "\n3-Cancel", 3);

                    if(reportsOption == 1) {
                        int flight = readNumber("Enter flight id: ", Integer.MAX_VALUE);
                        // Invocar metodo de la clase que genere y/o envie el reporte en base a email y flightId
                    } else if(reportsOption == 2) {
                        // Pedir fecha al usuario y generar un nuevo Date Object
                        // Invocar metodo de la clase que genere y/o envie el reporte en base a email y date
                    }


                }
                case 7 -> { System.exit(0); }
            }
        }
    }

    public void displayFlightsList(){
        //iterar y mostrar los vuelos en
        controller.getFlightsList().stream().forEach(System.out::print);
    }

    private int readNumber(String prompt, int max) {
        Scanner userInput = new Scanner(System.in);
        final String NUMBER_REGEX = "[0-9]+";
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
        Scanner userInput = new Scanner(System.in);
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
}
