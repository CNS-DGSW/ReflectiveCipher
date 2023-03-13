package org.swcns.reflectivecipher.example.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.swcns.reflectivecipher.example.SecurityValidator;

@Component
@RequiredArgsConstructor
public class SampleCommandRunner implements CommandLineRunner {

    private final SecurityValidator securityValidator;

    @Override
    public void run(String... args) {
        securityValidator.check();
    }
}
