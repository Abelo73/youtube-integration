package com.ethio_connect.youtubeintegration.controller;

import com.ethio_connect.youtubeintegration.dto.BaseResponseDTO;
import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.dto.SearchResponseDTO;
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

    @GetMapping("/latest/{channelId}")
    public BaseResponseDTO<LatestVideoResponse> getLatest(@PathVariable String channelId) {
        LatestVideoResponse video = schedulerService.getCachedVideo(channelId);
        return BaseResponseDTO.ok(video, "Fetched from cache successfully");
    }

    @GetMapping("/search")
    public BaseResponseDTO<List<LatestVideoResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String channelId,
            @RequestParam(defaultValue = "relevance") String order,
            @RequestParam(defaultValue = "10") Integer maxResults,
            @RequestParam(required = false) String pageToken) {

        VideoSearchRequest request = VideoSearchRequest.builder()
                .query(q)
                .channelId(channelId)
                .order(order)
                .maxResults(maxResults)
                .pageToken(pageToken)
                .build();

        SearchResponseDTO searchResult = youtubeService.searchVideos(request);

        return BaseResponseDTO.ok(
                searchResult.getVideos(),
                "Search completed successfully",
                searchResult.getNextPageToken()
        );
    }
}