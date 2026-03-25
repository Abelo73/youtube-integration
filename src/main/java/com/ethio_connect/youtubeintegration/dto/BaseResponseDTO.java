package com.ethio_connect.youtubeintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDTO<T> {
    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private String nextPageToken;
    private T data;

    public static <T> BaseResponseDTO<T> ok(T data, String message) {
        return BaseResponseDTO.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponseDTO<T> ok(T data, String message, String nextPageToken) {
        return BaseResponseDTO.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .nextPageToken(nextPageToken)
                .data(data)
                .build();
    }
}