package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    private String identification;
    private Parameter typeDoc; // Será una instancia de TypeDoc
    private String name;
    private String lastName;
    private byte age;
    private Location city;
}
