package com.example.pastebinlite.controller;

import com.example.pastebinlite.entity.Paste;
import com.example.pastebinlite.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class PasteViewController {

    private final PasteService pasteService;

    public PasteViewController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @GetMapping("/p/{id}")
    public String viewPaste(
            @PathVariable String id,
            Model model,
            HttpServletRequest request
    ) {
        Optional<Paste> optionalPaste = pasteService.fetchPaste(id, request);

        if (optionalPaste.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("content", optionalPaste.get().getContent());
        return "paste";
    }
}
