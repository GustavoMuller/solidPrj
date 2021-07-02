package controller;

import data.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirportController {
    private final Airport airport;
    private Report report;
    private Email email;

    public AirportController(Location location){
        airport = new Airport(location);
    }

    public void addAircraft(Aircraft a){
        airport.addAircraft(a);
    }

    public boolean hasFlights(){
        return airport.hasFlights();
    }

    public boolean flightExists(int id){
        return airport.getFlight(id) != null;
    }

    public Flight getFlightDetails(int id){
        return airport.getFlight(id);
    }

    public List<Flight> getFlightDetails(LocalDate date) {
        List<Flight> flightList = new ArrayList<>();
        if (hasFlights())
            for (Flight f : airport.getFlights())
                if (f.getArrivalTime().toLocalDate().equals(date)) flightList.add(f);
        return Collections.unmodifiableList(flightList);
    }

    public List<Flight> getFlightsList(){
        return Collections.unmodifiableList(airport.getFlights());
    }

    public Location getAirportLocation() {
        return airport.getLocation();
    }

    public void addFlight(Flight f){
        airport.addFlight(f);
    }

    public int addFlightsFromFile(String fileName){
        report = new Report();
        var count = 0;
        for (Flight f : report.readFile(fileName)) {
            airport.addFlight(f);
            count++;
        }

        return count;
    }

    //Already modified object is passed to be updated in list
    public void updateFlightStatus(Flight f){
        airport.updateFlight(f);
    }

    public void createAircraftReport(int id, String fileName){
        var flight = getFlightDetails(id);
        if (flight == null) {
            System.out.println("There are no flights with the ID entered");
        } else {
            report = new Report();
            report.addToReport(flight, fileName, airport.getLocalWeather());
        }
    }

    public void createFlightReport(LocalDate date, String fileName){
        List<Flight> flightList = getFlightDetails(date);
        if (flightList == null) {
            System.out.println("There are no flights with the date entered");
        } else {
            report = new Report();
            for (Flight f : flightList) {
                report.addToReport(f, fileName, airport.getLocalWeather());
            }
        }
    }

    public void sendEmail(String toEmail, String fileName) {
        email = new Email();
        email.email(toEmail, fileName);
    }
}
