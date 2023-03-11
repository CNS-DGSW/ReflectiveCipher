package org.swcns.reflectivecipher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBeanConfiguration {

    @Bean
    public AnnotationTestMethod testMethod() {
        return new AnnotationTestMethod();
    }
}
