package org.swcns.reflectivecipher.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EncryptProperties {

    private final String algorithm;
    private final String key;
    private final String hash;
    private final String iv;

}
