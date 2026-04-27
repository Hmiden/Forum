package com.Forum.Forum.Services;




import com.Forum.Forum.entity.Topic;
import com.Forum.Forum.entity.TopicDTO;

import java.util.List;

public interface IServiceTopic {
    public TopicDTO create(Topic topic, Long categoryId);
    List<Topic> getAll();
    Topic getById(Long id);
    List<Topic> getByCategory(Long categoryId);
    Topic update(Long id, Topic topic);
    void delete(Long id);
    List<TopicDTO> getAllDTO();
}
