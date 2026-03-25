package com.ethio_connect.youtubeintegration.exception;

import com.ethio_connect.youtubeintegration.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Handles all exceptions globally across the application.
 * Returns a consistent ErrorResponse DTO for all errors.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle custom YouTube API errors (Bad Gateway)
     */
    @ExceptionHandler(YouTubeApiException.class)
    public ResponseEntity<ErrorResponse> handleYouTubeApiException(YouTubeApiException ex, WebRequest request) {
        log.error("YouTube API Error: {}", ex.getMessage());
        return buildErrorResponse(
                HttpStatus.BAD_GATEWAY,
                "External YouTube Service Error",
                ex.getMessage(),
                request
        );
    }

    /**
     * Handle general runtime errors (Internal Server Error)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("Runtime Exception: ", ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Processing Error",
                ex.getMessage(),
                request
        );
    }

    /**
     * Fallback handler for any other unhandled exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        log.error("Unexpected System Error: ", ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                ex.getMessage(),
                request
        );
    }

    /**
     * Helper method to construct the ErrorResponse DTO
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}