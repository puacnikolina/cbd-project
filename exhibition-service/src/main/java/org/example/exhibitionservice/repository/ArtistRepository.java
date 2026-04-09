package org.example.exhibitionservice.repository;

import org.example.exhibitionservice.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
