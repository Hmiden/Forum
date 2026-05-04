package com.Forum.Forum.Repository;

import com.Forum.Forum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ITopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCategoryId(Long categoryId);

    // Trending: topics with the most replies created in the last N days
    @Query("SELECT t FROM Topic t WHERE t.createdAt >= :since ORDER BY SIZE(t.replies) DESC")
    List<Topic> findTrendingTopics(@Param("since") LocalDateTime since);

    // Search by title (case-insensitive)
    @Query("SELECT t FROM Topic t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Topic> searchByTitle(@Param("title") String title);
}
