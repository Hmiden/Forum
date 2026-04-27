package com.example.resource_service.repository;

import com.example.resource_service.entity.ResourceLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceLikeRepository extends JpaRepository<ResourceLike, Long> {

    boolean existsByResourceIdAndUserId(Long resourceId, Long userId);
}