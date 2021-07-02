import controller.AirportController;
import data.Location;
import presentation.AirportView;

public class Main {

    public static void main(String[] args) {

        new AirportView(new AirportController(new Location("El Salvador", "San Salvador"))).displayMenu();
    }

}
