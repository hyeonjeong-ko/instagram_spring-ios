package com.example.instagram_diana.src.follow;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowDto {
    private int id;
    private String userName;
    private Integer followState;
    private Integer equalUserState;
}
