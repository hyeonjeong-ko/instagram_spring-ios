package com.example.instagram_diana.src.testS3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileDto {
    private String title;
    private String url;
    private MultipartFile file;
}