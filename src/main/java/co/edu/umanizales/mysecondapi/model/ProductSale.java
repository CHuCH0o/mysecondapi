package co.edu.umanizales.mysecondapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductSale {

    private Product product;   // Producto vendido
    private int quantity;      // Cantidad vendida
    private double subtotal;   // Precio * cantidad

    // Constructor que recibe el producto y la cantidad vendida
    public ProductSale(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity; // Se calcula el subtotal automáticamente
    }
}
