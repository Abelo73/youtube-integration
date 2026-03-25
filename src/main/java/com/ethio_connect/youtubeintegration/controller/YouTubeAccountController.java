package com.ethio_connect.youtubeintegration.controller;

import com.ethio_connect.youtubeintegration.dto.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/youtube")
@RequiredArgsConstructor
public class YouTubeAccountController {

    private final WebClient.Builder webClientBuilder;


    @GetMapping("/my-playlists")
    public Mono<BaseResponseDTO<Object>> getMyPlaylists(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            @RequestParam(required = false) String pageToken) {

        String token = authorizedClient.getAccessToken().getTokenValue();

        return webClientBuilder.build().get()
                .uri(uriBuilder -> {
                    uriBuilder
                            .scheme("https")
                            .host("www.googleapis.com")
                            .path("/youtube/v3/playlists")
                            .queryParam("part", "snippet")
                            .queryParam("mine", "true")
                            .queryParam("maxResults", 5);

                    if (pageToken != null && !pageToken.isEmpty()) {
                        uriBuilder.queryParam("pageToken", pageToken);
                    }

                    return uriBuilder.build();
                })
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Object.class)
                .map(data -> BaseResponseDTO.ok(data, "Fetched user playlists successfully"));
    }
}