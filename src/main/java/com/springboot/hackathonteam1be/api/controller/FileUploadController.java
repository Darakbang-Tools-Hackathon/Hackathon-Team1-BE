package com.springboot.hackathonteam1be.api.controller;

import com.springboot.hackathonteam1be.core.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final ImageUploadService imageUploadService;

    @PostMapping("upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile")MultipartFile file) {
        try {
            // Service를 호출하여 파일 업로드 및 URL 반환
            String fileUrl = imageUploadService.uploadImage(file);

            // 성공 응답: Cloudinary로부터 받은 URL 반환
            return new ResponseEntity<>("업로드 성공. URL: " + fileUrl, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("업로드 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
