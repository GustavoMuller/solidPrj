package data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Report {

    /* Create a new excel file with the current date */
    public void createFile(String name) {
        try (Workbook workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet("Flights " + java.time.LocalDate.now());
            var row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Airline");
            row.createCell(2).setCellValue("Aircraft");
            row.createCell(3).setCellValue("Status");
            row.createCell(4).setCellValue("Origin");
            row.createCell(5).setCellValue("Destination");
            row.createCell(6).setCellValue("Departure Time");
            row.createCell(7).setCellValue("Arrival Time");
            row.createCell(8).setCellValue("Cancel Reason");
            row.createCell(9).setCellValue("Incidents");

            var fileOut = new FileOutputStream("src/main/resources/" + name + ".xlsx"); /* fix with yaml later */
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Read report and add flights to file */
    /* Currently only prints the info on file, waiting on a method to create a flight object */
    public List<Flight> readFile(String name) {
        DataFormatter format = new DataFormatter();
        Flight flight;
        ArrayList<Flight> flightList = new ArrayList<>();
        var i = 1;
        try (var workbook = WorkbookFactory.create(new File("src/main/resources/" + name + ".xlsx"))) {
            var sheet = workbook.getSheetAt(0);

            while (sheet.getRow(i) != null) {
                var row = sheet.getRow(i);
                flight = new Flight();
                flight.setId((int)row.getCell(0).getNumericCellValue());
                flight.setAirline(row.getCell(1).toString());
                flight.setAircraft(new Aircraft(row.getCell(2).toString(), 1000, 100f)); //MODIFICAR
                flight.setStatus(row.getCell(3).toString());
                flight.setOrigin(new Location(row.getCell(4).toString(), row.getCell(4).toString()));
                flight.setDestination(new Location(row.getCell(5).toString(), row.getCell(5).toString()));
                /*flight.setDepartureTime(LocalDate.now());   // Work in progress cell 6
                flight.setArrivalTime(LocalDate.now());     // Work in progress cell 7
                flight.setDepartureTime(LocalDate.now());   // Work in progress cell 6
                flight.setArrivalTime(LocalDate.now());     // Work in progress cell 7*/
                flight.setCancellationMotive(row.getCell(8).toString());
                flight.setIncidents(new ArrayList<>(Arrays.asList(row.getCell(9).toString())));
                i++;
                flightList.add(flight);
            }
            return flightList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /* Add Flight to the report */
    public void addToReport(Flight flight, String fileName) {
        try {
            var file = new File("src/main/resources/" + fileName + ".xlsx");
            if (!(file.exists())) {
                createFile(fileName);
            }
            var workbook = WorkbookFactory.create(file);
            var sheet = workbook.getSheetAt(0);
            var row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(flight.getId());
            row.createCell(1).setCellValue(flight.getAirline());
            row.createCell(2).setCellValue(flight.getAircraft().getModel());
            row.createCell(3).setCellValue(flight.getStatus());
            row.createCell(4).setCellValue(flight.getOrigin().getCity());
            row.createCell(5).setCellValue(flight.getDestination().getCity());
            /*row.createCell(6).setCellValue(flight.getDepartureTime());
            row.createCell(7).setCellValue(flight.getArrivalTime());*/
            row.createCell(8).setCellValue(flight.getCancellationMotive());
//            row.createCell(9).setCellValue(flight.getIncidents());  /* Work in progress */

            var fileOut = new FileOutputStream(fileName + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}