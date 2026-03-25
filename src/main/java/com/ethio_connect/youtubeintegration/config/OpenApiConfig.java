package com.ethio_connect.youtubeintegration.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    static {
        org.springdoc.core.utils.SpringDocUtils.getConfig().addAnnotationsToIgnore(
                org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient.class
        );
    }
}