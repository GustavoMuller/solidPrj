package Data;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Flight {
    private int id;
    private String status;
    private Location origin;
    private Location destination;
    private Date departureTime;
    private Date arrivalTime;
    private String cancellationMotive;
    private List<String> incidentes;
    private String airline;
    private Aircraft aircraft;
}
