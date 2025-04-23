package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.MysecondapiApplication;
import co.edu.umanizales.mysecondapi.model.Location;
import co.edu.umanizales.mysecondapi.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Lista todas las ubicaciones (municipios y departamentos)
    @GetMapping("/locations")
    public List<Location> getLocations() {
        return locationService.getLocations();
    }

    // Busca una ubicación por su código de municipio (ej: 05001)
    @GetMapping("/location/code/{code}")
    public Location getLocationByCode(@PathVariable String code) {
        return locationService.getLocations().stream()
                .filter(loc -> loc.getTownCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    // Busca ubicaciones que contienen el nombre indicado (sin tildes, sin importar mayúsculas)
    @GetMapping("/locations/name/{name}")
    public List<Location> getLocationsByName(@PathVariable String name) {
        String normalizedQuery = MysecondapiApplication.normalize(name);
        return locationService.getLocations().stream()
                .filter(loc -> MysecondapiApplication.normalize(loc.getTownName()).contains(normalizedQuery))
                .toList();
    }

    // Lista ubicaciones cuyo nombre comienza con el texto indicado (sin tildes)
    @GetMapping("/locations/il/{prefix}")
    public List<Location> getLocationsByInitialLetters(@PathVariable String prefix) {
        String normalizedPrefix = MysecondapiApplication.normalize(prefix);
        return locationService.getLocations().stream()
                .filter(loc -> MysecondapiApplication.normalize(loc.getTownName()).startsWith(normalizedPrefix))
                .toList();
    }

    // Lista todos los municipios de un estado dado (por código de estado)
    @GetMapping("/locations/state/{code}")
    public List<Location> getLocationsByStateCode(@PathVariable String code) {
        return locationService.getLocations().stream()
                .filter(loc -> loc.getStateCode().equals(code))
                .toList();
    }

    // Lista todos los estados con solo su código y nombre (usando el primer municipio como referencia)
    @GetMapping("/states")
    public List<Map<String, String>> getStates() {
        return locationService.getLocations().stream()
                .filter(loc -> loc.getTownCode().endsWith("001"))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Location::getStateCode,
                                loc -> loc,
                                (existing, replacement) -> existing
                        ),
                        map -> map.values().stream()
                                .map(loc -> {
                                    Map<String, String> stateInfo = new HashMap<>();
                                    stateInfo.put("stateCode", loc.getStateCode());
                                    stateInfo.put("stateName", loc.getStateName());
                                    return stateInfo;
                                })
                                .toList()
                ));
    }

    // Devuelve solo el código y nombre de un estado específico
    @GetMapping("/state/{code}")
    public Map<String, String> getStateByCode(@PathVariable String code) {
        return locationService.getLocations().stream()
                .filter(loc -> loc.getStateCode().equals(code))
                .findFirst()
                .map(loc -> {
                    Map<String, String> result = new HashMap<>();
                    result.put("stateCode", loc.getStateCode());
                    result.put("stateName", loc.getStateName());
                    return result;
                })
                .orElse(null);
    }

    // Lista todas las cabeceras municipales (municipios con código terminado en 001 y tipo Municipio)
    @GetMapping("/capitals")
    public List<Location> getCapitals() {
        return locationService.getLocations().stream()
                .filter(loc -> loc.getTownCode().endsWith("001") &&
                        MysecondapiApplication.normalize(loc.getType()).contains("municipio"))
                .toList();
    }
}

