package com.example.resource_service.entity.Dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicDTO {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime createdAt;
    private int replyCount;

}
