package com.ethio_connect.youtubeintegration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import io.netty.channel.ChannelOption;
import java.time.Duration;

/**
 * Configuration class for creating and customizing WebClient beans.
 *
 * WebClient is a non-blocking, reactive HTTP client used to call external APIs
 * such as the YouTube Data API.
 *
 * This configuration centralizes:
 * - Base URL
 * - Timeouts
 * - Connection settings
 *
 * This makes the application:
 * - Cleaner
 * - Easier to maintain
 * - More scalable
 */
@Configuration
public class WebClientConfig {

    /**
     * Base URL for YouTube API
     * Loaded from application.yml
     *
     * Example:
     * youtube:
     *   api:
     *     base-url: https://www.googleapis.com/youtube/v3
     */
    @Value("${youtube.api.base-url}")
    private String baseUrl;

    /**
     * Creates a reusable WebClient bean.
     *
     * Why this is important:
     * - Avoid creating WebClient multiple times (performance issue)
     * - Centralize configuration (timeouts, base URL, etc.)
     *
     * @return configured WebClient instance
     */
    @Bean
    public WebClient webClient() {

        /**
         * Configure low-level HTTP client (Reactor Netty)
         * This allows us to define timeouts and connection behavior.
         */
        HttpClient httpClient = HttpClient.create()
                // Connection timeout (time to establish connection)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)

                // Response timeout (max time waiting for response)
                .responseTimeout(Duration.ofSeconds(5));

        /**
         * Build and return WebClient
         */
        return WebClient.builder()
                // Base URL for all requests (no need to repeat in every call)
                .baseUrl(baseUrl)

                // Attach custom HTTP client
                .clientConnector(new ReactorClientHttpConnector(httpClient))

                /**
                 * Default headers (optional but useful)
                 * You can add API-wide headers here
                 */
                .defaultHeader("Content-Type", "application/json")

                /**
                 * Filter for logging requests (optional - good for debugging)
                 */
                .filter((request, next) -> {
                    System.out.println("Request: " + request.method() + " " + request.url());
                    return next.exchange(request);
                })

                .build();
    }
}