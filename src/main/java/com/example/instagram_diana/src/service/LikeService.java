package com.example.instagram_diana.src.service;


import com.example.instagram_diana.src.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    @Transactional
    public void Like(long postId,long loginUserId) {likeRepository.postLike(postId,loginUserId);}

    @Transactional
    public void unLike(long postId,long loginUserId) {likeRepository.postUnLike(postId,loginUserId);}


    @Transactional
    public long countPostLikes(long postId){
        return likeRepository.countBypostId(postId);
    }


}
