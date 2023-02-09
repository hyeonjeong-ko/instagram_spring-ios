package com.example.instagram_diana.src.service;

import com.example.instagram_diana.src.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public int commentNum(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}
