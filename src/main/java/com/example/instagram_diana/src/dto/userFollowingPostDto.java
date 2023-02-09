package com.example.instagram_diana.src.dto;


import com.example.instagram_diana.src.model.PostMedia;
import com.example.instagram_diana.src.repository.DayDao;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class userFollowingPostDto {
    private Long postId;
    private String userName;
    private String content;
    private List<String> imgUrls;
    private long likeCount;
    private long commentCount;

    // 추가된 부분들
    private long postUserId;
    private int likeState;
    private String userProfileUrl;
    private DayDto dayInfo;
}
