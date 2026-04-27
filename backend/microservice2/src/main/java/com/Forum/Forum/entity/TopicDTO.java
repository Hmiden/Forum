package com.Forum.Forum.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TopicDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long categoryId;
    private String authorEmail;
    private int replyCount;
}
