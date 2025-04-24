package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Store;
import co.edu.umanizales.mysecondapi.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador que expone el listado de tiendas cargadas
@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // Endpoint que retorna todas las tiendas con su ubicación
    @GetMapping("/stores")
    public List<Store> getStores() {
        return storeService.getStores();
    }
}
