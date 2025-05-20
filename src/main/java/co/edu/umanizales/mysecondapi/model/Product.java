package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String code;                // Código único del producto
    private double price;              // Precio del producto
    private int stock;                 // Cantidad en inventario
    private Parameter typeProduct;     // Tipo de producto (instancia de TypeProduct)
}
