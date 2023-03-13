package org.swcns.reflectivecipher.autoconfigure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swcns.reflectivecipher.aspect.ReflectionCipher;
import org.swcns.reflectivecipher.autoconfigure.property.CipherProperties;
import org.swcns.reflectivecipher.core.EncryptionManager;
import org.swcns.reflectivecipher.property.EncryptProperties;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CipherProperties.class)
public class ReflectiveCipherAutoConfiguration {

    private final CipherProperties properties;

    @Bean @ConditionalOnMissingBean
    public ReflectionCipher cipher() {
        return new ReflectionCipher(objectEncryptor(), objectDecryptor());
    }

    @Bean @ConditionalOnMissingBean
    public EncryptionManager encryptionManager() {
        return new EncryptionManager(new EncryptProperties(properties.getAlgorithm(),
                properties.getKey(), properties.getHash(), properties.getIv()));
    }

    @Bean @ConditionalOnMissingBean
    public ObjectEncryptor objectEncryptor() {
        return new ObjectEncryptor(encryptionManager());
    }

    @Bean @ConditionalOnMissingBean
    public ObjectDecryptor objectDecryptor() {
        return new ObjectDecryptor(encryptionManager());
    }
}
