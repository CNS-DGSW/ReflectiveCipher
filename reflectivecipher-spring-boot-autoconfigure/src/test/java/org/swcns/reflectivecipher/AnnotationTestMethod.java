package org.swcns.reflectivecipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.swcns.reflectivecipher.annotation.*;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

import java.util.Arrays;
import java.util.List;

@Component
public class AnnotationTestMethod {

    @Autowired
    private ObjectEncryptor encryptor;

    @DecryptParams
    void decryptParams(@SecurityParam TestObject decrypted) {
        System.out.println(decrypted);
    }

    @EncryptParams
    void encryptParams(@SecurityParam TestObject encrypted) {
        System.out.println(encrypted);
    }

    @EncryptReturns
    TestObject encryptReturns(TestObject obj) {
        return obj;
    }

    @DecryptReturns
    TestObject decryptReturns(TestObject obj) {
        return obj;
    }

    @EncryptParams
    void encryptedInstanceParams(@SecurityParam String encryptedField1,
                                 @SecurityParam byte[] encryptedField2, int nonEncryptedField1,
                                 String nonEncryptedField2, @SecurityParam List<String> encryptedCollection) {
        System.out.printf("Encrypted: %s, %s, %d, %s, %s\n", encryptedField1,
                Arrays.toString(encryptedField2), nonEncryptedField1, nonEncryptedField2,
                encryptedCollection.toString());
    }

    @DecryptParams
    void decryptedInstanceParams(@SecurityParam String encryptedField1,
                                 @SecurityParam byte[] encryptedField2, int nonEncryptedField1,
                                 String nonEncryptedField2, @SecurityParam List<String> encryptedCollection) {
        System.out.printf("Decrypted: %s, %s, %d, %s, %s\n", encryptedField1,
                Arrays.toString(encryptedField2), nonEncryptedField1, nonEncryptedField2,
                encryptedCollection.toString());
    }
}
