package com.ethio_connect.youtubeintegration.dto;

import lombok.Data;

/**
 * Contains video metadata
 */
@Data
public class Snippet {

    private String title;
    private Thumbnails thumbnails;
}