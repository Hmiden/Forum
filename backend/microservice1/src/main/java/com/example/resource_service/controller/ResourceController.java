package com.example.resource_service.controller;

import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@CrossOrigin(origins = "http://localhost:4200")
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
    public void delete(@PathVariable Long id) {
        resourceService.deleteResource(id);
    }

}