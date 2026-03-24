package com.ethio_connect.youtubeintegration.service;

import com.ethio_connect.youtubeintegration.dto.LatestVideoResponse;
import com.ethio_connect.youtubeintegration.dto.VideoSearchRequest;

import java.util.List;

public interface YouTubeService {
    LatestVideoResponse getLatestVideo(String channelId);
    List<LatestVideoResponse> searchVideos(VideoSearchRequest request);
}