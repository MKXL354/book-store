package com.mahdy.bookstore.bookservice.exception;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
public class PasswordMismatchException extends Exception {

    public PasswordMismatchException() {
        super();
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
