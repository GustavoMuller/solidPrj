package Presentation;

import Controler.AirportController;

public class AirportView {
    private AirportController controller;

    public AirportView(){
        controller = new AirportController();
    }

    public void displayMenu(){
        System.out.println("""
                =============== AIRPORT ===============
                
                Menu:
                1. Display flights list
                2. Display flight details
                3. Add flight
                4. Add flights from file (xlsx)
                5. Update flight status
                6. Reports
                """);
    }

    public void displayFlightsList(){
        controller.getFlightsList();
        //iterar y mostrar los vuelos en
    }
}
