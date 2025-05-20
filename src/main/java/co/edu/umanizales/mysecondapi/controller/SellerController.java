package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Seller;
import co.edu.umanizales.mysecondapi.service.SellerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    // Servicio que gestiona los vendedores
    private final SellerService sellerService;

    // Constructor con inyección de dependencias
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    // GET /sellers
    // Retorna todos los vendedores cargados desde el CSV
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    // POST /sellers
    // Agrega un nuevo vendedor manualmente (por JSON en Postman)
    @PostMapping
    public String addSeller(@RequestBody Seller seller) {
        sellerService.addSeller(seller);
        return "Vendedor agregado correctamente.";
    }
}
