package co.edu.umanizales.mysecondapi.service;

import co.edu.umanizales.mysecondapi.model.Parameter;
import co.edu.umanizales.mysecondapi.model.TypeDocument;
import co.edu.umanizales.mysecondapi.model.TypeProduct;
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
public class ParameterService {

    // Lista de parámetros cargados desde el CSV
    private final List<Parameter> parameters = new ArrayList<>();

    // Nombre del archivo CSV definido en application.properties
    @Value("${parameters_filename}")
    private String parametersFilename;

    // Carga automática del CSV al iniciar la aplicación
    @PostConstruct
    public void loadParametersFromCSV() throws IOException, URISyntaxException {
        Path pathFile = Paths.get(ClassLoader.getSystemResource(parametersFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar la primera línea (encabezado)

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 3) {
                    String type = line[0].trim();
                    String code = line[1].trim();
                    String description = line[2].trim();

                    // Cargar documentos
                    if (type.equalsIgnoreCase("document")) {
                        parameters.add(new TypeDocument(code, description));
                    }
                    // Cargar productos
                    else if (type.equalsIgnoreCase("product")) {
                        parameters.add(new TypeProduct(code, description));
                    }
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo de parámetros", e);
        }
    }

    // Retorna todos los parámetros cargados
    public List<Parameter> getAllParameters() {
        return parameters;
    }

    // Filtra los parámetros por tipo (document o product)
    public List<Parameter> getParametersByType(String type) {
        List<Parameter> result = new ArrayList<>();
        for (Parameter p : parameters) {
            if (type.equalsIgnoreCase("document") && p instanceof TypeDocument) {
                result.add(p);
            } else if (type.equalsIgnoreCase("product") && p instanceof TypeProduct) {
                result.add(p);
            }
        }
        return result;
    }

    // Retorna el parámetro cuyo código coincida (como lista de 1 elemento)
    public List<Parameter> getParametersByCode(String code) {
        List<Parameter> result = new ArrayList<>();
        for (Parameter p : parameters) {
            if (p.getCode().equalsIgnoreCase(code)) {
                result.add(p);
            }
        }
        return result;
    }

    // Agrega un nuevo parámetro a la lista
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }
}
