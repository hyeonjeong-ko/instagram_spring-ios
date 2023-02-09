package com.example.instagram_diana.src.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DMDto {
    private long toUserId;
    private String content;
}
