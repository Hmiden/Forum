package com.Forum.Forum.controller;

import com.Forum.Forum.Services.IServiceTopic;
import com.Forum.Forum.entity.Category;
import com.Forum.Forum.entity.Topic;
import com.Forum.Forum.entity.TopicDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.Forum.Forum.security.JwtFilter;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = TopicController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceTopic serviceTopic;

    @MockBean
    private JwtFilter jwtFilter;

    private Topic topic;
    private TopicDTO topicDTO;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1L);
        category.setName("General");

        topic = new Topic();
        topic.setId(10L);
        topic.setTitle("Test Topic");
        topic.setContent("Test Content");
        topic.setCategory(category);

        topicDTO = new TopicDTO();
        topicDTO.setId(10L);
        topicDTO.setTitle("Test Topic");
        topicDTO.setContent("Test Content");
        topicDTO.setCategoryId(1L);
    }

    // ===== CREATE =====
    @Test
    void testCreate() throws Exception {
        when(serviceTopic.create(any(Topic.class), eq(1L))).thenReturn(topicDTO);

        String topicJson = """
                {"title":"Test Topic","content":"Test Content"}
                """;

        mockMvc.perform(post("/topic/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(topicJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Topic"));
    }

    // ===== GET ALL =====
    @Test
    void testGetAll() throws Exception {
        when(serviceTopic.getAll()).thenReturn(Arrays.asList(topic));

        mockMvc.perform(get("/topic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Topic"));
    }

    // ===== GET BY ID =====
    @Test
    void testGetById() throws Exception {
        when(serviceTopic.getById(10L)).thenReturn(topic);

        mockMvc.perform(get("/topic/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Topic"));
    }

    // ===== GET BY CATEGORY =====
    @Test
    void testGetByCategory() throws Exception {
        when(serviceTopic.getByCategory(1L)).thenReturn(Arrays.asList(topic));

        mockMvc.perform(get("/topic/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Topic"));
    }

    // ===== UPDATE =====
    @Test
    void testUpdate() throws Exception {
        Topic updated = new Topic();
        updated.setTitle("Updated Title");
        updated.setContent("Updated Content");

        when(serviceTopic.update(eq(10L), any(Topic.class))).thenReturn(updated);

        String updateJson = """
                {"title":"Updated Title","content":"Updated Content"}
                """;

        mockMvc.perform(put("/topic/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    // ===== DELETE =====
    @Test
    void testDelete() throws Exception {
        doNothing().when(serviceTopic).delete(10L);

        mockMvc.perform(delete("/topic/10"))
                .andExpect(status().isOk());
    }

    // ===== GET ALL DTO =====
    @Test
    void testGetAllDTO() throws Exception {
        when(serviceTopic.getAllDTO()).thenReturn(Arrays.asList(topicDTO));

        mockMvc.perform(get("/topic/dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Topic"));
    }
}
