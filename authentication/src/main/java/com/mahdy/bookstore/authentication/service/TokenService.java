package com.mahdy.bookstore.authentication.service;

import com.mahdy.bookstore.authentication.exception.*;
import com.mahdy.bookstore.authentication.model.Token;
import com.mahdy.bookstore.authentication.model.TokenClaims;
import com.mahdy.bookstore.authentication.model.TokenRequest;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public interface TokenService {

    Token generateToken(TokenRequest tokenRequest) throws GeneralAuthenticationException, EncryptionKeyLengthException;

    TokenClaims parseToken(Token token) throws TokenParseException, EncryptionKeyLengthException, GeneralAuthenticationException,
            TokenSignatureException, TokenExpiredException;
}
