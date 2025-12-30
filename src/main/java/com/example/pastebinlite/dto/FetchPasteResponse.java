package com.example.pastebinlite.dto;

public class FetchPasteResponse {

    private String content;
    private Integer remaining_views;
    private String expires_at;

    public FetchPasteResponse(String content, Integer remainingViews, String expiresAt) {
        this.content = content;
        this.remaining_views = remainingViews;
        this.expires_at = expiresAt;
    }

    public String getContent() {
        return content;
    }

    public Integer getRemaining_views() {
        return remaining_views;
    }

    public String getExpires_at() {
        return expires_at;
    }
}
