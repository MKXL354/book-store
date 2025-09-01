package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class TokenGeneralException extends AuthenticationRuntimeException {

    public TokenGeneralException() {
        super();
    }

    public TokenGeneralException(String message) {
        super(message);
    }

    public TokenGeneralException(Throwable cause) {
        super(cause);
    }

    public TokenGeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
