package controller;

import data.Airport;
import data.Flight;
import data.Report;

import java.time.LocalDate;
import java.util.List;

public class AirportController {
    private Airport airport;
    private Report report;

    public AirportController(){
        airport = new Airport();
    }

    public Flight getFLightDetails(int id){
        for (Flight f : airport.getFlights())
            if (f.getId() == id) return f;
        return null;
    }

    public Flight getFLightDetails(LocalDate date){
        for (Flight f : airport.getFlights())
            if (f.getDepartureTime() == date) return f;
        return null;
    }

    public List<Flight> getFlightsList(){
        return airport.getFlights();
    }

    public void addFlight(){
        //Read input data
        //Create new obj and pass it to airport.addFlight()
    }

    //Read file and iterate the records
    //Create new Flight obj for each record and pass it to airport.addFlight()
    public void addFlightsFromFile(String fileName){
        report = new Report();
        for (Flight f : report.readFile(fileName)) {
            airport.addFlight(f);
        }
    }

    //Overloaded method to update a flight's status
    public void updateFlight(int id, String status, LocalDate arrivalTime){
        Flight flight = getFLightDetails(id);
        flight.setStatus(status);
        flight.setArrivalTime(arrivalTime);
        airport.updateFlight(flight);
    }

    public void updateFlight(int id, String status, String cancelMotive){
        Flight flight = getFLightDetails(id);
        flight.setStatus(status);
        flight.setCancellationMotive(cancelMotive);
        airport.updateFlight(flight);
    }

    public void updateFlight(int id, String status, List<String> incidents){
        Flight flight = getFLightDetails(id);
        flight.setStatus(status);
        flight.setIncidents(incidents);
        airport.updateFlight(flight);
    }

    public void createAircraftReport(int id, String fileName){
        Flight flight = getFLightDetails(id);
        report = new Report();
        report.addToReport(flight, fileName);
    }

    public void createFlightReport(LocalDate date, String fileName){
        Flight flight = getFLightDetails(date);
        report = new Report();
        report.addToReport(flight, fileName);
    }
}
