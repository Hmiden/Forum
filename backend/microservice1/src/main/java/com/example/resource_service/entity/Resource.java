package com.example.resource_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resources")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String url;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    private Long topicId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @Column(columnDefinition = "TEXT")
    private String aiSummary;

    // ===== Advanced Business Logic: Approval workflow =====
    @Enumerated(EnumType.STRING)
    private ResourceStatus status;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ResourceStatus.PENDING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}