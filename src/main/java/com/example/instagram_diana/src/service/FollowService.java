package com.example.instagram_diana.src.service;


import com.example.instagram_diana.src.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;



    private final EntityManager em;

//    @Transactional
//    public List<FollowDto> followList(){
//
//    }

    @Transactional
    public void follow(long fromUserId,long toUserId){

        followRepository.mFollow(fromUserId,toUserId);
    }


    @Transactional
    public void unFollow(Long fromUserId,Long toUserId){

        followRepository.mUnFollow(fromUserId,toUserId);
    }

    @Transactional
    public int checkFollow(Long fromUserId, Long toUserId) {

        return followRepository.checkFollow(fromUserId,toUserId);
    }
}
