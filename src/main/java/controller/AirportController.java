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

    public List<Flight> getFlightsList(){
        return airport.getFlights();
    }

    public void addFlight(){
        //Read input data
        //Create new obj and pass it to airport.addFlight()
    }

    public void addFlightsFromFile(String fileName){
        //Read file and iterate the records
        //Create new Flight obj for each record and pass it to airport.addFlight()
        /*
        Report fileReader = new Report();
        for (Flight f : fileReader.readFile(fileName)) {
            airport.addFlight(f);
        }*/
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

    public void createAircraftReport(int aircraftId){
        report = new Report();
        report.createReport(airport, aircraftId, java.time.LocalDate.now().toString());
    }

    public void createFlightReport(LocalDate date){
        report = new Report();
        report.createReport(airport, date, java.time.LocalDate.now().toString());
    }
}
