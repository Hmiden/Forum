package com.example.resource_service.services.impl;

import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.entity.Dto.TopicDTO;
import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.ResourceType;
import com.example.resource_service.entity.ResourceStatus;
import com.example.resource_service.feign.ForumClient;
import com.example.resource_service.repository.ResourceRepository;
import com.example.resource_service.services.GeminiService;
import com.example.resource_service.services.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceImplTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ForumClient forumClient;

    @Mock
    private GeminiService geminiService;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    private Resource resource;
    private TopicDTO topicDTO;

    @BeforeEach
    void setUp() {
        resource = new Resource();
        resource.setId(1L);
        resource.setTitle("Test Resource");
        resource.setDescription("Test Description");
        resource.setUrl("http://test.com");
        resource.setType(ResourceType.VIDEO);
        resource.setTopicId(100L);
        resource.setStatus(ResourceStatus.APPROVED); // Default to approved for listing tests

        topicDTO = new TopicDTO();
        topicDTO.setId(100L);
        topicDTO.setTitle("Test Topic");
    }

    @Test
    void testAddResource() {
        when(geminiService.summarize(anyString())).thenReturn("AI Summary");
        when(forumClient.getTopicById(100L)).thenReturn(topicDTO);
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        ResourceResponseDTO result = resourceService.addResource(resource);

        assertNotNull(result);
        assertEquals("Test Resource", result.getTitle());
        assertEquals("Test Topic", result.getTopicName());
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }

    @Test
    void testGetAllResources_All() {
        Resource pendingResource = new Resource();
        pendingResource.setStatus(ResourceStatus.PENDING);
        
        when(forumClient.getAllTopics()).thenReturn(Arrays.asList(topicDTO));
        when(resourceRepository.findAll()).thenReturn(Arrays.asList(resource, pendingResource));

        List<ResourceResponseDTO> result = resourceService.getAllResources();

        assertNotNull(result);
        assertEquals(2, result.size()); // Now returns everything
    }

    @Test
    void testGetResourcesByTopic_OnlyApproved() {
        when(forumClient.getTopicById(100L)).thenReturn(topicDTO);
        when(resourceRepository.findByTopicId(100L)).thenReturn(Arrays.asList(resource));

        List<ResourceResponseDTO> result = resourceService.getResourcesByTopic(100L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Topic", result.get(0).getTopicName());
    }

    @Test
    void testUpdateResource() {
        Resource updatedInfo = new Resource();
        updatedInfo.setTitle("Updated Title");
        updatedInfo.setDescription("Updated Desc");
        updatedInfo.setUrl("http://updated.com");
        updatedInfo.setType(ResourceType.PDF);

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);
        when(forumClient.getTopicById(100L)).thenReturn(topicDTO);

        ResourceResponseDTO result = resourceService.updateResource(1L, updatedInfo);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }

    @Test
    void testUpdateResourceStatus() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);
        when(forumClient.getTopicById(100L)).thenReturn(topicDTO);

        ResourceResponseDTO result = resourceService.updateResourceStatus(1L, ResourceStatus.REJECTED);

        assertNotNull(result);
        assertEquals(ResourceStatus.REJECTED, resource.getStatus());
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }

    @Test
    void testDeleteResource() {
        doNothing().when(resourceRepository).deleteById(1L);

        resourceService.deleteResource(1L);

        verify(resourceRepository, times(1)).deleteById(1L);
    }
}
