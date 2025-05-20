package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    private Store store;                        // Tienda donde se realizó la venta
    private Seller seller;                      // Vendedor que hizo la venta
    private LocalDate dateSale;                 // Fecha de la venta
    private int quantity;                       // Cantidad total de productos vendidos
    private List<ProductSale> products;         // Lista de productos vendidos, conexion con DTO
    private double totalSale;                   // Monto total de la venta
}
