package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Sale;
import co.edu.umanizales.mysecondapi.service.SaleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    // Servicio que maneja la lógica de ventas
    private final SaleService saleService;

    // Constructor
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    // GET /sales
    // Retorna todas las ventas registradas
    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    // POST /sales
    // Agrega una nueva venta recibida por JSON
    @PostMapping
    public String addSale(@RequestBody Sale sale) {
        saleService.addSale(sale);
        return "Venta agregada correctamente.";
    }
}
