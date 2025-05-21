package co.edu.umanizales.mysecondapi.service;

import co.edu.umanizales.mysecondapi.model.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class SaleService {

    // Lista donde se almacenan todas las ventas
    private final List<Sale> sales = new ArrayList<>();

    // Nombre del archivo CSV con las ventas (definido en application.properties)
    @Value("${sales_filename}")
    private String salesFilename;

    // Servicios para buscar tienda, vendedor y producto
    private final StoreService storeService;
    private final SellerService sellerService;
    private final ProductService productService;

    // Constructor
    public SaleService(StoreService storeService, SellerService sellerService, ProductService productService) {
        this.storeService = storeService;
        this.sellerService = sellerService;
        this.productService = productService;
    }

    // Método que se ejecuta al iniciar para cargar las ventas desde el CSV
    @PostConstruct
    public void loadSalesFromCSV() throws IOException, URISyntaxException {
        Path pathFile = Paths.get(ClassLoader.getSystemResource(salesFilename).toURI());

        try (CSVReader csvReader = new CSVReader(new FileReader(pathFile.toString()))) {
            String[] line;
            csvReader.skip(1); // Saltar encabezado del archivo

            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 5) {
                    String storeCode = line[0].trim();     // Código de tienda
                    String sellerId = line[1].trim();      // Identificación del vendedor
                    LocalDate dateSale = LocalDate.parse(line[2].trim()); // Fecha de la venta
                    String productsText = line[4].trim();  // Productos en formato "P001:2;P002:1"

                    // Buscar la tienda por su código
                    Store store = null;
                    for (Store s : storeService.getStores()) {
                        if (s.getCode().equalsIgnoreCase(storeCode)) {
                            store = s;
                            break;
                        }
                    }

                    // Buscar el vendedor por su identificación
                    Seller seller = null;
                    for (Seller sel : sellerService.getSellers()) {
                        if (sel.getIdentification().equalsIgnoreCase(sellerId)) {
                            seller = sel;
                            break;
                        }
                    }

                    // Lista de productos vendidos en esta venta
                    String[] productEntries = productsText.split(";");
                    List<ProductSale> productSales = new ArrayList<>();

                    for (String entry : productEntries) {
                        String[] parts = entry.split(":");
                        if (parts.length == 2) {
                            String productCode = parts[0].trim();
                            int quantitySold = Integer.parseInt(parts[1].trim());

                            // Buscar el producto por código usando ProductService
                            Product matchedProduct = productService.getProductByCode(productCode);

                            // Si el producto existe, se agrega a la lista
                            if (matchedProduct != null) {
                                ProductSale ps = new ProductSale(matchedProduct, quantitySold);
                                productSales.add(ps);
                            }
                        }
                    }

                    // Calcular la cantidad total de unidades vendidas
                    int totalQuantity = 0;
                    for (ProductSale ps : productSales) {
                        totalQuantity += ps.getQuantity();
                    }

                    // Crear y agregar la venta si todo está completo
                    if (store != null && seller != null && !productSales.isEmpty()) {
                        Sale sale = new Sale(store, seller, dateSale, totalQuantity, productSales);
                        sales.add(sale);
                    }
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo de ventas", e);
        }
    }

    // Método para obtener todas las ventas
    public List<Sale> getAllSales() {
        return sales;
    }

    // Método para agregar una venta manualmente (POST)
    public void addSale(Sale sale) {
        sales.add(sale);
    }
}
