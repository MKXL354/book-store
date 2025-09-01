package com.mahdy.bookstore.bookservice.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Data
public class UserLoginRequestModel {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
