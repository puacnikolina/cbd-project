package org.example.exhibitionservice.repository;

import org.example.exhibitionservice.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findByNameIgnoreCase(String name);
}
