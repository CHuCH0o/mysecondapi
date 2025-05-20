package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Parameter;
import co.edu.umanizales.mysecondapi.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parameters")
public class ParameterController {

    // Inyección del servicio que gestiona los parámetros
    private final ParameterService parameterService;

    // Constructor
    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    // GET /parameters
    // Retorna todos los parámetros cargados (TypeDocument y TypeProduct)
    @GetMapping
    public List<Parameter> getAllParameters() {
        return parameterService.getAllParameters();
    }

    // GET /parameters/type/{type}
    // Retorna los parámetros filtrados por tipo (ejemplo: "document", "product")
    @GetMapping("/type/{type}")
    public List<Parameter> getParametersByType(@PathVariable String type) {
        return parameterService.getParametersByType(type);
    }

    // GET /parameters/code/{code}
    // Retorna el parámetro que coincide con el código
    @GetMapping("/code/{code}")
    public List<Parameter> getParametersByCode(@PathVariable String code) {
        return parameterService.getParametersByCode(code);
    }

    // POST /parameters
    // Agrega un nuevo parámetro a la lista en memoria
    @PostMapping
    public String addParameter(@RequestBody Parameter parameter) {
        parameterService.addParameter(parameter);
        return "Parámetro agregado correctamente.";
    }
}
