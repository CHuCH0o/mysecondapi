package co.edu.umanizales.mysecondapi.controller;

import co.edu.umanizales.mysecondapi.model.Parameter;
import co.edu.umanizales.mysecondapi.model.TypeDocument;
import co.edu.umanizales.mysecondapi.model.TypeProduct;
import co.edu.umanizales.mysecondapi.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parameters")
public class ParameterController {

    // Servicio que gestiona los parámetros
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
    // Retorna los parámetros filtrados por tipo ("document", "product")
    @GetMapping("/type/{type}")
    public List<Parameter> getParametersByType(@PathVariable String type) {
        return parameterService.getParametersByType(type);
    }

    // GET /parameters/code/{code}
    // Retorna los parámetros que coincidan con el código
    @GetMapping("/code/{code}")
    public List<Parameter> getParametersByCode(@PathVariable String code) {
        return parameterService.getParametersByCode(code);
    }

    // POST /parameters
    // Agrega un nuevo parámetro según su tipo (product o document)
    @PostMapping
    public String addParameter(@RequestBody Map<String, String> paramData) {
        String type = paramData.get("type");
        String code = paramData.get("code");
        String description = paramData.get("description");

        if (type == null || code == null || description == null) {
            return "Error: Faltan campos obligatorios (type, code, description)";
        }

        Parameter newParam;
        if (type.equalsIgnoreCase("product")) {
            newParam = new TypeProduct(code, description);
        } else if (type.equalsIgnoreCase("document")) {
            newParam = new TypeDocument(code, description);
        } else {
            return "Error: Tipo no reconocido. Usa 'product' o 'document'.";
        }

        parameterService.addParameter(newParam);
        return "Parámetro agregado correctamente.";
    }
}
