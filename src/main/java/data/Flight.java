package data;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Flight {
    private int id;
    private String airline;
    private Aircraft aircraft;
    private String status;
    private Location origin;
    private Location destination;
    private LocalDate departureTime;
    private LocalDate arrivalTime;
    private String cancellationMotive;
    private List<String> incidents;
}
