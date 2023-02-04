package com.example.instagram_diana.src.user.model;

import com.example.instagram_diana.src.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private boolean pageOwnerState;
    private int postCount;
    private boolean followState; // 구독한 상태인지
    private int follower;
    private int following;
    private User user;
}
