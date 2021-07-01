package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Weather;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class Airport {
    private List<Flight> flights;
    private List<Aircraft> aircraftCatalog;
    private String localWeather;
    private Location location;

    public Airport(Location location) {
        this.location = location;
        this.localWeather = Weather.getLocalWeather(this.location.getCity());
        aircraftCatalog = new ArrayList<>();
        flights = new ArrayList<>();
    }

    public void addAircraft(Aircraft a){
        aircraftCatalog.add(a);
    }

    public void addFlight(Flight flight){
        flights.add(flight);
    }

    public boolean hasFlights(){
        return !flights.isEmpty();
    }

    public Flight getFlight(int id){
        if (hasFlights())
            for (Flight f : flights)
                if (f.getId() == id) return f;
        return null;
    }

    public void updateFlight(Flight flight){
        if (hasFlights())
            for (Flight f : flights)
                if (f.getId() == flight.getId()){
                    flights.set(flights.indexOf(f), flight);
                    break;
                }
    }
}
