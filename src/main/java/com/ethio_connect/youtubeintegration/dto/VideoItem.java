package com.ethio_connect.youtubeintegration.dto;

import lombok.Data;

/**
 * Represents each video item in response
 */
@Data
public class VideoItem {
    private String kind;
    private String etag;
    private Id id;           // Should contain videoId
    private Snippet snippet; // Should contain title, description, thumbnails
}