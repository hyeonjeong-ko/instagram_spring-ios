package com.example.instagram_diana.src.follow;

import com.example.instagram_diana.src.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    @Modifying
    @Query(value="INSERT INTO Follow(fromUserId,toUserId,createdAt) VALUES(:fromUserId,:toUserId,now())",nativeQuery = true)
    void mFollow(long fromUserId,long toUserId);

    @Modifying
    @Query(value = "DELETE FROM Follow WHERE fromUserId = :fromUserId AND toUserId =:toUserId",nativeQuery = true)
    void mUnFollow(Long fromUserId,Long toUserId);

//    @Query(value = "SELECT COUNT(*) FROM Follow WHERE fromUserId = :principalId AND toUserId = :pageUserId",nativeQuery = true)
//    int mFollowState(Long principalId,Long pageUserId);
//
//    @Query(value = "SELECT COUNT(*) FROM Follow WHERE fromUserId=:pageUserId",nativeQuery = true)
//    int mFollowCount(Long pageUserId);

    @Query(value = "SELECT EXISTS(select * from Follow where fromUserId=:fromUserId and toUserId = :toUserId)",nativeQuery = true)
    int checkFollow(Long fromUserId, Long toUserId);
}
