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

    // Lista para guardar los productos cargados
    private final List<Product> products = new ArrayList<>();

    // Nombre del archivo CSV con los productos
    @Value("${products_filename}")
    private String productsFilename;

    // Servicio para obtener el tipo de producto desde los parámetros
    private final ParameterService parameterService;

    // Constructor
    public ProductService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    // Carga los productos desde el archivo CSV al iniciar la aplicación
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

                    // Crear y agregar el producto si se encontró su tipo
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

    // Busca un producto por su código
    public Product getProductByCode(String code) {
        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null; // Si no se encuentra
    }
}
