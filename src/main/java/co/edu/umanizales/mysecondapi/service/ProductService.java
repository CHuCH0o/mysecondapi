package co.edu.umanizales.mysecondapi.service;

import co.edu.umanizales.mysecondapi.model.Parameter;
import co.edu.umanizales.mysecondapi.model.Product;
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
public class ProductService {

    // Lista para almacenar los productos
    private final List<Product> products = new ArrayList<>();

    // Nombre del archivo CSV (desde application.properties)
    @Value("${products_filename}")
    private String productsFilename;

    // Servicio para obtener el tipo de producto
    private final ParameterService parameterService;

    // Constructor
    public ProductService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    // Carga automática desde products.csv al iniciar
    @PostConstruct
    public void loadProductsFromCSV() throws IOException, URISyntaxException {
        Path pathFile = Paths.get(ClassLoader.getSystemResource(productsFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar encabezado

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 4) {
                    String code = line[0].trim();
                    double price = Double.parseDouble(line[1].trim());
                    int stock = Integer.parseInt(line[2].trim());
                    String typeCode = line[3].trim();

                    // Buscar el tipo de producto correspondiente
                    TypeProduct typeProduct = null;
                    for (Parameter p : parameterService.getParametersByCode(typeCode)) {
                        if (p instanceof TypeProduct) {
                            typeProduct = (TypeProduct) p;
                            break;
                        }
                    }

                    // Si se encuentra el tipo, se crea el producto
                    if (typeProduct != null) {
                        Product product = new Product(code, price, stock, typeProduct);
                        products.add(product);
                    }
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo de productos", e);
        }
    }

    // Retorna todos los productos
    public List<Product> getAllProducts() {
        return products;
    }

    // Agrega un producto manualmente
    public void addProduct(Product product) {
        products.add(product);
    }
}
