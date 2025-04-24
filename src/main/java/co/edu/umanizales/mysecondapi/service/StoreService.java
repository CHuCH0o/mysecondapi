package co.edu.umanizales.mysecondapi.service;

import co.edu.umanizales.mysecondapi.model.Store;
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

// Servicio para cargar y consultar tiendas desde un CSV
@Service
@Getter
public class StoreService {

    private List<Store> stores = new ArrayList<>();

    @Value("${stores_filename}")
    private String storesFilename;

    private final LocationService locationService;

    public StoreService(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostConstruct
    public void loadStoresFromCSV() throws IOException, URISyntaxException {
        Path pathFile = Paths.get(ClassLoader.getSystemResource(storesFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar encabezado

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 3) {
                    String storeCode = line[0].trim();
                    String storeName = line[1].trim();
                    String townCode = line[2].trim();
                    Store store = new Store(storeCode, storeName, locationService.getLocationByCode(townCode));
                    stores.add(store);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
