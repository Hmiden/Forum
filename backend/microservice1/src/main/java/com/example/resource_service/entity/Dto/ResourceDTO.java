package com.example.resource_service.entity.Dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

    private Long id;
    private String title;
    private String description;
    private String url;
    private String type;
    private Long topicId;
    private String topicName; // 🔥 IMPORTANT

}