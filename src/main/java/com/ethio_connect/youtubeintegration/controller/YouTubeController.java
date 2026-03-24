package com.ethio_connect.youtubeintegration.controller;

import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.service.YouTubeSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class YouTubeController {

    private final YouTubeSchedulerService schedulerService;

    @GetMapping("/latest-video/{channelId}")
    public LatestVideoResponse getLatestVideo(@PathVariable String channelId) {
        LatestVideoResponse video = schedulerService.getCachedVideo(channelId);
        if (video == null) {
            throw new RuntimeException("No cached video found for channel: " + channelId);
        }
        return video;
    }
}