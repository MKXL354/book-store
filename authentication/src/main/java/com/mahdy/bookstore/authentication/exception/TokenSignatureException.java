package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class TokenSignatureException extends AuthenticationException {

    public TokenSignatureException() {
        super();
    }

    public TokenSignatureException(String message) {
        super(message);
    }

    public TokenSignatureException(Throwable cause) {
        super(cause);
    }

    public TokenSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
