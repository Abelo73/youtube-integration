package com.ethio_connect.youtubeintegration.service.impl;

import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;

/**
 * Service interface for YouTube operations.
 *
 * Defines business logic contracts.
 * Implementation will be in YouTubeServiceImpl.
 */
public interface YouTubeService {

    /**
     * Fetch the latest video from a given YouTube channel
     *
     * @param channelId YouTube channel ID
     * @return LatestVideoResponse containing video details
     */
    LatestVideoResponse getLatestVideo(String channelId);
}