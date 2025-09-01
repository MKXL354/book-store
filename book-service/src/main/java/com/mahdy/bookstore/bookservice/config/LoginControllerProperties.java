package com.mahdy.bookstore.bookservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@ConfigurationProperties(prefix = "token")
@Data
public class LoginControllerProperties {

    private final int accessTimeToLiveMillis;
    private final int refreshTokenMaxLength;
}
