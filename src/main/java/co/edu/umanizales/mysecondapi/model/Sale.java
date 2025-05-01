package co.edu.umanizales.mysecondapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.Locale;

@Data
@AllArgsConstructor
public class Sale {
    private Store store;
    private Seller seller;
    private Locale dateSale;
    private int quantity ;
    private String products;
}
