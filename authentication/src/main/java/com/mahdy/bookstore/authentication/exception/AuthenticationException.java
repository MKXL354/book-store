package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class AuthenticationException extends Exception {

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
