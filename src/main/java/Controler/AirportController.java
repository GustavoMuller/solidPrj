package Controler;

import Data.Airport;
import Data.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class AirportController {
    private Airport airport;

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
    public void updateFlightStatus(int id, String status, LocalDateTime arrivalTime){
        Flight flight = getFLightDetails(id);
        flight.setStatus(status);
        flight.setArrivalTime(arrivalTime);
        airport.updateFlight(flight);
    }

    public void updateFlightStatus(int id, String status, String cancelMotive){
        Flight flight = getFLightDetails(id);
        flight.setStatus(status);
        flight.setCancellationMotive(cancelMotive);
        airport.updateFlight(flight);
    }

    public void updateFlightStatus(int id, String status, List<String> incidents){
        Flight flight = getFLightDetails(id);
        flight.setStatus(status);
        flight.setIncidents(incidents);
        airport.updateFlight(flight);
    }

    public void createAircraftReport(){

    }

    public void createFlightReport(){

    }
}
