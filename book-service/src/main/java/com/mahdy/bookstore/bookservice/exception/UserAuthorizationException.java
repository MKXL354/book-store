package com.mahdy.bookstore.bookservice.exception;

/**
 * @author Mehdi Kamali
 * @since 01/09/2025
 */
public class UserAuthorizationException extends ApplicationException{

    public UserAuthorizationException() {
        super();
    }

    public UserAuthorizationException(String message) {
        super(message);
    }

    public UserAuthorizationException(Throwable cause) {
        super(cause);
    }

    public UserAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
