package com.example.resource_service.services.impl;

import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.entity.Dto.TopicDTO;
import com.example.resource_service.entity.Resource;
import com.example.resource_service.feign.ForumClient;
import com.example.resource_service.repository.ResourceRepository;
import com.example.resource_service.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ForumClient forumClient;
    private final com.example.resource_service.services.GeminiService geminiService;

    // ================= CREATE =================
    @Override
    public ResourceResponseDTO addResource(Resource resource) {

        TopicDTO topic = forumClient.getTopicById(resource.getTopicId());

        resource.setCreatedAt(LocalDateTime.now());
        
        // Generate AI Summary
        if (resource.getDescription() != null && !resource.getDescription().isEmpty()) {
            resource.setAiSummary(geminiService.summarize(resource.getDescription()));
        }

        Resource saved = resourceRepository.save(resource);

        return mapToDTO(saved, topic.getTitle());
    }

    // ================= GET ALL =================
    @Override
    public List<ResourceResponseDTO> getAllResources() {

        List<TopicDTO> topics = null;
        try {
            topics = forumClient.getAllTopics();
        } catch (Exception e) {
            System.err.println("Failed to fetch topics from Forum service: " + e.getMessage());
        }

        Map<Long, String> topicMap = (topics != null) ? 
            topics.stream().collect(Collectors.toMap(TopicDTO::getId, TopicDTO::getTitle)) : 
            new java.util.HashMap<>();

        // Return all resources as requested
        return resourceRepository.findAll()
                .stream()
                .map(r -> mapToDTO(
                        r,
                        topicMap.getOrDefault(r.getTopicId(), "Deleted Topic")
                ))
                .toList();
    }

    // ================= GET BY TOPIC =================
    @Override
    public List<ResourceResponseDTO> getResourcesByTopic(Long topicId) {

        String topicName = forumClient.getTopicById(topicId).getTitle();

        return resourceRepository.findByTopicId(topicId)
                .stream()
                .map(r -> mapToDTO(r, topicName))
                .toList();
    }

    // ================= UPDATE =================
    @Override
    public ResourceResponseDTO updateResource(Long id, Resource updated) {

        Resource r = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        r.setTitle(updated.getTitle());
        r.setDescription(updated.getDescription());
        r.setUrl(updated.getUrl());
        r.setType(updated.getType());

        Resource saved = resourceRepository.save(r);

        String topicName = forumClient.getTopicById(saved.getTopicId()).getTitle();

        return mapToDTO(saved, topicName);
    }

    // ================= DELETE =================
    @Override
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
    
    // ================= APPROVAL (ADVANCED LOGIC) =================
    public ResourceResponseDTO updateResourceStatus(Long id, com.example.resource_service.entity.ResourceStatus status) {
        Resource r = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        r.setStatus(status);
        Resource saved = resourceRepository.save(r);
        String topicName = forumClient.getTopicById(saved.getTopicId()).getTitle();
        return mapToDTO(saved, topicName);
    }

    // ================= MAPPER =================
    private ResourceResponseDTO mapToDTO(Resource resource, String topicName) {

        return new ResourceResponseDTO(
                resource.getId(),
                resource.getTitle(),
                resource.getDescription(),
                resource.getUrl(),
                resource.getType() != null ? resource.getType().name() : "OTHER",
                resource.getTopicId(),
                topicName,
                resource.getAiSummary(),
                resource.getStatus() != null ? resource.getStatus().name() : "PENDING"
        );
    }
}