package com.Forum.Forum.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReplyDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long topicId;
}
