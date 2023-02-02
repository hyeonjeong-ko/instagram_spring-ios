package com.example.instagram_diana.src.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storyId", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "contentType", length = 20)
    private String contentType;

//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "postId")
    private Long postId;

    @Lob
    @Column(name = "mediaUrl")
    private String mediaUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt",nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "status", nullable = false, length = 20)
    private String status;


}