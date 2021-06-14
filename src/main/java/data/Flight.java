package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
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
    private boolean arrival;
}
