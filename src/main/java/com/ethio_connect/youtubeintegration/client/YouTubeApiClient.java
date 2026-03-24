package com.ethio_connect.youtubeintegration.client;

import com.ethio_connect.youtubeintegration.dto.VideoSearchRequest;
import com.ethio_connect.youtubeintegration.dto.YouTubeResponse;
import com.ethio_connect.youtubeintegration.exception.YouTubeApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Client responsible for communicating with YouTube Data API.
 *
 * This class:
 * - Calls external YouTube API
 * - Maps response into DTO (YouTubeResponse)
 * - Handles API errors cleanly
 *
 * IMPORTANT:
 * - No business logic here
 * - Only external communication
 */
@Component
@RequiredArgsConstructor
public class YouTubeApiClient {

    private final WebClient webClient;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.api.max-results}")
    private int maxResults;

    /**
     * Fetch latest videos from a YouTube channel
     *
     * @param channelId YouTube channel ID
     * @return YouTubeResponse mapped from API JSON
     */
    public YouTubeResponse fetchLatestVideos(String channelId) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("key", apiKey)
                        .queryParam("channelId", channelId)
                        .queryParam("part", "snippet")
                        .queryParam("order", "date")
                        .queryParam("maxResults", maxResults)
                        .build()
                )
                .retrieve()

                /**
                 * Handle HTTP errors (4xx, 5xx)
                 */
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody ->
                                        Mono.error(new YouTubeApiException(
                                                "YouTube API Error: " + errorBody
                                        ))
                                )
                )

                /**
                 * Convert JSON → Java DTO automatically (Jackson)
                 */
                .bodyToMono(YouTubeResponse.class)

                /**
                 * Block since we're using it in a non-reactive service
                 */
                .block();
    }

    public YouTubeResponse searchVideos(VideoSearchRequest request) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/search")
                            .queryParam("key", apiKey)
                            .queryParam("part", "snippet")
                            .queryParam("type", "video");

                    // Expert Tip: Add filters only if they are present
                    if (request.getQuery() != null) uriBuilder.queryParam("q", request.getQuery());
                    if (request.getChannelId() != null) uriBuilder.queryParam("channelId", request.getChannelId());
                    if (request.getOrder() != null) uriBuilder.queryParam("order", request.getOrder());
                    if (request.getVideoDuration() != null) uriBuilder.queryParam("videoDuration", request.getVideoDuration());
                    uriBuilder.queryParam("maxResults", request.getMaxResults() != null ? request.getMaxResults() : maxResults);

                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(YouTubeResponse.class)
                .block();
    }
}