package org.swcns.reflectivecipher.core;

import lombok.RequiredArgsConstructor;
import org.swcns.reflectivecipher.property.EncryptProperties;
import org.swcns.reflectivecipher.util.ReflectionUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
public class EncryptionManager {
    private final EncryptProperties properties;

    private Optional<SecretKey> generateKey(String keyString) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(keyString.toCharArray(), properties.getHash().getBytes(StandardCharsets.UTF_8),
                    65536, 256);

            return Optional.of(new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES"));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private Optional<IvParameterSpec> generateIv(String ivString) {
        if(ivString.length() != 16) return Optional.empty();

        try {
            return Optional.of(new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private byte[] processBytes(byte[] bytes, int cipherMode) throws Exception {
        Cipher cipher = Cipher.getInstance(properties.getAlgorithm());
        cipher.init(cipherMode, generateKey(properties.getKey()).orElseThrow(),
                generateIv(properties.getIv()).orElseThrow());

        return cipher.doFinal(bytes);
    }

    public <T> T encrypt(T data) throws Exception {
        byte[] rawData;

        if(ReflectionUtil.isCharacterType(data)) {
            rawData = ((String)data).getBytes(StandardCharsets.UTF_8);
        } else if(ReflectionUtil.isBinaryType(data)) {
            rawData = (byte[])data;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }

        rawData = processBytes(rawData, Cipher.ENCRYPT_MODE);

        if(ReflectionUtil.isCharacterType(data)) {
            return (T)(Base64.getEncoder().encodeToString(rawData));
        } else if(ReflectionUtil.isBinaryType(data)) {
            return (T)rawData;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }
    }

    public <T> T decrypt(T data) throws Exception {
        byte[] rawData;

        if(ReflectionUtil.isCharacterType(data)) {
            rawData = Base64.getDecoder().decode((String)data);
        } else if(ReflectionUtil.isBinaryType(data)) {
            rawData = (byte[])data;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }

        rawData = processBytes(rawData, Cipher.DECRYPT_MODE);

        if(ReflectionUtil.isCharacterType(data)) {
            return (T)(new String(rawData, Charset.forName("utf8")));
        } else if(ReflectionUtil.isBinaryType(data)) {
            return (T)rawData;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }
    }
}
