package com.ethio_connect.youtubeintegration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoSearchRequest {
    private String query;
    private String channelId;
    private String order;
    private Integer maxResults;
    private String pageToken;
}