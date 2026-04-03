package com.animetracker.animetracker_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_anime", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "anime_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;
    
    @Column(name = "watch_status", length = 20)
    private String watchStatus = "WISHLIST"; 
    @Column(nullable = true)
    private Integer rating;
    
    @Column(name = "ep_actuel")  
    private Integer epActuel = 0;
    
    @Column(name = "is_favorite")
    private Boolean isFavorite = false;
    
    @Column(columnDefinition = "TEXT")
    private String review;
    
   
}