package com.ethio_connect.youtubeintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoSearchRequest {
    private String query;
    private String channelId;
    private String videoDuration; // "short", "medium", "long"
    private String order;         // "date", "viewCount", "relevance"
    private String safeSearch;    // "none", "moderate", "strict"
    private Integer maxResults;
}