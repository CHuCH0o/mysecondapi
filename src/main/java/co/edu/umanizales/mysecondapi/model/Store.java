package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Store {
    private String code;       // Código de la tienda
    private String name;       // Nombre de la tienda
    private Location location; // Ubicación asociada (ciudad o municipio)
}
