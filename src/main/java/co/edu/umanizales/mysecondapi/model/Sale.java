package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    private Store store;                      // Tienda donde se realiza la venta
    private Seller seller;                    // Vendedor que realizó la venta
    private LocalDate dateSale;               // Fecha de la venta
    private int quantity;                     // Cantidad total de productos vendidos
    private List<ProductSale> products;       // Lista de productos vendidos con su cantidad
}
