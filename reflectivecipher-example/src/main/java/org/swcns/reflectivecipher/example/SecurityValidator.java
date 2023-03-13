package org.swcns.reflectivecipher.example;

import lombok.RequiredArgsConstructor;
import org.swcns.reflectivecipher.example.component.A;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

@RequiredArgsConstructor
public class SecurityValidator {

    private final ObjectEncryptor encryptor;

    private final ObjectDecryptor decryptor;

    public void check() {
        A a = new A("Hello, World!", "Nice!");
        System.out.printf("before encrypt: %s%n", a);

        A b = encryptor.getEncryptedObject(a);
        System.out.printf("after encrypt: %s%n", b);

        A c = decryptor.getDecryptedObject(b);
        System.out.printf("after decrypt: %s%n", c);
    }
}
