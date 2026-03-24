package com.ethio_connect.youtubeintegration.controller;

import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.dto.VideoSearchRequest;
import com.ethio_connect.youtubeintegration.service.YouTubeSchedulerService;
import com.ethio_connect.youtubeintegration.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/youtube")
public class YouTubeController {

    private final YouTubeService youtubeService;
    private final YouTubeSchedulerService schedulerService;

    // Existing Cached Endpoint
    @GetMapping("/latest/{channelId}")
    public LatestVideoResponse getLatest(@PathVariable String channelId) {
        return schedulerService.getCachedVideo(channelId);
    }

    // New Advanced Search Endpoint
    @GetMapping("/search")
    public List<LatestVideoResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String channelId,
            @RequestParam(defaultValue = "relevance") String order,
            @RequestParam(defaultValue = "10") Integer maxResults) {

        VideoSearchRequest request = VideoSearchRequest.builder()
                .query(q)
                .channelId(channelId)
                .order(order)
                .maxResults(maxResults)
                .build();

        return youtubeService.searchVideos(request);
    }
}