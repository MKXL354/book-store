package com.mahdy.bookstore.bookservice.api.model;

import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Data
public class UserLoginResponseModel {

    private String accessToken;
    private String refreshToken;
}
