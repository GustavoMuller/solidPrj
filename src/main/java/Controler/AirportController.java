package Controler;

import Data.Aircraft;
import Data.Airport;
import Data.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class AirportController {
    private Airport airport;

    public AirportController(){
        airport = new Airport();
    }

    public void addAircraft(Aircraft a){
        airport.addAircraft(a);
    }

    public boolean hasFlights(){
        return !airport.getFlights().isEmpty();
    }

    public boolean flightExists(int id){
        for (Flight f : airport.getFlights())
            if (f.getId() == id) return true;
        return false;
    }

    public Flight getFlightDetails(int id){
        for (Flight f : airport.getFlights())
            if (f.getId() == id) return f;
        return null;
    }

    public List<Flight> getFlightsList(){
        return airport.getFlights();
    }

    public void addFlight(Flight f){
        airport.addFlight(f);
    }

    public void addFlightsFromFile(String fileName){
        //Read file and iterate the records (using a 'Report' class??)
        //Create new Flight obj for each record and pass it to airport.addFlight()
    }

    //Overloaded method to update a flight's status
    public void updateFlightStatus(int id, String status, LocalDateTime arrivalTime){
        Flight flight = getFlightDetails(id);
        flight.setStatus(status);
        flight.setArrivalTime(arrivalTime);
        airport.updateFlight(flight);
    }

    public void updateFlightStatus(int id, String status, String cancelMotive){
        Flight flight = getFlightDetails(id);
        flight.setStatus(status);
        flight.setCancellationMotive(cancelMotive);
        airport.updateFlight(flight);
    }

    public void updateFlightStatus(int id, String status, List<String> incidents){
        Flight flight = getFlightDetails(id);
        flight.setStatus(status);
        flight.setIncidents(incidents);
        airport.updateFlight(flight);
    }

    public void createAircraftReport(){

    }

    public void createFlightReport(){

    }
}
