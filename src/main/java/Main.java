import controller.AirportController;
import presentation.AirportView;

public class Main {

    public static void main(String[] args) {

        new AirportView(new AirportController()).displayMenu();
    }

}
