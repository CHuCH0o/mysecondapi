package co.edu.umanizales.mysecondapi.service;

import co.edu.umanizales.mysecondapi.model.Location;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Servicio para gestionar la carga y consulta de ubicaciones desde un archivo CSV
@Service
@Getter
public class LocationService {

    private List<Location> locations = new ArrayList<>();

    @Value("${locations_filename}")
    private String locationsFilename;

    @PostConstruct
    public void readLocationsFromCSV() throws IOException, URISyntaxException {
        Path pathFile = Paths.get(ClassLoader.getSystemResource(locationsFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar encabezado

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 5) {
                    Location location = new Location(
                            line[0].trim(),
                            line[1].trim(),
                            line[2].trim(),
                            line[3].trim(),
                            line[4].trim()
                    );
                    locations.add(location);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Location> getAllLocations() {
        return locations;
    }

    public Location getLocationByCode(String code) {
        for (Location location : locations) {
            if (location.getTownCode().equals(code)) {
                return location;
            }
        }
        return null;
    }

    public List<Location> getLocationsByName(String name) {
        List<Location> result = new ArrayList<>();
        for (Location location : locations) {
            if (location.getTownName().toLowerCase().contains(name.toLowerCase())) {
                result.add(location);
            }
        }
        return result;
    }

    public List<Location> getLocationsByInitials(String prefix) {
        List<Location> result = new ArrayList<>();
        for (Location location : locations) {
            if (location.getTownName().toLowerCase().startsWith(prefix.toLowerCase())) {
                result.add(location);
            }
        }
        return result;
    }

    public List<Location> getLocationsByStateCode(String stateCode) {
        List<Location> result = new ArrayList<>();
        for (Location location : locations) {
            if (location.getStateCode().equals(stateCode)) {
                result.add(location);
            }
        }
        return result;
    }

    public List<Location> getStateRepresentatives() {
        List<Location> result = new ArrayList<>();
        List<String> addedStates = new ArrayList<>();
        for (Location location : locations) {
            if (location.getTownCode().endsWith("001") && !addedStates.contains(location.getStateCode())) {
                result.add(location);
                addedStates.add(location.getStateCode());
            }
        }
        return result;
    }

    public Location getStateInfo(String stateCode) {
        for (Location location : locations) {
            if (location.getStateCode().equals(stateCode)) {
                return location;
            }
        }
        return null;
    }

    public List<Location> getCapitals() {
        List<Location> result = new ArrayList<>();
        for (Location location : locations) {
            if (location.getTownCode().endsWith("001") && location.getType().equalsIgnoreCase("Municipio")) {
                result.add(location);
            }
        }
        return result;
    }

    public List<Location> getLocationsByStartAndEnd(String start, String end) {
        List<Location> result = new ArrayList<>();
        for (Location location : locations) {
            String name = location.getTownName().toLowerCase();
            if (name.startsWith(start.toLowerCase()) && name.endsWith(end.toLowerCase())) {
                result.add(location);
            }
        }
        return result;
    }
}
