package com.Forum.Forum.Services;

import com.Forum.Forum.entity.Reply;
import com.Forum.Forum.entity.ReplyDTO;

public class ReplyMapper {
    public static ReplyDTO toDTO(Reply reply) {
        ReplyDTO dto = new ReplyDTO();
        dto.setId(reply.getId());
        dto.setContent(reply.getContent());
        dto.setCreatedAt(reply.getCreatedAt());
        if (reply.getTopic() != null) {
            dto.setTopicId(reply.getTopic().getId());
        }
        return dto;
    }
}
