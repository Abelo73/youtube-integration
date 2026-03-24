package com.ethio_connect.youtubeintegration.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class SearchResponseDTO {
    private List<LatestVideoResponse> videos;
    private String nextPageToken;
}