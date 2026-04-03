package com.animetracker.animetracker_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeDTO {
    @NotBlank(message = "Le titre est obligatoire")
    @Pattern(regexp = ".*\\S.*", message = "Le titre ne peut pas contenir uniquement des espaces")
    @Size(max = 200)
    private String title;
    private String description;
    @Min(1)
    private Integer episodes;
    @Min(value = 1800, message = "L'année de sortie doit être au moins 1800")
    @Max(value = 2100, message = "L'année de sortie ne peut pas dépasser 2100")
    private Integer anneeSortie;
    private String genre;
    private String studio;
    private String status;
    @Min(value = 0, message = "L'âge minimum doit être au moins 0")
    @Max(value = 18, message = "L'âge minimum ne peut pas dépasser 18")
    private Integer ageMin;
}
