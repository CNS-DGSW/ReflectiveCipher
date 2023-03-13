package org.swcns.reflectivecipher.example;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

@Component
@RequiredArgsConstructor
public class ReflectiveCipherComponent implements ApplicationRunner {

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

    @Override
    public void run(ApplicationArguments args) {
        check();
    }
}
