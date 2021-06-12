package Data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Flight {
    private int id;
    private String status;
    private Location origin;
    private Location destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String cancellationMotive;
    private List<String> incidents;
    private String airline;
    private Aircraft aircraft;
}
