package com.ethio_connect.youtubeintegration.controller;

import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.service.impl.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller
 */
@RestController
@RequestMapping("/youtube")
@RequiredArgsConstructor
public class YouTubeController {

    private final YouTubeService youTubeService;

    @GetMapping("/latest")
    public LatestVideoResponse getLatestVideo(@RequestParam String channelId) {
        return youTubeService.getLatestVideo(channelId);
    }
}