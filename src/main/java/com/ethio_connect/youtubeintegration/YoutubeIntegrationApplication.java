package com.ethio_connect.youtubeintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main application entry point
 */
@SpringBootApplication
@EnableCaching   // 🔥 THIS IS REQUIRED for @Cacheable to work
public class YoutubeIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeIntegrationApplication.class, args);
    }
}