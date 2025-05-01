package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private double price;
    private int stock;
}
