package com.Forum.Forum.Services;

import com.Forum.Forum.Repository.ICategoryRepository;
import com.Forum.Forum.Repository.ITopicRepository;
import com.Forum.Forum.entity.Category;
import com.Forum.Forum.entity.Topic;
import com.Forum.Forum.entity.TopicDTO;
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
public class ImplServiceTopicTest {

    @Mock
    private ITopicRepository repository;

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private ImplServiceTopic service;

    private Topic topic;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("General");

        topic = new Topic();
        topic.setId(10L);
        topic.setTitle("Hello");
        topic.setContent("World");
        topic.setCategory(category);
    }

    @Test
    void testCreate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(repository.save(any(Topic.class))).thenReturn(topic);

        TopicDTO result = service.create(topic, 1L);

        assertNotNull(result);
        assertEquals("Hello", result.getTitle());
        verify(repository).save(any(Topic.class));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(topic));
        List<Topic> result = service.getAll();
        assertEquals(1, result.size());
    }

    @Test
    void testGetById() {
        when(repository.findById(10L)).thenReturn(Optional.of(topic));
        Topic result = service.getById(10L);
        assertEquals("Hello", result.getTitle());
    }

    @Test
    void testUpdate() {
        Topic updateInfo = new Topic();
        updateInfo.setTitle("Updated");
        updateInfo.setContent("Content");

        when(repository.findById(10L)).thenReturn(Optional.of(topic));
        when(repository.save(any(Topic.class))).thenReturn(topic);

        Topic result = service.update(10L, updateInfo);

        assertNotNull(result);
        assertEquals("Updated", result.getTitle());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(10L);
        service.delete(10L);
        verify(repository).deleteById(10L);
    }
}
