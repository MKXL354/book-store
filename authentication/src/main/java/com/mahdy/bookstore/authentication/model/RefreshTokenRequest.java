package com.mahdy.bookstore.authentication.model;

import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Data
public class RefreshTokenRequest {

    private final int maxLength;
}
