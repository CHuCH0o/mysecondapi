package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private String code;       // Código único de la tienda
    private String name;       // Nombre de la tienda
    private Location city;     // Ciudad (referencia a una instancia de Location)
}
