package com.mahdy.bookstore.bookservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@ConfigurationProperties(prefix = "key")
@Data
public class KeyProperties {

    private final String privateKey;
    private final String publicKey;
    private final String secretKey;
}
