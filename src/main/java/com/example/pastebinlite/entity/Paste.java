package com.example.pastebinlite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pastes")
public class Paste {

    @Id
    @Column(length = 64)
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "expires_at")
    private Long expiresAt;

    @Column(name = "max_views")
    private Integer maxViews;

    @Column(name = "current_views", nullable = false)
    private Integer currentViews;

    // ---- getters and setters ----

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getMaxViews() {
        return maxViews;
    }

    public void setMaxViews(Integer maxViews) {
        this.maxViews = maxViews;
    }

    public Integer getCurrentViews() {
        return currentViews;
    }

    public void setCurrentViews(Integer currentViews) {
        this.currentViews = currentViews;
    }
}
