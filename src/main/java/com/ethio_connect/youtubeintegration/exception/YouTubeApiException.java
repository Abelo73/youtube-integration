package com.ethio_connect.youtubeintegration.exception;

/**
 * Custom exception for YouTube API errors
 */
public class YouTubeApiException extends RuntimeException {

    public YouTubeApiException(String message) {
        super(message);
    }
}