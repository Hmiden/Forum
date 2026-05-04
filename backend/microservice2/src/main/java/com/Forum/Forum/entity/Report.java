package com.Forum.Forum.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The reply being reported
    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @Column(nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Optional: store the user who reported it (if you have the user context)
    private String reportedBy; 
}
