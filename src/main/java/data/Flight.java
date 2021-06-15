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
    private boolean isArrival;

    public Flight(int id, String status, Location origin, Location destination, LocalDateTime departureTime,
                  LocalDateTime arrivalTime, String airline, Aircraft aircraft, boolean isArrival) {
        this.id = id;
        this.status = status;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airline = airline;
        this.aircraft = aircraft;
        this.isArrival = isArrival;
    }

    @Override
    public String toString() {
        var output = "\n| id: " + id + " | status: " + status + " | origin : " + origin.getCountry() + ", " +
                origin.getCity() + " | destination: " + destination.getCountry() + ", " +
                destination.getCity() + " | departure: " + departureTime + " | arrival: " + arrivalTime +
                " | airline: " + airline + " | isArrival: " + isArrival;

        if(status.equals("Cancelled")) output += " | cancellation motive: " + cancellationMotive;

        return output;

    }
}
