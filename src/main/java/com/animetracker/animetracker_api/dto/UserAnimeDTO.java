package com.animetracker.animetracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnimeDTO {
    private Long userId;
    private Long animeId;
    private String watchStatus;
    private Integer rating;
    private Integer epActuel;
    private Boolean isFavorite;
    private String review;
}
