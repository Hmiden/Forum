package com.Forum.Forum.Repository;

import com.Forum.Forum.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByTopicId(Long topicId);

}
