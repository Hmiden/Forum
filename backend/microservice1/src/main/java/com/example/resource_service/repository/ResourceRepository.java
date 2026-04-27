package com.example.resource_service.repository;


import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByTopicId(Long topicId);

    List<Resource> findByType(ResourceType type);
}