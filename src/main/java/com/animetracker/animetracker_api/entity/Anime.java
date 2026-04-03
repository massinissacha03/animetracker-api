package com.animetracker.animetracker_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "anime")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    private Integer episodes;
    
    @Column(name = "annee_sortie")
    private Integer anneeSortie;
    
    @Column(length = 20)
    private String season;
    
    @Column(length = 100)
    private String genre;
    
    @Column(length = 100)
    private String studio;
    
    @Column(length = 20)
    private String status = "FINISHED";
    
    @Column(name = "age_min")
    private Integer ageMin;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "mal_score", precision = 3, scale = 2)
    private BigDecimal malScore;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}