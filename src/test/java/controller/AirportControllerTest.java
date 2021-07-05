package controller;

import data.Flight;
import data.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirportControllerTest {
    private AirportController controller;

    @BeforeEach
    public void setup(){
        controller = new AirportController(new Location("El Salvador","San Salvador"));
    }

    @Test
    public void shouldInitializeValues(){
        Assertions.assertNotNull(controller.getFlightsList());
    }

    @Test
    public void shouldAddFlight(){
        controller.addFlight(new Flight());
        Assertions.assertTrue(controller.hasFlights());
    }

    @Test
    public void shouldNotModifyList(){
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            controller.getFlightsList().add(new Flight());
        });
    }
}
