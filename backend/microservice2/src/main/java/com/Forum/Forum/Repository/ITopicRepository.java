package com.Forum.Forum.Repository;

import com.Forum.Forum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCategoryId(Long categoryId);

}
