package com.example.instagram_diana.src.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DMRoomDto {
    private long dmId;
    private String content;
    private long fromUserId;
    private long toUserId;

    private int equalUserState;
}
