package com.example.resource_service.controller;

import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    // ✅ CREATE
    @PostMapping
    public ResourceResponseDTO addResource(@RequestBody Resource resource) {
        return resourceService.addResource(resource);
    }

    // ✅ GET ALL
    @GetMapping
    public List<ResourceResponseDTO> getAll() {
        return resourceService.getAllResources();
    }

    // ✅ GET BY TOPIC
    @GetMapping("/topic/{topicId}")
    public List<ResourceResponseDTO> getByTopic(@PathVariable Long topicId) {
        return resourceService.getResourcesByTopic(topicId);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResourceResponseDTO update(@PathVariable Long id, @RequestBody Resource resource) {
        return resourceService.updateResource(id, resource);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            return org.springframework.http.ResponseEntity.ok().build();
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.status(500).body("Delete error: " + e.getMessage());
        }
    }

    // ✅ UPDATE STATUS (Advanced Business Logic)
    @PatchMapping("/{id}/status")
    public ResourceResponseDTO updateStatus(@PathVariable Long id, @RequestParam com.example.resource_service.entity.ResourceStatus status) {
        return resourceService.updateResourceStatus(id, status);
    }
}