package com.mahdy.bookstore.authentication.util;

import com.mahdy.bookstore.authentication.exception.AuthenticationRuntimeException;
import com.mahdy.bookstore.authentication.util.model.EncodedAesSecretKey;
import com.mahdy.bookstore.authentication.util.model.EncodedEcKeyPair;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class KeyUtil {

    public static EncodedEcKeyPair generateEncodedEcKeyPair() {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        } catch (GeneralSecurityException e) {
            throw new AuthenticationRuntimeException(e);
        }
        KeyPair ecKeyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey rsaPublicKey = (ECPublicKey) ecKeyPair.getPublic();
        ECPrivateKey rsaPrivateKey = (ECPrivateKey) ecKeyPair.getPrivate();
        return new EncodedEcKeyPair(rsaPublicKey.getEncoded(), rsaPrivateKey.getEncoded());
    }

    public static ECPublicKey generateECPublicKey(String encodedRSAPublicKey) {
        byte[] keyBytes = Base64.getDecoder().decode(encodedRSAPublicKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return (ECPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthenticationRuntimeException(e);
        }
    }

    public static ECPrivateKey generateECPrivateKey(String encodedRSAPrivateKey) {
        byte[] keyBytes = Base64.getDecoder().decode(encodedRSAPrivateKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return (ECPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthenticationRuntimeException(e);
        }
    }

    public static EncodedAesSecretKey generateEncodedAesSecretKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticationRuntimeException(e);
        }
        keyGenerator.init(256);
        SecretKey aesKey = keyGenerator.generateKey();
        return new EncodedAesSecretKey(aesKey.getEncoded());
    }

    public static SecretKey generateAesSecretKey(String encodedAesKey) {
        byte[] keyBytes = Base64.getDecoder().decode(encodedAesKey);
        return new SecretKeySpec(keyBytes, "AES");
    }
}
