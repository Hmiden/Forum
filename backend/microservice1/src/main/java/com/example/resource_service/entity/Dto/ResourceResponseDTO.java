package com.example.resource_service.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String url;
    private String type;
    private Long topicId;
    private String topicName;
    private String aiSummary;
    private String status;
}