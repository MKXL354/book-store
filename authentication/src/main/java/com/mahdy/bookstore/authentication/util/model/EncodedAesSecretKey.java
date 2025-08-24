package com.mahdy.bookstore.authentication.util.model;

import lombok.Data;

import java.util.Base64;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
@Data
public class EncodedAesSecretKey {

    private final byte[] aesSecretKeyBytes;
    private final String aesSecretKey;

    public EncodedAesSecretKey(byte[] aesSecretKeyBytes) {
        this.aesSecretKeyBytes = aesSecretKeyBytes;
        this.aesSecretKey = Base64.getEncoder().encodeToString(aesSecretKeyBytes);
    }
}
