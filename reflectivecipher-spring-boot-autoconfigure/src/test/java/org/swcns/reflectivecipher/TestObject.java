package org.swcns.reflectivecipher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.swcns.reflectivecipher.annotation.SecurityField;

@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class TestObject {

    @SecurityField
    private String encryptedField1;

    @SecurityField
    private byte[] encryptedField2;

    private Integer nonEncryptedField1;

    private String nonEncryptedField2;

}
