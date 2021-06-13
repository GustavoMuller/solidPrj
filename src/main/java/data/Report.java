package data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

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
    public void readReport(String name) {
        var sb = new StringBuilder();
        try (var workbook = WorkbookFactory.create(new File("src/main/resources/" + name + ".xlsx"))) {
            var sheet = workbook.getSheetAt(0);
            for (Row r : sheet) {
                for (Cell c : r) {
                    if (c != null) {
                        sb.append(c).append(" ");
                    }
                }
                System.out.println(sb);
                sb = new StringBuilder();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Add Flight to the report */
    public void addToReport(Flight flight, String name) {

        try {
            var file = new File("src/main/resources/" + name + ".xlsx");
            if (!(file.exists())) {
                createFile(name);
            }
            var workbook = WorkbookFactory.create(file);
            var sheet = workbook.getSheetAt(0);
            var r = sheet.createRow(sheet.getLastRowNum() + 1);
            r.createCell(0).setCellValue(flight.getId());
            r.createCell(1).setCellValue(flight.getAirline());
            r.createCell(2).setCellValue(flight.getAircraft().getModel());
            r.createCell(3).setCellValue(flight.getStatus());
            r.createCell(4).setCellValue(flight.getOrigin().getCity());
            r.createCell(5).setCellValue(flight.getDestination().getCity());
            r.createCell(6).setCellValue(flight.getDepartureTime());
            r.createCell(7).setCellValue(flight.getArrivalTime());
            r.createCell(8).setCellValue(flight.getCancellationMotive());
//            r.createCell(9).setCellValue(flight.getIncidents());  /* Work in progress */

            var fileOut = new FileOutputStream(name + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Create a report based on an aircraft ID */
    public void createReport(Airport airport, int aircraftId, String fileName) {
        if (airport.getFlights() != null) {
            for (Flight f: airport.getFlights()) {
                if (f.getId() == (aircraftId)) {
                    addToReport(f, fileName);
                }
            }
        }
    }

    /* Create a report based on a date */
    public void createReport(Airport airport, LocalDate date, String fileName) {
        if (airport.getFlights() != null) {
            for (Flight f: airport.getFlights()) {
                if (f.getDepartureTime() == (date)) {
                    addToReport(f, fileName);
                }
            }
        }
    }
}