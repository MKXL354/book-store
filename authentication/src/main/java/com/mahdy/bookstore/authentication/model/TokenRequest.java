package com.mahdy.bookstore.authentication.model;

import lombok.Data;

import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
@Data
public class TokenRequest {

    private final Map<String, Object> claims;
    private final long timeToLiveMillis;
}
