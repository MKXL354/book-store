package com.mahdy.bookstore.authentication.service;

import com.mahdy.bookstore.authentication.exception.TokenExpiredException;
import com.mahdy.bookstore.authentication.exception.TokenParseException;
import com.mahdy.bookstore.authentication.exception.TokenSignatureException;
import com.mahdy.bookstore.authentication.model.AccessTokenRequest;
import com.mahdy.bookstore.authentication.model.RefreshTokenRequest;
import com.mahdy.bookstore.authentication.model.Token;
import com.mahdy.bookstore.authentication.model.TokenClaims;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public interface TokenService {

    Token generateAccessToken(AccessTokenRequest accessTokenRequest);

    TokenClaims parseAccessToken(Token token) throws TokenParseException, TokenSignatureException,
            TokenExpiredException;

    Token generateRandomRefreshToken(RefreshTokenRequest refreshTokenRequest);
}
