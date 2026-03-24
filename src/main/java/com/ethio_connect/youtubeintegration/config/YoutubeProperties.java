package com.ethio_connect.youtubeintegration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "youtube")
public class YoutubeProperties {

    private Api api;
    private List<String> channels;

    @Data
    public static class Api {
        private String key;
        private String baseUrl;
        private int maxResults;
    }
}