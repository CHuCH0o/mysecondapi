package co.edu.umanizales.mysecondapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeProduct extends Parameter {

    public TypeProduct(String code, String description) {
        super(code, description);
    }
}
