package com.ethio_connect.youtubeintegration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles all exceptions globally across the application
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle YouTube API errors
     */
    @ExceptionHandler(YouTubeApiException.class)
    public ResponseEntity<String> handleYouTubeApiException(YouTubeApiException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body("External API Error: " + ex.getMessage());
    }

    /**
     * Handle general runtime errors
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Error: " + ex.getMessage());
    }

    /**
     * Fallback handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected Error: " + ex.getMessage());
    }
}