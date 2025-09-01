package com.mahdy.bookstore.bookservice.web.exceptionhandling;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author Mehdi Kamali
 * @since 01/09/2025
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    TODO: this is just here to enforce a uniform error dto and is not for informative error handling
    @ExceptionHandler(Throwable.class)
    public ErrorDto handleThrowable(Throwable ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(ex.getMessage(), ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDto.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorDto.setPath(request.getRequestURI());

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return errorDto;
    }
}
