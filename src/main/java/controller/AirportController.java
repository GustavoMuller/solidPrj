package controller;

import data.Aircraft;
import data.Airport;
import data.Flight;
import data.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AirportController {
    private final Airport airport;

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

    public List<Flight> getFlightDetails(LocalDateTime date){
        ArrayList<Flight> flights = new ArrayList<>();
        if (hasFlights())
            for (Flight f : airport.getFlights())
                if (f.getArrivalTime().toLocalDate() == date.toLocalDate()) flights.add(f);
        return flights;
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
        //Read file and iterate the records (using a 'Report' class??)
        //Create new Flight obj for each record and pass it to airport.addFlight()
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

    public void createAircraftReport(){

    }

    public void createFlightReport(){

    }
}
