package com.mahdy.bookstore.authentication.util.model;

import lombok.Data;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Data
public class EncodedHashedPassword {

    private final byte[] hashedPasswordBytes;
    private final String hashedPassword;
}
