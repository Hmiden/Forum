package com.example.resource_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resource_likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long resourceId;

    private Long userId;
}