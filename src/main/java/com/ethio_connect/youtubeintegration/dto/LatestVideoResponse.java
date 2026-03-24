package com.ethio_connect.youtubeintegration.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Response sent to frontend
 */
@Data
@Builder
public class LatestVideoResponse {

    private String videoId;
    private String title;
    private String thumbnail;
}