package com.mahdy.bookstore.bookservice.web.exceptionhandling;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Mehdi Kamali
 * @since 01/09/2025
 */
@Data
public class ErrorDto {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
}
