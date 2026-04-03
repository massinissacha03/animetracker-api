package com.animetracker.animetracker_api.controller;

import com.animetracker.animetracker_api.dto.*;
import com.animetracker.animetracker_api.entity.Anime;
import com.animetracker.animetracker_api.entity.User;
import com.animetracker.animetracker_api.entity.UserAnime;
import com.animetracker.animetracker_api.repository.AnimeRepository;
import com.animetracker.animetracker_api.repository.UserRepository;
import com.animetracker.animetracker_api.repository.UserAnimeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-animes")
public class UserAnimeController {

    @Autowired
    private UserAnimeRepository userAnimeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimeRepository animeRepository;

    @GetMapping
    public List<UserAnime> getAllUserAnimes() {
        return userAnimeRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<UserAnime> getUserAnimesByUser(@PathVariable Long userId) {
        return userAnimeRepository.findByUserId(userId);
    }

    @PostMapping
    public UserAnime createUserAnime(@RequestBody @Valid UserAnimeDTO dto) {
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        Anime anime = animeRepository.findById(dto.getAnimeId())
            .orElseThrow(() -> new RuntimeException("Anime not found with id: " + dto.getAnimeId()));

        UserAnime userAnime = new UserAnime();
        userAnime.setUser(user);
        userAnime.setAnime(anime);
        userAnime.setWatchStatus(dto.getWatchStatus());
        userAnime.setRating(dto.getRating());
        userAnime.setEpActuel(dto.getEpActuel());
        userAnime.setIsFavorite(dto.getIsFavorite());
        userAnime.setReview(dto.getReview());
        return userAnimeRepository.save(userAnime);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAnime> updateUserAnime(@PathVariable Long id, @RequestBody @Valid UserAnimeDTO dto) {
        return userAnimeRepository.findById(id)
            .map(userAnime -> {
                userAnime.setWatchStatus(dto.getWatchStatus());
                userAnime.setRating(dto.getRating());
                userAnime.setEpActuel(dto.getEpActuel());
                userAnime.setIsFavorite(dto.getIsFavorite());
                userAnime.setReview(dto.getReview());
                return ResponseEntity.ok(userAnimeRepository.save(userAnime));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAnime(@PathVariable Long id) {
        UserAnime userAnime = userAnimeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserAnime not found with id: " + id));
        userAnimeRepository.delete(userAnime);
    }
}
