package com.ethio_connect.youtubeintegration.service;

import com.ethio_connect.youtubeintegration.client.YouTubeApiClient;
import com.ethio_connect.youtubeintegration.dto.VideoItem;
import com.ethio_connect.youtubeintegration.dto.YouTubeResponse;
import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.service.impl.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Business logic layer
 */
@Service
@RequiredArgsConstructor
public class YouTubeServiceImpl implements YouTubeService {

    private final YouTubeApiClient youTubeApiClient;

    /**
     * Get latest video from a channel
     *
     * Cached to reduce API calls (VERY IMPORTANT)
     */
    @Override
    @Cacheable(value = "latestVideo", key = "#channelId")
    public LatestVideoResponse getLatestVideo(String channelId) {

        YouTubeResponse response = youTubeApiClient.fetchLatestVideos(channelId);

        if (response.getItems() == null || response.getItems().isEmpty()) {
            throw new RuntimeException("No videos found for channel");
        }

        VideoItem video = response.getItems().get(0);

        return LatestVideoResponse.builder()
                .videoId(video.getId().getVideoId())
                .title(video.getSnippet().getTitle())
                .thumbnail(video.getSnippet().getThumbnails().getMedium().getUrl())
                .build();
    }
}