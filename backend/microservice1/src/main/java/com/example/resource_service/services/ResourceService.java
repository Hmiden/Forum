package com.example.resource_service.services;

import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.ResourceStatus;

import java.util.List;

public interface ResourceService {

    ResourceResponseDTO addResource(Resource resource);

    List<ResourceResponseDTO> getAllResources();

    List<ResourceResponseDTO> getResourcesByTopic(Long topicId);

    ResourceResponseDTO updateResource(Long id, Resource resource);

    void deleteResource(Long id);

    ResourceResponseDTO updateResourceStatus(Long id, ResourceStatus status);
}