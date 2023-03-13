package org.swcns.reflectivecipher.example.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.swcns.reflectivecipher.annotation.SecurityField;

@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class A {

    @SecurityField
    private String securityField;

    private String nonSecurityField;

}
