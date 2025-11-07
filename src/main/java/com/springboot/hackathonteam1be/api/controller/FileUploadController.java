package com.springboot.hackathonteam1be.api.controller;

import com.springboot.hackathonteam1be.core.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final ImageUploadService imageUploadService;
}
