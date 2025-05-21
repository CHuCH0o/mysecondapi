package co.edu.umanizales.mysecondapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TypeDocument extends Parameter {

    public TypeDocument(String code, String description) {
        super(code, description);
    }
}
