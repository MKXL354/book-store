package com.mahdy.bookstore.authentication.service;

import com.mahdy.bookstore.authentication.exception.*;
import com.mahdy.bookstore.authentication.model.AccessTokenRequest;
import com.mahdy.bookstore.authentication.model.RefreshTokenRequest;
import com.mahdy.bookstore.authentication.model.Token;
import com.mahdy.bookstore.authentication.model.TokenClaims;
import com.mahdy.bookstore.authentication.util.SecurityUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class TokenServiceImpl implements TokenService {

    private final String CONTENT_TYPE = "JWT";
    private final String PAYLOAD = "payload";

    private static final SecureRandom RNG = new SecureRandom();
    private static final Base64.Encoder B64_URL = Base64.getUrlEncoder().withoutPadding();

    private final long clockSkewMillis;

    private final ECPublicKey ecPublicKey;
    private final ECPrivateKey ecPrivateKey;
    private final SecretKey aesSecretKey;

    public TokenServiceImpl(long clockSkewMillis, String encodedPublicKey, String encodedPrivateKey, String encodedEncryptionKey) {
        this.clockSkewMillis = clockSkewMillis;
        this.ecPublicKey = SecurityUtil.generateECPublicKey(encodedPublicKey);
        this.ecPrivateKey = SecurityUtil.generateECPrivateKey(encodedPrivateKey);
        this.aesSecretKey = SecurityUtil.generateAesSecretKey(encodedEncryptionKey);
    }

    @Override
    public Token generateAccessToken(AccessTokenRequest accessTokenRequest) {
        JWTClaimsSet claims = getJwtClaims(accessTokenRequest);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.ES256), claims);
        try {
            signedJWT.sign(new ECDSASigner(ecPrivateKey));
        } catch (JOSEException e) {
            throw new TokenGeneralException(e);
        }

        EncryptedJWT encryptedJWT = new EncryptedJWT(
                new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM).contentType(CONTENT_TYPE).build(),
                new JWTClaimsSet.Builder().claim(PAYLOAD, signedJWT.serialize()).build()
        );
        try {
            encryptedJWT.encrypt(new DirectEncrypter(aesSecretKey));
        } catch (KeyLengthException e) {
            throw new EncryptionKeyLengthException(e);
        } catch (JOSEException e) {
            throw new TokenGeneralException(e);
        }

        return new Token(encryptedJWT.serialize());
    }

    @Override
    public TokenClaims parseAccessToken(Token token) throws TokenParseException, TokenSignatureException,
            TokenExpiredException {
        EncryptedJWT parsedEncrypted;
        try {
            parsedEncrypted = EncryptedJWT.parse(token.getTokenString());
            parsedEncrypted.decrypt(new DirectDecrypter(aesSecretKey));
        } catch (ParseException e) {
            throw new TokenParseException(e);
        } catch (KeyLengthException e) {
            throw new EncryptionKeyLengthException(e);
        } catch (JOSEException e) {
            throw new TokenGeneralException(e);
        }

        try {
            String signedSerialized = parsedEncrypted.getJWTClaimsSet().getStringClaim(PAYLOAD);
            SignedJWT parsedSigned = SignedJWT.parse(signedSerialized);
            if (!parsedSigned.verify(new ECDSAVerifier(ecPublicKey))) {
                throw new TokenSignatureException("Invalid signature");
            }
            JWTClaimsSet claims = parsedSigned.getJWTClaimsSet();
            validateClaims(claims);
            return new TokenClaims(claims.getClaims());
        } catch (ParseException e) {
            throw new TokenParseException(e);
        } catch (JOSEException e) {
            throw new TokenGeneralException(e);
        }
    }

    @Override
    public Token generateRandomRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        int maxLength = refreshTokenRequest.getMaxLength();
        byte[] random = new byte[maxLength];
        RNG.nextBytes(random);
        String token = B64_URL.encodeToString(random);
        token = token.length() > maxLength ? token.substring(0, maxLength) : token;
        return new Token(token);
    }

    private JWTClaimsSet getJwtClaims(AccessTokenRequest accessTokenRequest) {
        JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder()
                .expirationTime(Date.from(Instant.now().plusMillis(accessTokenRequest.getTimeToLiveMillis())));
        for (Map.Entry<String, Object> entry : accessTokenRequest.getClaims().entrySet()) {
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        return jwtBuilder.build();
    }

    private void validateClaims(JWTClaimsSet claims) throws TokenExpiredException {
        Date now = new Date();
        Date expirationTime = claims.getExpirationTime();
        if (expirationTime == null || now.getTime() - clockSkewMillis > expirationTime.getTime()) {
            throw new TokenExpiredException("token is expired");
        }
    }
}
//TODO: make this entire module a separate library?
//TODO: change the naming of algorithms or generalize?
