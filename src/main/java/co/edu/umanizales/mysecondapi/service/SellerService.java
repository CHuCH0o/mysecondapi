package co.edu.umanizales.mysecondapi.service;

import co.edu.umanizales.mysecondapi.model.Location;
import co.edu.umanizales.mysecondapi.model.Parameter;
import co.edu.umanizales.mysecondapi.model.Seller;
import co.edu.umanizales.mysecondapi.model.TypeDocument;
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
public class SellerService {

    // Lista para guardar los vendedores cargados desde el CSV
    private final List<Seller> sellers = new ArrayList<>();

    // Nombre del archivo CSV, tomado desde application.properties
    @Value("${sellers_filename}")
    private String sellersFilename;

    // Servicios para obtener ciudades y tipos de documento
    private final LocationService locationService;
    private final ParameterService parameterService;

    // Constructor
    public SellerService(LocationService locationService, ParameterService parameterService) {
        this.locationService = locationService;
        this.parameterService = parameterService;
    }

    // Carga automática de vendedores desde el archivo sellers.csv
    @PostConstruct
    public void loadSellersFromCSV() throws IOException, URISyntaxException {
        Path pathFile = Paths.get(ClassLoader.getSystemResource(sellersFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar encabezado

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 6) {
                    String id = line[0].trim();
                    String typeDocCode = line[1].trim();
                    String name = line[2].trim();
                    String lastName = line[3].trim();
                    byte age = Byte.parseByte(line[4].trim());
                    String townCode = line[5].trim();

                    // Buscar el tipo de documento correcto
                    TypeDocument typeDocument = null;
                    for (Parameter param : parameterService.getParametersByCode(typeDocCode)) {
                        if (param instanceof TypeDocument) {
                            typeDocument = (TypeDocument) param;
                            break;
                        }
                    }

                    // Buscar la ciudad por código
                    Location location = locationService.getLocationByCode(townCode);

                    // Si ambos se encontraron, se crea el objeto Seller
                    if (typeDocument != null && location != null) {
                        Seller seller = new Seller(id, typeDocument, name, lastName, age, location);
                        sellers.add(seller);
                    }
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo de vendedores", e);
        }
    }

    // Retorna la lista completa de vendedores
    public List<Seller> getAllSellers() {
        return sellers;
    }

    // Agrega un vendedor manualmente
    public void addSeller(Seller seller) {
        sellers.add(seller);
    }
}
