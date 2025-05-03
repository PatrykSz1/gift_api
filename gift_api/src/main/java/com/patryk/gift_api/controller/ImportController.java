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

@RestController
@RequestMapping("api/v1/imports")
@RequiredArgsConstructor
@Slf4j
public class ImportController {

    private final ImportService importService;
    private final GiftLoader giftLoader;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            importService.upload(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IllegalArgumentException e) {
            log.warn("Upload failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateKids(@RequestParam int totalGifts) {
        giftLoader.loadGifts(totalGifts);
        return ResponseEntity.ok("Kids generation started for " + totalGifts + " kids.");
    }

    //TODO string
}
