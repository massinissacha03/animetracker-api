package com.animetracker.animetracker_api.controller;

import com.animetracker.animetracker_api.dto.*;
import com.animetracker.animetracker_api.entity.Anime;
import com.animetracker.animetracker_api.repository.AnimeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
public class AnimeController {

    @Autowired
    private AnimeRepository animeRepository;

    @GetMapping
    public List<Anime> getAllAnimes() {
        return animeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        return animeRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Anime> searchAnimes(@RequestParam String title) {
        return animeRepository.findByTitleContainingIgnoreCase(title);
    }

    @PostMapping
    public Anime createAnime(@RequestBody @Valid AnimeDTO dto) {
        Anime anime = new Anime();
        anime.setTitle(dto.getTitle());
        anime.setDescription(dto.getDescription());
        anime.setEpisodes(dto.getEpisodes());
        anime.setAnneeSortie(dto.getAnneeSortie());
        anime.setGenre(dto.getGenre());
        anime.setStudio(dto.getStudio());
        anime.setStatus(dto.getStatus());
        anime.setAgeMin(dto.getAgeMin());
        return animeRepository.save(anime);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody @Valid AnimeDTO dto) {
        return animeRepository.findById(id)
            .map(anime -> {
                anime.setTitle(dto.getTitle());
                anime.setDescription(dto.getDescription());
                anime.setEpisodes(dto.getEpisodes());
                anime.setAnneeSortie(dto.getAnneeSortie());
                anime.setGenre(dto.getGenre());
                anime.setStudio(dto.getStudio());
                anime.setStatus(dto.getStatus());
                anime.setAgeMin(dto.getAgeMin());
                return ResponseEntity.ok(animeRepository.save(anime));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnime(@PathVariable Long id) {
        Anime anime = animeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Anime not found with id: " + id));
        animeRepository.delete(anime);
    }
}
