package com.example.pastebinlite.controller;

import com.example.pastebinlite.repository.PasteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    private final PasteRepository pasteRepository;

    public HealthController(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @GetMapping("/api/healthz")
    public Map<String, Boolean> health() {
        // Simple DB check
        pasteRepository.count();
        return Map.of("ok", true);
    }
}
