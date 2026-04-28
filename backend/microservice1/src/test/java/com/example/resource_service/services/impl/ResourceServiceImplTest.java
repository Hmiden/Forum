package com.example.resource_service.services.impl;

import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.entity.Dto.TopicDTO;
import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.ResourceType;
import com.example.resource_service.feign.ForumClient;
import com.example.resource_service.repository.ResourceRepository;
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
        resource.setType(ResourceType.valueOf("VIDEO")); // Adjust if your Enum values are different
        resource.setTopicId(100L);

        topicDTO = new TopicDTO();
        topicDTO.setId(100L);
        topicDTO.setTitle("Test Topic");
    }

    @Test
    void testAddResource() {
        when(forumClient.getTopicById(100L)).thenReturn(topicDTO);
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        ResourceResponseDTO result = resourceService.addResource(resource);

        assertNotNull(result);
        assertEquals("Test Resource", result.getTitle());
        assertEquals("Test Topic", result.getTopicName());
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }

    @Test
    void testGetAllResources() {
        when(forumClient.getAllTopics()).thenReturn(Arrays.asList(topicDTO));
        when(resourceRepository.findAll()).thenReturn(Arrays.asList(resource));

        List<ResourceResponseDTO> result = resourceService.getAllResources();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Topic", result.get(0).getTopicName());
    }

    @Test
    void testGetResourcesByTopic() {
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
        updatedInfo.setType(ResourceType.valueOf("PDF"));

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);
        when(forumClient.getTopicById(100L)).thenReturn(topicDTO);

        ResourceResponseDTO result = resourceService.updateResource(1L, updatedInfo);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }

    @Test
    void testDeleteResource() {
        doNothing().when(resourceRepository).deleteById(1L);

        resourceService.deleteResource(1L);

        verify(resourceRepository, times(1)).deleteById(1L);
    }
}
