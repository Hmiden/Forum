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

    // ================= CREATE =================
    @Override
    public ResourceResponseDTO addResource(Resource resource) {

        TopicDTO topic = forumClient.getTopicById(resource.getTopicId());

        resource.setCreatedAt(LocalDateTime.now());

        Resource saved = resourceRepository.save(resource);

        return mapToDTO(saved, topic.getTitle());
    }

    // ================= GET ALL =================
    @Override
    public List<ResourceResponseDTO> getAllResources() {

        // 🔥 OPTIMISATION: 1 appel au lieu de N appels
        List<TopicDTO> topics = forumClient.getAllTopics();

        Map<Long, String> topicMap = topics.stream()
                .collect(Collectors.toMap(TopicDTO::getId, TopicDTO::getTitle));

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

    // ================= MAPPER =================
    private ResourceResponseDTO mapToDTO(Resource resource, String topicName) {

        return new ResourceResponseDTO(
                resource.getId(),
                resource.getTitle(),
                resource.getDescription(),
                resource.getUrl(),
                resource.getType().name(),
                resource.getTopicId(),
                topicName
        );
    }
}