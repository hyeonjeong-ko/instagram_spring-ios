package com.example.instagram_diana.src.service;


import com.example.instagram_diana.src.dto.PostUploadDto;
import com.example.instagram_diana.src.model.Post;
import com.example.instagram_diana.src.model.PostMedia;
import com.example.instagram_diana.src.model.User;
import com.example.instagram_diana.src.repository.PostMediaRepository;
import com.example.instagram_diana.src.repository.PostRepository;
import com.example.instagram_diana.src.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.metadata.PostgresCallMetaDataProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;
    private final UserService userService;
    public void postUpload(PostUploadDto postUploadDto, Long loginId) {

        // post DB저장
        Post post = new Post();
        User user = userService.findUserById(loginId);

        post.setUser(user);
        post.setContent(postUploadDto.getContent());
        post.setStatus("ACTIVE");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        // postMedia 모두 별도 DB저장
        for(int i=0;i<postUploadDto.getImgUrlList().size();i++){
            PostMedia postMedia = new PostMedia();
            postMedia.setPost(post);
            // 미디어타입필터링(구현예정)
            postMedia.setMediaType("PHOTO");
            postMedia.setMediaUrl(postUploadDto.getImgUrlList().get(i));
            postMedia.setStatus("ACTIVE");
            postMedia.setCreatedAt(LocalDateTime.now());
            postMedia.setUpdatedAt(LocalDateTime.now());
            postMediaRepository.save(postMedia);
        }
    }




}
