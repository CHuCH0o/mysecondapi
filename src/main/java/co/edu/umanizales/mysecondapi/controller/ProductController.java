package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Product;
import co.edu.umanizales.mysecondapi.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    // Servicio que maneja la lógica de productos
    private final ProductService productService;

    // Constructor
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /products
    // Retorna todos los productos cargados desde el CSV
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // POST /products
    // Agrega un nuevo producto manualmente
    @PostMapping
    public String addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return "Producto agregado correctamente.";
    }
}
