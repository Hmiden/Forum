package com.example.resource_service.controller;

import com.example.resource_service.entity.Resource;
import com.example.resource_service.entity.Dto.ResourceResponseDTO;
import com.example.resource_service.services.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResourceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceService resourceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddResource() throws Exception {
        Resource resource = new Resource();
        resource.setTitle("New Resource");
        
        ResourceResponseDTO response = new ResourceResponseDTO();
        response.setTitle("New Resource");

        when(resourceService.addResource(any(Resource.class))).thenReturn(response);

        mockMvc.perform(post("/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Resource"));
    }

    @Test
    void testGetAll() throws Exception {
        ResourceResponseDTO r1 = new ResourceResponseDTO();
        r1.setTitle("R1");
        
        when(resourceService.getAllResources()).thenReturn(Arrays.asList(r1));

        mockMvc.perform(get("/resources"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("R1"));
    }

    @Test
    void testGetByTopic() throws Exception {
        ResourceResponseDTO r1 = new ResourceResponseDTO();
        r1.setTopicId(1L);
        
        when(resourceService.getResourcesByTopic(1L)).thenReturn(Arrays.asList(r1));

        mockMvc.perform(get("/resources/topic/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].topicId").value(1));
    }

    @Test
    void testUpdate() throws Exception {
        Resource resource = new Resource();
        resource.setTitle("Updated");
        
        ResourceResponseDTO response = new ResourceResponseDTO();
        response.setTitle("Updated");

        when(resourceService.updateResource(eq(1L), any(Resource.class))).thenReturn(response);

        mockMvc.perform(put("/resources/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/resources/1"))
                .andExpect(status().isOk());
    }
}
