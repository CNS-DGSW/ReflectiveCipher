package org.swcns.reflectivecipher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.swcns.reflectivecipher.core.EncryptionManager;
import org.swcns.reflectivecipher.property.EncryptProperties;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilTest {

    private ObjectEncryptor encryptor;

    private ObjectDecryptor decryptor;

    @BeforeAll
    void init() {
        EncryptProperties properties = new EncryptProperties(
                "AES/CBC/PKCS5Padding", "WOWILOVEIT", "WOW", "AAAABBBBCCCCDDDD"
        );
        EncryptionManager manager = new EncryptionManager(properties);
        encryptor = new ObjectEncryptor(manager);
        decryptor = new ObjectDecryptor(manager);
    }

    @Test
    void objectEncryptTest() {
        TestObject beforeEncrypt = new TestObject("안녕하세요", "wow".getBytes(), 1024, "wow!");
        System.out.printf("Before encrypt: %s%n", beforeEncrypt);

        TestObject afterEncrypt = encryptor.getEncryptedObject(beforeEncrypt);
        System.out.printf("After encrypt: %s%n", afterEncrypt);

        assertThat(beforeEncrypt).isNotSameAs(afterEncrypt);
        assertThat(beforeEncrypt.getEncryptedField1())
                .isNotEqualTo(afterEncrypt.getEncryptedField1());
        assertThat(beforeEncrypt.getEncryptedField2())
                .isNotEqualTo(afterEncrypt.getEncryptedField2());
        assertThat(beforeEncrypt.getNonEncryptedField1())
                .isEqualTo(afterEncrypt.getNonEncryptedField1());
        assertThat(beforeEncrypt.getNonEncryptedField2())
                .isEqualTo(afterEncrypt.getNonEncryptedField2());

        TestObject afterDecrypt = decryptor.getDecryptedObject(afterEncrypt);
        System.out.printf("After decrypt: %s%n", afterDecrypt);

        assertThat(beforeEncrypt).isNotSameAs(afterDecrypt);
        assertThat(beforeEncrypt.getEncryptedField1())
                .isEqualTo(afterDecrypt.getEncryptedField1());
        assertThat(beforeEncrypt.getEncryptedField2())
                .isEqualTo(afterDecrypt.getEncryptedField2());
        assertThat(beforeEncrypt.getNonEncryptedField1())
                .isEqualTo(afterDecrypt.getNonEncryptedField1());
        assertThat(beforeEncrypt.getNonEncryptedField2())
                .isEqualTo(afterDecrypt.getNonEncryptedField2());
    }
}
