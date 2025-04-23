package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {

    // Código del estado o departamento (ej: 05 para Antioquia)
    private String stateCode;

    // Nombre del estado o departamento (ej: ANTIOQUIA)
    private String stateName;

    // Código del municipio (ej: 05001 para Medellín)
    private String townCode;

    // Nombre del municipio (ej: MEDELLÍN)
    private String townName;

    // Tipo de ubicación (por ejemplo: Municipio, Isla, etc.)
    private String type;
}
