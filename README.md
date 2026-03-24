# YouTube Integration Service (Enterprise Edition)

A Spring Boot 3.2+ service to fetch the **latest video from a YouTube channel** and perform **advanced searches** via a REST API.

### 🔹 New in this Version:
- **BaseResponseDTO**: Standardized API response format for all endpoints.
- **Advanced Search**: Search by query, order (date, relevance, etc.), and max results.
- **Pagination Support**: Full support for `nextPageToken` to browse multiple pages of results.
- **Enhanced Error Handling**: Global Exception Handler returning consistent JSON error objects.

---

## 🔹 Features
1. **Fetch Latest Video**: Cached lookup for specific channel IDs.
2. **Advanced Search**: Live search across YouTube with filters.
3. **Pagination**: Seamlessly move between result pages using tokens.
4. **Scheduler**: Automatically updates the cache every configurable interval.
5. **In-memory Caching**: Reduces API quota usage significantly.

---

## 📂 Folder Structure
(Updated to include new DTOs and Exception structures)
```text
youtube-integration/
├── src/main/java/com/ethio_connect/youtubeintegration
│   ├── dto
│   │   ├── BaseResponseDTO.java      
│   │   ├── SearchResponseDTO.java    
│   │   ├── ErrorResponse.java         
│   │   ├── VideoSearchRequest.java   
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
```

```
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
```
```
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
```
Important: Do NOT hardcode your API key in the file. Use an environment variable:

# Linux / Mac
export YOUTUBE_API_KEY=your_api_key

# Windows (PowerShell)
``` 
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

```
Once the service is running, use a REST client (Postman, Curl, or browser) to fetch the latest video:

GET http://localhost:8080/youtube/latest?channelId=UCXXXXXXXXXXXXXXX

Sample JSON Response:

``` 
{
  "timestamp": "2026-03-24T19:50:00",
  "success": true,
  "message": "Search completed successfully",
  "nextPageToken": "CAUQAA",
  "data": [
    {
      "videoId": "abc123XYZ",
      "title": "Spring Boot Tutorial",
      "videoUrl": "[https://www.youtube.com/watch?v=abc123XYZ](https://www.youtube.com/watch?v=abc123XYZ)"
    }
  ]
}
```


``` 
🔹 Step 3: Error Handling
The API now returns structured errors for 404, 500, and 502 (Bad Gateway) errors:

JSON
{
  "timestamp": "...",
  "status": 502,
  "error": "External YouTube Service Error",
  "message": "Quota exceeded",
  "path": "/api/v1/youtube/search"
}
```


``
---

## 🚀 Updated Postman Collection (V4)

Save this JSON as `YouTube_Integration_v4.json` and import it. It includes the new **Base URL with Context Path** and the **Pagination Token** logic.

```json
{
	"info": {
		"name": "YouTube Integration - Enterprise",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "1. Search - Page 1",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/api/v1/youtube/search?q=java spring boot&maxResults=5",
					"host": ["{{baseUrl}}"],
					"path": ["api", "v1", "youtube", "search"],
					"query": [
						{ "key": "q", "value": "java spring boot" },
						{ "key": "maxResults", "value": "5" }
					]
				},
				"description": "Step 1: Run this and copy 'nextPageToken' from the response."
			}
		},
		{
			"name": "2. Search - Page 2 (Pagination)",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/api/v1/youtube/search?q=java spring boot&maxResults=5&pageToken=PASTE_TOKEN_HERE",
					"host": ["{{baseUrl}}"],
					"path": ["api", "v1", "youtube", "search"],
					"query": [
						{ "key": "q", "value": "java spring boot" },
						{ "key": "maxResults", "value": "5" },
						{ "key": "pageToken", "value": "PASTE_TOKEN_HERE", "description": "Paste the token from the first request here." }
					]
				}
			}
		},
		{
			"name": "3. Get Latest (Cached)",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/api/v1/youtube/latest/UCV3L1bFJs0Wn-Rmp9cVfPWg",
					"host": ["{{baseUrl}}"],
					"path": ["api", "v1", "youtube", "latest", "UCV3L1bFJs0Wn-Rmp9cVfPWg"]
				}
			}
		}
	],
	"variable": [
		{
			"key": "baseUrl",
			"value": "http://localhost:8080/youtube-integration",
			"type": "string"
		}
	]
}

```
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

``` 
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
```
🔹 Reference
``` 
YouTube Data API v3: https://developers.google.com/youtube/v3
Spring Boot Scheduling: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.scheduling
Spring WebClient: https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
```
✅ Summary

This service provides a reliable, production-ready way to:

Get the latest video from any YouTube channel
Cache it in memory
Serve it via a REST API
Schedule updates to reduce API calls and improve performance

---

If you want, I can **also write a `TESTING.md`** with step-by-step **unit tests and integration tests** for this service, so you can **automatically verify everything works** before deploying.  

Do you want me to create that next?