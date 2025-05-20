package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSale {
    private String code;       // Código del producto vendido
    private int quantity;      // Cantidad de unidades vendidas
    private double subtotal;   // Precio total por este producto (price × quantity)
}
