package com.Forum.Forum.Services;

import com.Forum.Forum.Repository.ICategoryRepository;
import com.Forum.Forum.Repository.ITopicRepository;

import com.Forum.Forum.entity.Category;
import com.Forum.Forum.entity.Topic;
import com.Forum.Forum.entity.TopicDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ImplServiceTopic implements IServiceTopic {

    private ITopicRepository repository;
    private ICategoryRepository categoryRepository;
    private BadwordService badwordService;

    @Override
    public TopicDTO create(Topic topic, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Censor badwords
        topic.setTitle(badwordService.censorText(topic.getTitle()));
        topic.setContent(badwordService.censorText(topic.getContent()));

        topic.setCategory(category);
        Topic savedTopic = repository.save(topic);

        return TopicMapper.toDTO(savedTopic);
    }

    @Override
    public List<Topic> getAll() {
        return repository.findAll();
    }

    @Override
    public Topic getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Topic> getByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public Topic update(Long id, Topic topic) {
        Topic existing = getById(id);
        
        // Censor badwords
        existing.setTitle(badwordService.censorText(topic.getTitle()));
        existing.setContent(badwordService.censorText(topic.getContent()));
        
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TopicDTO> getAllDTO() {
        return repository.findAll().stream()
                .map(TopicMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    // =================== ADVANCED BUSINESS LOGIC ===================

    /**
     * Returns the trending topics of the last N days, sorted by reply count.
     * Trending = topics created in the last `days` days, ordered by number of replies DESC.
     */
    @Override
    public List<Topic> getTrendingTopics(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return repository.findTrendingTopics(since);
    }

    /**
     * Full-text search of topics by title (case-insensitive).
     */
    @Override
    public List<Topic> searchByTitle(String title) {
        return repository.searchByTitle(title);
    }
}
