package com.ethio_connect.youtubeintegration.service;

import com.ethio_connect.youtubeintegration.config.YoutubeProperties;
import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class YouTubeSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(YouTubeSchedulerService.class);

    private final YouTubeService youTubeService;
    private final YoutubeProperties youtubeProperties;

    private final Map<String, LatestVideoResponse> cache = new ConcurrentHashMap<>();

    @Scheduled(fixedRateString = "${app.scheduler.youtube.interval}")
    public void updateLatestVideos() {
        log.info("Starting scheduled YouTube update...");

        for (String channelId : youtubeProperties.getChannels()) {
            try {
                LatestVideoResponse video = youTubeService.getLatestVideo(channelId);

                if (video != null) {
                    cache.put(channelId, video);
                    log.info("Updated cache for channel: {}", channelId);
                } else {
                    log.warn("No video found for channel: {}", channelId);
                }
            } catch (Exception e) {
                log.error("Failed to update channel: {}", channelId, e);
            }
        }

        log.info("Finished scheduled YouTube update.");
    }

    public LatestVideoResponse getCachedVideo(String channelId) {
        return cache.get(channelId);
    }
}