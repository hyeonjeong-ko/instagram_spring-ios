package com.example.instagram_diana.src.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
//@ToString(exclude = "User")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    // 이부분을 아래로 수정함.
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "userId", nullable = false)
//    private User user;

    @Lob
    @Column(name = "content", nullable = true)
    private String content;

    //추가된부분 ---------------
    @JsonIgnoreProperties({"posts"})
    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    //------------------------

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt",nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

}