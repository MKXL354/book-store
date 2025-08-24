package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class GeneralAuthenticationException extends AuthenticationException {

    public GeneralAuthenticationException() {
        super();
    }

    public GeneralAuthenticationException(String message) {
        super(message);
    }

    public GeneralAuthenticationException(Throwable cause) {
        super(cause);
    }

    public GeneralAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
