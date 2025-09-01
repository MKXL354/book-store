package com.mahdy.bookstore.bookservice.config;

import com.mahdy.bookstore.authentication.service.TokenService;
import com.mahdy.bookstore.authentication.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Configuration
@ConfigurationPropertiesScan
public class BookServiceConfig {

    @Bean
    public TokenService tokenService(@Value("${token.clock-skew-millis}") int clockSkewMillis, KeyConfig keyConfig) {
        return new TokenServiceImpl(clockSkewMillis, keyConfig.getPublicKey(), keyConfig.getPrivateKey(), keyConfig.getSecretKey());
    }
}
