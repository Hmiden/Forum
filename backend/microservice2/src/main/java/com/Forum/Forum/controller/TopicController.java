package com.Forum.Forum.controller;

import com.Forum.Forum.Services.IServiceCategory;
import com.Forum.Forum.Services.IServiceTopic;
import com.Forum.Forum.entity.Category;
import com.Forum.Forum.entity.Topic;
import com.Forum.Forum.entity.TopicDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/topic")
@CrossOrigin("*")
public class TopicController {
    private IServiceTopic serviceTopic;
    @PostMapping("/category/{categoryId}")
    public TopicDTO create(@RequestBody Topic topic,
                           @PathVariable Long categoryId) {
        return serviceTopic.create(topic, categoryId);
    }



    @GetMapping
    public List<Topic> getAll() {
        return serviceTopic.getAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<Topic> getByCategory(@PathVariable Long categoryId) {
        return serviceTopic.getByCategory(categoryId);
    }

    @PutMapping("/{id}")
    public Topic update(@PathVariable Long id, @RequestBody Topic topic) {
        return serviceTopic.update(id, topic);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        serviceTopic.delete(id);
    }

    @GetMapping("/dto")
    public List<TopicDTO> getAllDTO() {
        return serviceTopic.getAllDTO();
    }

}
