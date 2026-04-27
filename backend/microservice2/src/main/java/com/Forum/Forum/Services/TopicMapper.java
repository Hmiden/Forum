package com.Forum.Forum.Services;


import com.Forum.Forum.entity.Topic;
import com.Forum.Forum.entity.TopicDTO;

public class TopicMapper {
    public static TopicDTO toDTO(Topic topic) {
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setContent(topic.getContent());
        dto.setCreatedAt(topic.getCreatedAt());
        if(topic.getCategory() != null) {
            dto.setCategoryId(topic.getCategory().getId());
        }
        dto.setReplyCount(topic.getReplyCount());
        dto.setAuthorEmail("user@example.com"); // Placeholder
        return dto;
    }
}
