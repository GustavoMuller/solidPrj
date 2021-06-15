package controller;

import data.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AirportController {
    private final Airport airport;
    private Report report;
    private Email email;

    public AirportController(){
        airport = new Airport( new Location("El Salvador", "San Salvador"));
    }

    public void addAircraft(Aircraft a){
        airport.addAircraft(a);
    }

    public boolean hasFlights(){
        return !(airport.getFlights() == null || airport.getFlights().isEmpty());
    }

    public boolean flightExists(int id){
        if (hasFlights())
            for (Flight f : airport.getFlights())
                if (f.getId() == id) return true;
        return false;
    }

    public Flight getFlightDetails(int id){
        for (Flight f : airport.getFlights())
            if (f.getId() == id) return f;
        return null;
    }

    public List<Flight> getFlightDetails(LocalDate date) {
        List<Flight> flightList = new ArrayList<>();
        if (hasFlights())
            for (Flight f : airport.getFlights())
                if (f.getArrivalTime().toLocalDate().equals(date)) flightList.add(f);
        return flightList;
    }

    public List<Flight> getFlightsList(){
        return airport.getFlights();
    }

    public Location getAirportLocation() {
        return airport.getLocation();
    }

    public void addFlight(Flight f){
        airport.addFlight(f);
    }

    public void addFlightsFromFile(String fileName){
        report = new Report();
        for (Flight f : report.readFile(fileName)) {
            airport.addFlight(f);
        }
        System.out.println("Flights were successfully added from file!");
    }

    //Overloaded method to update a flight's status
    public void updateFlightStatus(int id, String status, LocalDateTime arrivalTime){ //DELAYED, ON TIME
        Flight flight = getFlightDetails(id);
        flight.setStatus(status);
        flight.setArrivalTime(arrivalTime);
        airport.updateFlight(flight);
    }

    public void updateFlightStatus(int id, String status, String cancelMotive){ //CANCELED
        Flight flight = getFlightDetails(id);
        flight.setStatus(status);
        flight.setCancellationMotive(cancelMotive);
        airport.updateFlight(flight);
    }

    public void updateFlightStatus(int id, String status, List<String> incidents){ //LANDED
        Flight flight = getFlightDetails(id);
        flight.setStatus(status);
        flight.setIncidents(incidents);
        airport.updateFlight(flight);
    }

    public void createAircraftReport(int id, String fileName){
        Flight flight = getFlightDetails(id);
        if (flight == null) {
            System.out.println("There are no flights with the ID entered");
        } else {
            report = new Report();
            report.addToReport(flight, fileName);
        }
    }

    public void createFlightReport(LocalDate date, String fileName){
        List<Flight> flightList = getFlightDetails(date);
        if (flightList == null) {
            System.out.println("There are no flights with the date entered");
        } else {
            for (Flight f : flightList) {
                report = new Report();
                report.addToReport(f, fileName);
            }
        }
    }

    public void sendEmail(String toEmail, String fileName) {
        email = new Email();
        email.email(toEmail, fileName);
    }
}
