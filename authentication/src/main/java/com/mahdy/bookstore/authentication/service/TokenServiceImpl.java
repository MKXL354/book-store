package com.mahdy.bookstore.authentication.service;

import com.mahdy.bookstore.authentication.exception.*;
import com.mahdy.bookstore.authentication.model.Token;
import com.mahdy.bookstore.authentication.model.TokenClaims;
import com.mahdy.bookstore.authentication.model.TokenRequest;
import com.mahdy.bookstore.authentication.util.KeyUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.crypto.SecretKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class TokenServiceImpl implements TokenService {

    private final String CONTENT_TYPE = "JWT";
    private final String PAYLOAD = "payload";

    private final long clockSkewMillis;

    private final ECPublicKey ecPublicKey;
    private final ECPrivateKey ecPrivateKey;
    private final SecretKey aesSecretKey;

    public TokenServiceImpl(long clockSkewMillis, String encodedPublicKey, String encodedPrivateKey, String encodedEncryptionKey) {
        this.clockSkewMillis = clockSkewMillis;
        this.ecPublicKey = KeyUtil.generateECPublicKey(encodedPublicKey);
        this.ecPrivateKey = KeyUtil.generateECPrivateKey(encodedPrivateKey);
        this.aesSecretKey = KeyUtil.generateAesSecretKey(encodedEncryptionKey);
    }

    @Override
    public Token generateToken(TokenRequest tokenRequest) throws GeneralAuthenticationException, EncryptionKeyLengthException {
        JWTClaimsSet claims = getJwtClaims(tokenRequest);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.ES256), claims);
        try {
            signedJWT.sign(new ECDSASigner(ecPrivateKey));
        } catch (JOSEException e) {
            throw new GeneralAuthenticationException(e);
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
            throw new GeneralAuthenticationException(e);
        }

        return new Token(encryptedJWT.serialize());
    }

    @Override
    public TokenClaims parseToken(Token token) throws TokenParseException, EncryptionKeyLengthException, GeneralAuthenticationException,
            TokenSignatureException, TokenExpiredException {
        EncryptedJWT parsedEncrypted;
        try {
            parsedEncrypted = EncryptedJWT.parse(token.getToken());
            parsedEncrypted.decrypt(new DirectDecrypter(aesSecretKey));
        } catch (ParseException e) {
            throw new TokenParseException(e);
        } catch (KeyLengthException e) {
            throw new EncryptionKeyLengthException(e);
        } catch (JOSEException e) {
            throw new GeneralAuthenticationException(e);
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
            throw new GeneralAuthenticationException(e);
        }
    }

    private JWTClaimsSet getJwtClaims(TokenRequest tokenRequest) {
        JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder()
                .expirationTime(Date.from(Instant.now().plusMillis(tokenRequest.getTimeToLiveMillis())));
        for (Map.Entry<String, Object> entry : tokenRequest.getClaims().entrySet()) {
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
//TODO: annotation and processing for use case
//TODO: make this entire module a separate library?
//TODO: change the naming of algorithms or generalize?
