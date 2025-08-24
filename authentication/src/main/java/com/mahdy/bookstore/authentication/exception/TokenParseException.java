package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class TokenParseException extends AuthenticationException {

    public TokenParseException() {
        super();
    }

    public TokenParseException(String message) {
        super(message);
    }

    public TokenParseException(Throwable cause) {
        super(cause);
    }

    public TokenParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
