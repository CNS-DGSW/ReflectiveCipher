package org.swcns.reflectivecipher.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swcns.reflectivecipher.property.EncryptProperties;
import org.swcns.reflectivecipher.util.ReflectionUtil;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class EncryptionManager {

    private final EncryptProperties properties;

    public <T> T encrypt(T data) throws Exception {
        byte[] rawData;

        if (ReflectionUtil.isCharacterType(data)) {
            rawData = ((String) data).getBytes(StandardCharsets.UTF_8);
        } else if (ReflectionUtil.isBinaryType(data)) {
            rawData = (byte[]) data;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }

        rawData = processBytes(rawData, Cipher.ENCRYPT_MODE);

        if(ReflectionUtil.isCharacterType(data)) {
            //noinspection unchecked
            return (T) Base64.getEncoder().encodeToString(rawData);
        } else if(ReflectionUtil.isBinaryType(data)) {
            return (T) rawData;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }
    }

    public <T> T decrypt(T data) throws Exception {
        byte[] rawData;

        if (ReflectionUtil.isCharacterType(data)) {
            rawData = Base64.getDecoder().decode((String)data);
        } else if(ReflectionUtil.isBinaryType(data)) {
            rawData = (byte[])data;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }

        rawData = processBytes(rawData, Cipher.DECRYPT_MODE);

        if(ReflectionUtil.isCharacterType(data)) {
            //noinspection unchecked
            return (T) new String(rawData, StandardCharsets.UTF_8);
        } else if(ReflectionUtil.isBinaryType(data)) {
            return (T)rawData;
        } else {
            throw new InvalidParameterException(String.format("Type %s is not supported.", data.getClass().getName()));
        }
    }

    protected Optional<SecretKey> generateKey(String key) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), properties.getHash().getBytes(StandardCharsets.UTF_8),
                    65536, 256);
            return Optional.of(new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES"));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("exception occurred in generate key", e);
            return Optional.empty();
        }
    }

    private Optional<IvParameterSpec> generateIv(String iv) {
        if (iv.length() != 16) {
            log.error("iv length is must be 16 bytes long");
            return Optional.empty();
        }

        return Optional.of(new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
    }

    private byte[] processBytes(byte[] bytes, int cipherMode) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(properties.getAlgorithm());
        cipher.init(cipherMode, generateKey(properties.getKey()).orElseThrow(),
                generateIv(properties.getIv()).orElseThrow());
        return cipher.doFinal(bytes);
    }
}
