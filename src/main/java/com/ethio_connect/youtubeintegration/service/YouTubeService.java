package com.ethio_connect.youtubeintegration.service;

import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;

public interface YouTubeService {
    LatestVideoResponse getLatestVideo(String channelId);
}