package org.example.exhibitionservice.repository;

import org.example.exhibitionservice.model.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
}
