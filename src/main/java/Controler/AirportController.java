package Controler;

import Data.Airport;
import Data.Flight;

import java.util.ArrayList;
import java.util.Date;
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

    public void addFlightsFromFile(){
        //Read file and iterate the records
        //Create new Flight obj for each record and pass it to airport.addFlight()
    }

    //Overloaded method to update a flight's status
    public void updateFlight(String status, Date arrivalTime){

    }

    public void updateFlight(String status, String cancelMotive){

    }

    public void updateFlight(String status, List<String> incidents){

    }

    public void createAircraftReport(){

    }

    public void createFlightReport(){

    }
}
