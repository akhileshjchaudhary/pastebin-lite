package com.example.pastebinlite.repository;

import com.example.pastebinlite.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<Paste, String> {
}
