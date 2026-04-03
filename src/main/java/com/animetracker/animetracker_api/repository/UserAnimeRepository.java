package com.animetracker.animetracker_api.repository;

import com.animetracker.animetracker_api.entity.UserAnime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnimeRepository extends JpaRepository<UserAnime, Long> {
    List<UserAnime> findByUserId(Long userId);
    List<UserAnime> findByAnimeId(Long animeId);
    Optional<UserAnime> findByUserIdAndAnimeId(Long userId, Long animeId);
}
