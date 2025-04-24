package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Location;
import co.edu.umanizales.mysecondapi.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para exponer los servicios relacionados con ubicaciones
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Lista todas las ubicaciones
    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    // Busca ubicación por código exacto
    @GetMapping("/location/code/{code}")
    public Location getLocationByCode(@PathVariable String code) {
        return locationService.getLocationByCode(code);
    }

    // Busca ubicaciones por nombre parcial
    @GetMapping("/locations/name/{name}")
    public List<Location> getLocationsByName(@PathVariable String name) {
        return locationService.getLocationsByName(name);
    }

    // Busca ubicaciones por letras iniciales
    @GetMapping("/locations/il/{prefix}")
    public List<Location> getLocationsByInitials(@PathVariable String prefix) {
        return locationService.getLocationsByInitials(prefix);
    }

    // Lista municipios de un estado por código
    @GetMapping("/locations/state/{stateCode}")
    public List<Location> getLocationsByStateCode(@PathVariable String stateCode) {
        return locationService.getLocationsByStateCode(stateCode);
    }

    // Lista un municipio representativo por estado
    @GetMapping("/states")
    public List<Location> getStateRepresentatives() {
        return locationService.getStateRepresentatives();
    }

    // Devuelve solo código y nombre del estado
    @GetMapping("/state/{stateCode}")
    public Location getStateInfo(@PathVariable String stateCode) {
        return locationService.getStateInfo(stateCode);
    }

    // Lista cabeceras municipales
    @GetMapping("/capitals")
    public List<Location> getCapitals() {
        return locationService.getCapitals();
    }

    // Lista ubicaciones que empiezan y terminan con letras específicas
    @GetMapping("/{start}/{end}")
    public List<Location> getLocationsByStartAndEnd(@PathVariable String start, @PathVariable String end) {
        return locationService.getLocationsByStartAndEnd(start, end);
    }
}
