package com.patryk.gift_api.controller;

import com.patryk.gift_api.service.GiftLoader;
import com.patryk.gift_api.service.ImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/v1/imports")
@RequiredArgsConstructor
@Slf4j
public class ImportController {

    private final ImportService importService;
    private final GiftLoader giftLoader;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            importService.upload(file);
            return ResponseEntity.ok(Map.of("message", "File uploaded and processed successfully."));
        } catch (IllegalArgumentException e) {
            log.warn("Upload failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error during file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred. Please try again later."));
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateKids(@RequestParam int totalGifts) {
        giftLoader.loadGifts(totalGifts);
        return ResponseEntity.ok(Map.of("message", "Kids generation started for " + totalGifts + " kids."));
    }
}
