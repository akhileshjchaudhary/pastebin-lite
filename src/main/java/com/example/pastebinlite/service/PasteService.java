package com.example.pastebinlite.service;

import com.example.pastebinlite.entity.Paste;
import com.example.pastebinlite.repository.PasteRepository;
import com.example.pastebinlite.util.TimeProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasteService {

    private final PasteRepository pasteRepository;
    private final TimeProvider timeProvider;

    public PasteService(PasteRepository pasteRepository, TimeProvider timeProvider) {
        this.pasteRepository = pasteRepository;
        this.timeProvider = timeProvider;
    }

    /**
     * Create and save a new Paste
     */
    public Paste createPaste(
            String content,
            Integer ttlSeconds,
            Integer maxViews,
            HttpServletRequest request
    ) {
        long now = timeProvider.now(request);

        Paste paste = new Paste();
        paste.setId(UUID.randomUUID().toString());
        paste.setContent(content);
        paste.setCreatedAt(now);
        paste.setCurrentViews(0);
        paste.setMaxViews(maxViews);

        if (ttlSeconds != null) {
            paste.setExpiresAt(now + ttlSeconds * 1000L);
        }

        return pasteRepository.save(paste);
    }

    /**
     * Fetch a paste and increment view count
     */
    @Transactional
    public Optional<Paste> fetchPaste(String id, HttpServletRequest request) {
        Optional<Paste> optionalPaste = pasteRepository.findById(id);
        if (optionalPaste.isEmpty()) {
            return Optional.empty();
        }

        Paste paste = optionalPaste.get();
        long now = timeProvider.now(request);

        // Check expiry
        if (isExpired(paste, now)) {
            return Optional.empty();
        }

        // Check view limit
        if (isViewLimitExceeded(paste)) {
            return Optional.empty();
        }

        // Increment views
        paste.setCurrentViews(paste.getCurrentViews() + 1);

        return Optional.of(paste);
    }

    private boolean isExpired(Paste paste, long now) {
        return paste.getExpiresAt() != null && now >= paste.getExpiresAt();
    }

    private boolean isViewLimitExceeded(Paste paste) {
        return paste.getMaxViews() != null &&
                paste.getCurrentViews() >= paste.getMaxViews();
    }
}
