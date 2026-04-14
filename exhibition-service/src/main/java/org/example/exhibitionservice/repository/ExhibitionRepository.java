package org.example.exhibitionservice.repository;

import org.example.exhibitionservice.model.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
    List<Exhibition> findByIsActiveTrue();
    List<Exhibition> findByTitleContainingIgnoreCase(String keyword);

    void deleteByArtistId(Integer id);

    List<Exhibition> findByArtistId(Integer id);
}
