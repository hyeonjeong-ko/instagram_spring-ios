package com.example.instagram_diana.src.repository;

import com.example.instagram_diana.src.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {


}
