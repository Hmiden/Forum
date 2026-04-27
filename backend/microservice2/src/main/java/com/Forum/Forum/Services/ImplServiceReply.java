package com.Forum.Forum.Services;

import com.Forum.Forum.Repository.IReplyRepository;
import com.Forum.Forum.Repository.ITopicRepository;
import com.Forum.Forum.entity.Reply;
import com.Forum.Forum.entity.ReplyDTO;
import com.Forum.Forum.entity.Topic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImplServiceReply implements IServiceReply {
    private  IReplyRepository replyRepository;
    private ITopicRepository topicRepository;
    @Override
    public ReplyDTO create(Reply reply, Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        reply.setTopic(topic);
        Reply savedReply = replyRepository.save(reply);

        return ReplyMapper.toDTO(savedReply);
    }


    @Override
    public List<Reply> getByTopic(Long topicId) {
        return replyRepository.findByTopicId(topicId);
    }

    @Override
    public Reply update(Long id, Reply reply) {
        Reply existing = replyRepository.findById(id).orElseThrow();
        existing.setContent(reply.getContent());
        return replyRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        replyRepository.deleteById(id);
    }
}
