package Model;

import lombok.Data;

import java.util.List;

@Data
public class AirportModel {
    private List<FlightModel> flights;
    private List<AircraftModel> aircraftCatalog;
}
