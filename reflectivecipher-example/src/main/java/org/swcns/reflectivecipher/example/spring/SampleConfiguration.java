package org.swcns.reflectivecipher.example.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.swcns.reflectivecipher.example.SecurityValidator;
import org.swcns.reflectivecipher.util.ObjectDecryptor;
import org.swcns.reflectivecipher.util.ObjectEncryptor;

@Configuration
@RequiredArgsConstructor
public class SampleConfiguration {

    private final ObjectEncryptor encryptor;

    private final ObjectDecryptor decryptor;

    @Bean
    public SecurityValidator securityValidator() {
        return new SecurityValidator(encryptor, decryptor);
    }
}
