package com.example.instagram_diana.src.service;


import com.example.instagram_diana.config.BaseException;
import com.example.instagram_diana.config.BaseResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.instagram_diana.config.BaseResponseStatus.POST_CANNOT_DELETE;
import static com.example.instagram_diana.config.BaseResponseStatus.POST_ID_NOT_EXISTS;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;
    private final UserService userService;

    @Transactional
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

    @Transactional
    public boolean checkPostExist(long postId){
        return postRepository.existsById(postId);
    }


    @Transactional
    public long getPostWriter(long postId){
        Post postEntity= postRepository.findById(postId).get();
        return postEntity.getUser().getId();
    }

    @Transactional
    public List<Post> getUserFollowingPosts(long loignUserId){
        return postRepository.userFollowingPosts(loignUserId);
    }


    @Transactional
    //포스트 내용 수정
    public void modifyPostContent(long postId, Long loginUserId,String content) {

        Post post = postRepository.findById(postId).get();

        post.setContent(content);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(long postId,long loginUserId) throws BaseException {

        Post post = postRepository.findById(postId).get();

        // 유저 아이디가 다를시 에러
        if(post.getUser().getId()!=loginUserId) {
            throw  new BaseException(POST_CANNOT_DELETE);

        }

        postRepository.deleteById(postId);
    }
}
