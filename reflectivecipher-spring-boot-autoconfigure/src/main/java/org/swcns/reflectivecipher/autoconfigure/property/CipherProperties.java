package org.swcns.reflectivecipher.autoconfigure.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "reflcrypt")
public class CipherProperties {
    private String algorithm;
    private String key;
    private String hash;
    private String iv;
}
