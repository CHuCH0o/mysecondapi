package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    private String identification;           // Número de identificación del vendedor
    private TypeDocument typeDocument;       // Tipo de documento (CC, NIT)
    private String name;                     // Nombre del vendedor
    private String lastName;                 // Apellido del vendedor
    private byte age;                        // Edad
    private Location city;                   // Ciudad de residencia
}
