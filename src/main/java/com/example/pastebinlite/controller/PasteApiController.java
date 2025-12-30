package com.example.pastebinlite.controller;

import com.example.pastebinlite.dto.CreatePasteRequest;
import com.example.pastebinlite.dto.CreatePasteResponse;
import com.example.pastebinlite.dto.FetchPasteResponse;
import com.example.pastebinlite.entity.Paste;
import com.example.pastebinlite.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pastes")
public class PasteApiController {

    private final PasteService pasteService;

    public PasteApiController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePasteResponse createPaste(
            @Valid @RequestBody CreatePasteRequest request,
            HttpServletRequest httpRequest
    ) {
        Paste paste = pasteService.createPaste(
                request.getContent(),
                request.getTtl_seconds(),
                request.getMax_views(),
                httpRequest
        );

        String url = httpRequest.getScheme() + "://" +
                httpRequest.getServerName() + ":" +
                httpRequest.getServerPort() +
                "/p/" + paste.getId();

        return new CreatePasteResponse(paste.getId(), url);
    }

    @GetMapping("/{id}")
    public FetchPasteResponse fetchPaste(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        return pasteService.fetchPaste(id, request)
                .map(paste -> {

                    Integer remainingViews = null;
                    if (paste.getMaxViews() != null) {
                        remainingViews = paste.getMaxViews() - paste.getCurrentViews();
                    }

                    String expiresAt = null;
                    if (paste.getExpiresAt() != null) {
                        expiresAt = java.time.Instant
                                .ofEpochMilli(paste.getExpiresAt())
                                .toString();
                    }

                    return new FetchPasteResponse(
                            paste.getContent(),
                            remainingViews,
                            expiresAt
                    );
                })
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND
                ));
    }

}
