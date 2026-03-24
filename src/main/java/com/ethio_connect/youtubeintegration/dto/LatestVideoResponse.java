package com.ethio_connect.youtubeintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestVideoResponse {
    private String videoId;
    private String title;
    private String thumbnail;
    private String description;
    private String videoUrl;
}