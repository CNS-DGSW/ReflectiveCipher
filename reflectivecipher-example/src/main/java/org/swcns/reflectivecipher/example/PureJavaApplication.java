package org.swcns.reflectivecipher.example;

import org.swcns.reflectivecipher.core.EncryptionManager;
import org.swcns.reflectivecipher.property.EncryptProperties;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

public class PureJavaApplication {

    public static void main(String[] args) {
        EncryptProperties properties = new EncryptProperties("AES/CBC/PKCS5Padding",
                "WOWILOVEIT", "WOW", "AAAABBBBCCCCDDDD");
        EncryptionManager manager = new EncryptionManager(properties);
        ObjectEncryptor encryptor = new ObjectEncryptor(manager);
        ObjectDecryptor decryptor = new ObjectDecryptor(manager);

        SecurityValidator securityValidator = new SecurityValidator(encryptor, decryptor);
        securityValidator.check();
    }

}
