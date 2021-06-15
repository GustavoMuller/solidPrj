package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    }

    public void addAircraft(Aircraft a){
        if (aircraftCatalog == null) aircraftCatalog = new ArrayList<>();
        aircraftCatalog.add(a);
    }

    public void addFlight(Flight flight){
        if (flights == null) flights = new ArrayList<>();
        flights.add(flight);
    }

    public void updateFlight(Flight flight){
        if (flights != null && flights.size() > 0){
            for (Flight f : flights) {
                if (f.getId() == flight.getId()){
                    flights.remove(f);
                    flights.add(flight);
                    break;
                }
            }
        }
    }
}
