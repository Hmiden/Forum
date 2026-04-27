package com.Forum.Forum.Services;

import com.Forum.Forum.entity.Reply;
import com.Forum.Forum.entity.ReplyDTO;

import java.util.List;

public interface IServiceReply {
    public ReplyDTO create(Reply reply, Long topicId);
    List<Reply> getByTopic(Long topicId);
    Reply update(Long id, Reply reply);
    void delete(Long id);
}
