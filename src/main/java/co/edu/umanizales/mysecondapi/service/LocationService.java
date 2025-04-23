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

@Service
@Getter
public class LocationService {

    // Lista de todas las ubicaciones cargadas desde el archivo CSV
    private List<Location> locations;

    // Nombre del archivo CSV que se va a leer (se configura en application.properties)
    @Value("${locations_filename}")
    private String locationsFilename;

    // Este método se ejecuta automáticamente al iniciar el servicio
    // Carga los datos del archivo CSV y llena la lista de ubicaciones
    @PostConstruct
    public void readLocationsFromCSV() throws IOException, URISyntaxException {
        locations = new ArrayList<>();
        Path pathFile = Paths.get(ClassLoader.getSystemResource(locationsFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar la primera línea (encabezado)

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 5) {
                    locations.add(new Location(
                            line[0].trim(), // stateCode
                            line[1].trim(), // stateName
                            line[2].trim(), // townCode
                            line[3].trim(), // townName
                            line[4].trim()  // type
                    ));
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error leyendo el archivo CSV", e);
        }
    }
}
