package com.ethio_connect.youtubeintegration.service.impl;

import com.ethio_connect.youtubeintegration.client.YouTubeApiClient;
import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.dto.YouTubeResponse;
import com.ethio_connect.youtubeintegration.dto.VideoItem;
import com.ethio_connect.youtubeintegration.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class YouTubeServiceImpl implements YouTubeService {

    private final YouTubeApiClient youTubeApiClient;

    @Override
    public LatestVideoResponse getLatestVideo(String channelId) {
        YouTubeResponse response = youTubeApiClient.fetchLatestVideos(channelId);

        if (response.getItems() == null || response.getItems().isEmpty()) {
            throw new RuntimeException("No videos found for channel: " + channelId);
        }

        VideoItem video = response.getItems().get(0);
        String videoId = video.getId().getVideoId();

        return LatestVideoResponse.builder()
                .videoId(video.getId().getVideoId())
                .title(video.getSnippet().getTitle())
                .thumbnail(video.getSnippet().getThumbnails().getMedium().getUrl())
                .videoUrl("https://www.youtube.com/watch?v="+videoId)
                .description(video.getSnippet().getDescription())
                .build();
    }
}