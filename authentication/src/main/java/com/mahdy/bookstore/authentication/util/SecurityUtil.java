package com.mahdy.bookstore.authentication.util;

import com.mahdy.bookstore.authentication.exception.AuthenticationRuntimeException;
import com.mahdy.bookstore.authentication.util.model.EncodedAesSecretKey;
import com.mahdy.bookstore.authentication.util.model.EncodedEcKeyPair;
import com.mahdy.bookstore.authentication.util.model.EncodedHashedPassword;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.Base64;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class SecurityUtil {

    //    TODO: no config for now
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

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

    public static EncodedHashedPassword generateHashedPassword(String password, String salt) {
        try {
            byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new EncodedHashedPassword(hash, Base64.getEncoder().encodeToString(hash));
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
