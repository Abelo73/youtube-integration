package com.ethio_connect.youtubeintegration.dto;

import lombok.Data;
import java.util.List;

/**
 * Root response from YouTube API
 */
@Data
public class YouTubeResponse {

    private List<VideoItem> items;
}