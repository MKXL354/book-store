package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class AuthenticationRuntimeException extends RuntimeException {

    public AuthenticationRuntimeException() {
        super();
    }

    public AuthenticationRuntimeException(String message) {
        super(message);
    }

    public AuthenticationRuntimeException(Throwable cause) {
        super(cause);
    }

    public AuthenticationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
