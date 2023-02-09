package com.example.instagram_diana.src.repository;

import com.example.instagram_diana.src.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<PostComment,Long> {

    int countByPostId(long postId);
}
