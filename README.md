# YouTube Integration Service

A Spring Boot service to fetch the **latest video from a YouTube channel** and serve it via a REST API.  
This service includes:

- Scheduled fetching of latest videos from configured YouTube channels.
- In-memory caching for fast responses and reduced API calls.
- Clean layered architecture: Controller → Service → API Client → DTOs.
- Production-ready error handling and logging.

---

## 🔹 Features

1. **Fetch latest video** for a given YouTube channel.
2. **Scheduler**: Automatically updates the cache every configurable interval.
3. **In-memory caching**: Returns fast responses without hitting the YouTube API every time.
4. **Error handling**: Graceful handling of YouTube API errors.
5. **Configurable channels and intervals** via `application.yml`.

---

## 📂 Folder Structure

```text
youtube-integration/
│
├── src/main/java/com/ethio_connect/youtubeintegration
│   ├── client
│   │   └── YouTubeApiClient.java
│   ├── config
│   │   └── WebClientConfig.java
│   ├── controller
│   │   └── YouTubeController.java
│   ├── dto
│   │   ├── LatestVideoResponse.java
│   │   ├── YouTubeResponse.java
│   │   ├── VideoItem.java
│   │   ├── Snippet.java
│   │   ├── Id.java
│   │   ├── Thumbnails.java
│   │   └── Thumbnail.java
│   ├── exception
│   │   ├── GlobalExceptionHandler.java
│   │   └── YouTubeApiException.java
│   ├── service
│   │   ├── YouTubeService.java
│   │   ├── YouTubeSchedulerService.java
│   │   └── impl
│   │       └── YouTubeServiceImpl.java
│   └── YoutubeIntegrationApplication.java
│
└── src/main/resources
    └── application.yml

⚙️ Prerequisites
Java 17+ installed.
Maven 3.8+ installed.
A Google account to generate YouTube API credentials.
🔹 Step 1: Get YouTube API Key
Go to Google Cloud Console
.
Create a new project.
Navigate to APIs & Services → Library.
Enable the YouTube Data API v3.
Go to APIs & Services → Credentials → Create Credentials → API Key.
Copy the API key; this will be used in application.yml.
🔹 Step 2: Configure application.yml
server:
  port: 8080

spring:
  application:
    name: youtube-integration
  cache:
    type: simple

youtube:
  api:
    key: ${YOUTUBE_API_KEY}       # Set via environment variable
    base-url: https://www.googleapis.com/youtube/v3
  channels:
    - UCXXXXXXXXXXXXXXX           # Replace with your channel ID
    - UCYYYYYYYYYYYYYYY

app:
  scheduler:
    youtube:
      interval: 300000            # 5 minutes in milliseconds

logging:
  level:
    root: INFO
    com.ethio_connect.youtubeintegration: DEBUG

Important: Do NOT hardcode your API key in the file. Use an environment variable:

# Linux / Mac
export YOUTUBE_API_KEY=your_api_key

# Windows (PowerShell)
setx YOUTUBE_API_KEY "your_api_key"
🔹 Step 3: Build and Run
Clone the repository:
git clone <repository_url>
cd youtube-integration
Build the project:
mvn clean install
Run the service:
mvn spring-boot:run
You should see logs indicating the scheduler is running:
INFO  Starting scheduled YouTube update...
INFO  Updated cache for channel: UCXXXXXXXXXXXXXXX
INFO  Finished scheduled YouTube update.
🔹 Step 4: Test the Service

Once the service is running, use a REST client (Postman, Curl, or browser) to fetch the latest video:

GET http://localhost:8080/youtube/latest?channelId=UCXXXXXXXXXXXXXXX

Sample JSON Response:

{
  "videoId": "abc123XYZ",
  "title": "Latest Podcast Episode",
  "thumbnail": "https://i.ytimg.com/vi/abc123XYZ/mqdefault.jpg"
}
If the video is not in cache yet, the service will fetch it from the YouTube API.
🔹 Step 5: Frontend Integration

Embed the latest video on your website:

<iframe width="560" height="315"
        src="https://www.youtube.com/embed/abc123XYZ"
        title="YouTube video player"
        frameborder="0"
        allowfullscreen>
</iframe>

Replace abc123XYZ with the videoId returned by the API.

🔹 Step 6: Logs & Error Handling
Scheduler logs are written using SLF4J.
Global exception handling ensures:
YouTube API errors → HTTP 502
Internal errors → HTTP 500

Example log:

ERROR Failed to update channel: UCXXXXXXXXXXXXXXX
🔹 Step 7: Notes / Best Practices
Do not call YouTube API on every request; use scheduler cache.
Scheduler interval can be adjusted in application.yml.
For multiple channels, just add them under youtube.channels.
Consider using Redis cache if scaling to multiple servers.
Never expose API keys publicly.
🔹 Step 8: Extending the Service
Add database support for dynamic channels.
Use Redis cache for distributed systems.
Add multi-channel fetch for dashboards or podcasts.
Deploy using Docker + Kubernetes for production.
🔹 Reference
YouTube Data API v3: https://developers.google.com/youtube/v3
Spring Boot Scheduling: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.scheduling
Spring WebClient: https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
✅ Summary

This service provides a reliable, production-ready way to:

Get the latest video from any YouTube channel
Cache it in memory
Serve it via a REST API
Schedule updates to reduce API calls and improve performance

---

If you want, I can **also write a `TESTING.md`** with step-by-step **unit tests and integration tests** for this service, so you can **automatically verify everything works** before deploying.  

Do you want me to create that next?