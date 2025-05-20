package co.edu.umanizales.mysecondapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeDocument extends Parameter {
    public TypeDocument(String code, String description) {
        super(code, description);
    }
}
