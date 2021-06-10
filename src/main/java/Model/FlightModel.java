package Model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FlightModel {
    private int id;
    private String status;
    private LocationModel origin;
    private LocationModel destination;
    private Date departureTime;
    private Date arrivalTime;
    private String cancellationMotive;
    private List<String> incidentes;
    private String airline;
    private AircraftModel aircraft;
}
