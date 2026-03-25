package com.ethio_connect.youtubeintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YouTubeResponse {
    private String kind;
    private String etag;


    private String nextPageToken;
    private String prevPageToken;

    private PageInfo pageInfo;
    private List<VideoItem> items;

    @Data
    public static class PageInfo {
        private int totalResults;
        private int resultsPerPage;
    }
}