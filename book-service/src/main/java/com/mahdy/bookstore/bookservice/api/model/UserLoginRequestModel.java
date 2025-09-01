package com.mahdy.bookstore.bookservice.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Data
public class UserLoginRequestModel {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
