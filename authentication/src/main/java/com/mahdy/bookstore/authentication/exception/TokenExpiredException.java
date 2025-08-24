package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class TokenExpiredException extends AuthenticationException {

    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
