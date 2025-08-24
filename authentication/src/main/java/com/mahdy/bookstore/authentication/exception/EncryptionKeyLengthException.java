package com.mahdy.bookstore.authentication.exception;

/**
 * @author Mehdi Kamali
 * @since 24/08/2025
 */
public class EncryptionKeyLengthException extends AuthenticationException {

    public EncryptionKeyLengthException() {
        super();
    }

    public EncryptionKeyLengthException(String message) {
        super(message);
    }

    public EncryptionKeyLengthException(Throwable cause) {
        super(cause);
    }

    public EncryptionKeyLengthException(String message, Throwable cause) {
        super(message, cause);
    }
}
