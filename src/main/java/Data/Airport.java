package Data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Airport {
    private List<Flight> flights;
    private List<Aircraft> aircraftCatalog;
    private String localWeather;

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
