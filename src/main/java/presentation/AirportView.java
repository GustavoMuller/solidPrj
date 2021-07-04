package presentation;

import controller.AirportController;
import data.Aircraft;
import data.Flight;
import data.FlightStatus;
import data.Location;
import utils.Console;
import java.time.LocalDate;
import java.util.Arrays;

public class AirportView {

    private final AirportController controller;

    public AirportView(AirportController controller){
        this.controller = controller;
    }

    public void displayMenu() {
        int option;

        do {
            option = Console.readNumber("""
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
                                              ****************************************""");

            switch (option) {
                case 1 -> displayFlightsList();
                case 2 -> displayFlight(Console.readNumber("Enter the flight ID: "));
                case 3 -> {
                    controller.addFlight(readFlightData());
                    System.out.println("The flight was added successfully!!!");
                }
                case 4 -> controller.addFlightsFromFile(Console.readText("Enter Flights' Filename: ", 1, 25));
                case 5 -> displayFlightStatusMenu(Console.readNumber("Enter the flight ID: "));
                case 6 -> displayReportsMenu();
                case 7 -> System.out.println("Come back soon!!!");
                default -> System.out.println("Please select a valid option!");
            }

        } while(option != 7);
    }

    private void displayFlight(int flightId) {
        if(controller.flightExists(flightId))
            System.out.println(controller.getFlightDetails(flightId));
        else System.out.println("There are no flights registered with that ID.");
    }

    private void displayFlightsList(){
        var flights = controller.getFlightsList();
        if(flights == null || flights.isEmpty())
            System.out.println("There are no flights registered at the moment.");
        else flights.forEach(System.out::print);
    }

    private void displayFlightStatusMenu(int flightId) {
        if(controller.flightExists(flightId)) System.out.println("There are no flights registered with that ID.");
        else {
            int statusOption = Console.readNumber("Select the new status for the flight:\n1-On time\n2-Delayed\n3-Cancelled\n4-Landed\n5-Return to menu");
            var updateFlight = controller.getFlightDetails(flightId);

            switch(statusOption) {
                case 1:
                    var newDateTime = Console.readDateTime("Enter the new arrival date and time:");
                    updateFlight.setStatus(FlightStatus.ON_TIME);
                    updateFlight.setArrivalTime(newDateTime);
                    break;
                case 2:
                    var newArrivalDateTime = Console.readDateTime("Enter the new arrival date and time:");
                    updateFlight.setStatus(FlightStatus.DELAYED);
                    updateFlight.setArrivalTime(newArrivalDateTime);
                    break;
                case 3:
                    var reason = Console.readText("Enter the specific reason why the flight was cancelled: ", 5, 100);
                    updateFlight.setStatus(FlightStatus.CANCELLED);
                    updateFlight.setCancellationMotive(reason);
                    break;
                case 4:
                    var incidents = Console.readText("\"Enter the list of flight incidents(if any) separated by comma: ", 5, 100);
                    var incidentsList = Arrays.stream(incidents.trim().split(",")).toList();
                    updateFlight.setStatus(FlightStatus.LANDED);
                    updateFlight.setIncidents(incidentsList);
                    break;
                default: break;
            }

            controller.updateFlightStatus(updateFlight);
            if (statusOption >= 1 && statusOption <= 4) System.out.println("The flight's status was updated successfully!");
        }
    }

    private void displayReportsMenu() {
        int reportsOption;
        var email = Console.readEmail("Enter destination email: ");

       do {
           reportsOption = Console.readNumber("Select an option:\n1-Generate and send flight's report\n2-Generate and send flights' report by date\n3-Cancel");

           if(reportsOption == 1) {
               var flight = Console.readNumber("Enter flight ID: ");

               if(controller.flightExists(flight)) {
                   controller.createAircraftReport(flight, "Report by ID " + flight);
                   controller.sendEmail(email, "Report by ID " + flight);
                   System.out.println("The report of the flight " + flight + " was sent to " + email + " successfully!!!");
               } else System.out.println("There are no flights registered with that ID");

           } else if(reportsOption == 2) {
               var filterDate = Console.readDate();
               controller.createFlightReport(filterDate, "Report by Date " + LocalDate.now());
               controller.sendEmail(email, "Report by Date " + LocalDate.now());
               System.out.println("The report of the flights registered on " + filterDate + " was sent to " + email + " successfully!!!");
           }
       } while(reportsOption >= 1 && reportsOption <= 2);

    }

    private Flight readFlightData() {
        var flightId = Console.readNumber("Enter flight ID: ");
        var countryOrigin = Console.readText("Enter country of origin: ", 2, 50);
        var cityOrigin = Console.readText("Enter city of origin: ", 2, 50);
        var countryDestination = Console.readText("Enter country of destination: ", 2, 50);
        var cityDestination = Console.readText("Enter city of destination: ", 2, 50);
        var origin = new Location(countryOrigin, cityOrigin);
        var destination = new Location(countryDestination, cityDestination);
        var departureDateTime = Console.readDateTime("Enter departure information");
        var arrivalDateTime = Console.readDateTime("Enter arrival information");
        var airline = Console.readText("Enter Airline name: ", 2, 50);
        var aircraftModel = Console.readText("Enter the aircraft model: ", 2, 20);
        var aircraftRange = Console.readNumber("Enter the maximum range with full tanks (in Km): ");
        var aircraftPassengerCapacity = Console.readNumber("Enter the passengers maximum capacity: ");
        var isArrival = destination.equals(controller.getAirportLocation());
        var aircraft = new Aircraft(aircraftModel, aircraftPassengerCapacity, aircraftRange);
        controller.addAircraft(aircraft);
        return new Flight(flightId, FlightStatus.ON_TIME, origin, destination, departureDateTime, arrivalDateTime, airline, aircraft, isArrival);
    }

}
