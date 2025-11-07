package com.springboot.hackathonteam1be.core.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("업로드할 파일이 없습니다.");
        }

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
            ObjectUtils.emptyMap()
        );

        return (String) uploadResult.get("secure_url");
    }



}
