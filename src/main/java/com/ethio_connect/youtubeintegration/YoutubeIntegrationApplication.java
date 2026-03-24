package com.ethio_connect.youtubeintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application entry point
 */
@SpringBootApplication
@EnableCaching   // 🔥 THIS IS REQUIRED for @Cacheable to work
@EnableScheduling
public class YoutubeIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeIntegrationApplication.class, args);
    }
}