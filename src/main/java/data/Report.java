package data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Report {

    private static final String FILE_PATH = "src/main/resources/";
    private static final String EXT = ".xlsx";

    public void createFile(String name) {
        try (Workbook workbook = new XSSFWorkbook()) {
            var filePath = FILE_PATH + name + EXT;
            var sheet = workbook.createSheet();
            var row = sheet.createRow(0);
            addTitles(row);
            var fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTitles(Row row) {
        var i = 0;
        for(String title : getTitles()) {
            row.createCell(i).setCellValue(title);
            i++;
        }
    }

    private String[] getTitles() {
        return new String[] {"ID", "Airline", "Aircraft", "Aircraft Capacity", "Aircraft Range", "Status", "Origin Country", "Origin City",
                "Destination Country", "Destination City", "Departure Date", "Departure Time", "Arrival Date", "Arrival Time", "Cancel Reason", "Incidents", "Type"};
    }

    public List<Flight> readFile(String name) {
        var filePath = FILE_PATH + name + EXT;
        ArrayList<Flight> flightList = new ArrayList<>();
        var i = 1;
        try (var workbook = WorkbookFactory.create(new File(filePath))) {
            var sheet = workbook.getSheetAt(0);

            while (sheet.getRow(i) != null) {
                var row = sheet.getRow(i);
                var flight = new Flight();
                setId(flight, row);
                setAirline(flight, row);
                setAircraft(flight, row);
                setStatus(flight, row);
                setOrigin(flight, row);
                setDestination(flight, row);
                setDepartureTime(flight, row);
                setArrivalTime(flight, row);
                setCancelMotive(flight, row);
                setIncidents(flight, row);
                setArrival(flight, row);
                i++;
                flightList.add(flight);
            }
            return flightList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    private void setId(Flight flight, Row row) {
        flight.setId((int)row.getCell(0).getNumericCellValue());
    }

    private void setAirline(Flight flight, Row row) {
        flight.setAirline(row.getCell(1).toString());
    }

    private void setAircraft(Flight flight, Row row) {
        flight.setAircraft(new Aircraft(row.getCell(2).toString(), (int)row.getCell(3).getNumericCellValue(), (float)row.getCell(4).getNumericCellValue()));
    }

    private void setStatus(Flight flight, Row row) {
        flight.setStatus(Enum.valueOf(FlightStatus.class, row.getCell(5).toString()));
    }

    private void setOrigin(Flight flight, Row row) {
        flight.setOrigin(new Location(row.getCell(6).toString(), row.getCell(7).toString()));
    }

    private void setDestination(Flight flight, Row row) {
        flight.setDestination(new Location(row.getCell(8).toString(), row.getCell(9).toString()));
    }

    private void setDepartureTime(Flight flight, Row row) {
        flight.setDepartureTime(LocalDateTime.of(row.getCell(10).getLocalDateTimeCellValue().toLocalDate(), row.getCell(11).getLocalDateTimeCellValue().toLocalTime()));
    }

    private void setArrivalTime(Flight flight, Row row) {
        flight.setArrivalTime(LocalDateTime.of(row.getCell(12).getLocalDateTimeCellValue().toLocalDate(), row.getCell(13).getLocalDateTimeCellValue().toLocalTime()));
    }

    private void setCancelMotive(Flight flight, Row row) {
        flight.setCancellationMotive(row.getCell(14).toString());
    }

    private void setIncidents(Flight flight, Row row) {
        var incident = row.getCell(15).toString();
        List<String> incidents = Arrays.asList(incident.split(","));
        flight.setIncidents(incidents);
    }

    private void setArrival(Flight flight, Row row) {
        flight.setArrival(row.getCell(16).getBooleanCellValue());
    }

    public void addToReport(Flight flight, String fileName, String weather) {
        var filePath = FILE_PATH + fileName + EXT;
        try {
            var file = new File(filePath);
            if (!file.exists()) {
                createFile(fileName);
            }
            var workbook = WorkbookFactory.create(file);
            var sheet = workbook.getSheetAt(0);
            var row = sheet.createRow(sheet.getLastRowNum() + 1);

            String incidents = (flight.getIncidents() == null) ? "No incidents" : flight.getIncidents().stream().collect(Collectors.joining(","));
            row.createCell(0).setCellValue(flight.getId());
            row.createCell(1).setCellValue(flight.getAirline());
            row.createCell(2).setCellValue(flight.getAircraft().getModel());
            row.createCell(3).setCellValue(flight.getAircraft().getPassengerCapacity());
            row.createCell(4).setCellValue(flight.getAircraft().getRange());
            row.createCell(5).setCellValue(flight.getStatus().toString());
            row.createCell(6).setCellValue(flight.getOrigin().getCountry());
            row.createCell(7).setCellValue(flight.getOrigin().getCity());
            row.createCell(8).setCellValue(flight.getDestination().getCountry());
            row.createCell(9).setCellValue(flight.getDestination().getCity());
            row.createCell(10).setCellValue(flight.getDepartureTime().toLocalDate().toString());
            row.createCell(11).setCellValue(flight.getDepartureTime().toLocalTime().toString());
            row.createCell(12).setCellValue(flight.getArrivalTime().toLocalDate().toString());
            row.createCell(13).setCellValue(flight.getArrivalTime().toLocalTime().toString());
            row.createCell(14).setCellValue(flight.getCancellationMotive());
            row.createCell(15).setCellValue(incidents);
            row.createCell(16).setCellValue(flight.isArrival() ? "Arrival" : "Departure");

            var fileOut = new FileOutputStream(fileName + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}