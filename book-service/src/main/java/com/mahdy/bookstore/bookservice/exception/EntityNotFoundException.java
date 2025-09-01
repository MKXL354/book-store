package com.mahdy.bookstore.bookservice.exception;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
public class EntityNotFoundException extends ApplicationException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
