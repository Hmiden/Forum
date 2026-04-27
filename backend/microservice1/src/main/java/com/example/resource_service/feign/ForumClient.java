package com.example.resource_service.feign;

import com.example.resource_service.entity.Dto.TopicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Forum", url = "http://localhost:8082")
public interface ForumClient {

    @GetMapping("/topic/{id}")
    TopicDTO getTopicById(@PathVariable Long id);


    // ✅ GET ALL TOPICS (IMPORTANT: must match backend /topic)
    @GetMapping("/topic/dto")
    List<TopicDTO> getAllTopics();

}