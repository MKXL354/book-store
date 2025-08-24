package com.mahdy.bookstore.authentication.util.model;

import lombok.Data;

import java.util.Base64;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
@Data
public class EncodedEcKeyPair {

    private final byte[] ecPublicKeyBytes;
    private final String ecPublicKey;
    private final byte[] ecPrivateKeyBytes;
    private final String ecPrivateKey;

    public EncodedEcKeyPair(byte[] ecPublicKeyBytes, byte[] ecPrivateKeyBytes) {
        this.ecPublicKeyBytes = ecPublicKeyBytes;
        ecPublicKey = Base64.getEncoder().encodeToString(ecPublicKeyBytes);
        this.ecPrivateKeyBytes = ecPrivateKeyBytes;
        ecPrivateKey = Base64.getEncoder().encodeToString(ecPrivateKeyBytes);
    }
}
