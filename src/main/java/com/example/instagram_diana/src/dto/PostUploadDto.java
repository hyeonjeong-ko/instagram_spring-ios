package com.example.instagram_diana.src.dto;

import com.example.instagram_diana.src.model.Post;
import com.example.instagram_diana.src.model.PostMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostUploadDto {
    private Long userId;
    private String content;
    private List<String> imgUrlList;
}
