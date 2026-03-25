package com.ethio_connect.youtubeintegration.service.impl;

import com.ethio_connect.youtubeintegration.client.YouTubeApiClient;
import com.ethio_connect.youtubeintegration.dto.*;
import com.ethio_connect.youtubeintegration.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class YouTubeServiceImpl implements YouTubeService {

    private final YouTubeApiClient apiClient;

    @Override
    public LatestVideoResponse getLatestVideo(String channelId) {
        YouTubeResponse response = apiClient.fetchLatestVideos(channelId);
        if (response.getItems() == null || response.getItems().isEmpty()) {
            throw new RuntimeException("No videos found");
        }
        return mapToResponse(response.getItems().get(0));
    }

    @Override
    public SearchResponseDTO searchVideos(VideoSearchRequest request) {
        log.info("Executing Search: Query='{}'", request.getQuery());

        YouTubeResponse response = apiClient.searchVideos(request);

        List<LatestVideoResponse> videoList = response.getItems().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return SearchResponseDTO.builder()
                .videos(videoList)
                .nextPageToken(response.getNextPageToken())
                .build();
    }

    private LatestVideoResponse mapToResponse(VideoItem item) {
        String videoId = item.getId().getVideoId();
        return LatestVideoResponse.builder()
                .videoId(videoId)
                .title(HtmlUtils.htmlUnescape(item.getSnippet().getTitle()))
                .description(item.getSnippet().getDescription())
                .thumbnail(item.getSnippet().getThumbnails().getMedium().getUrl())
                .videoUrl("https://www.youtube.com/watch?v=" + videoId)
                .build();
    }
}