package com.Forum.Forum.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badwords")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Badword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String word;
}
