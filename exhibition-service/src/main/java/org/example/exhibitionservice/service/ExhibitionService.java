package org.example.exhibitionservice.service;


import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ExhibitionRequest;
import org.example.exhibitionservice.exception.ArtistNotFoundException;
import org.example.exhibitionservice.exception.ExhibitionNotFoundException;
import org.example.exhibitionservice.model.Artist;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.repository.ArtistRepository;
import org.example.exhibitionservice.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepo;
    private final ArtistRepository artistRepo;

    public List<Exhibition> getAllExhibitions() {
        return exhibitionRepo.findAll();
    }

    public Exhibition createExhibition(ExhibitionRequest exhibitionRequest) {
        Exhibition exhibition = new Exhibition();
        exhibition.setTitle(exhibitionRequest.getTitle());
        exhibition.setDescription(exhibitionRequest.getDescription());
        exhibition.setStartDate(exhibitionRequest.getStartDate());
        exhibition.setEndDate(exhibitionRequest.getEndDate());

        if (exhibitionRequest.getArtistId() != null) {
            Artist artist = artistRepo.findById(exhibitionRequest.getArtistId())
                    .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + exhibitionRequest.getArtistId()));
            exhibition.setArtistId(artist.getId());
        }

        exhibition.setIsActive(isActive(exhibitionRequest.getStartDate(), exhibitionRequest.getEndDate()));

        return exhibitionRepo.save(exhibition);
    }

    private boolean isActive(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (startDate == null || endDate == null) {
            return false;
        }
        return (!today.isBefore(startDate) && !today.isAfter(endDate));
    }

    public void deleteExhibition(Integer id) {
        Exhibition exhibition = exhibitionRepo.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition not found with id: " + id));
        exhibitionRepo.delete(exhibition);
    }

    public Exhibition updateExhibition(Integer id, ExhibitionRequest exhibitionRequest) {

        Exhibition updatedExhibition = exhibitionRepo.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition not found with id: " + id));

        updatedExhibition.setTitle(exhibitionRequest.getTitle());
        updatedExhibition.setDescription(exhibitionRequest.getDescription());
        updatedExhibition.setStartDate(exhibitionRequest.getStartDate());
        updatedExhibition.setEndDate(exhibitionRequest.getEndDate());

        if (exhibitionRequest.getArtistId() != null) {
            Artist artist = artistRepo.findById(exhibitionRequest.getArtistId())
                    .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + exhibitionRequest.getArtistId()));
            updatedExhibition.setArtistId(artist.getId());
        } else {
            updatedExhibition.setArtistId(null);
        }

        updatedExhibition.setIsActive(
                isActive(exhibitionRequest.getStartDate(), exhibitionRequest.getEndDate())
        );

        return exhibitionRepo.save(updatedExhibition);
    }

    public List<Exhibition> getActiveExhibitions() {
        return exhibitionRepo.findByIsActiveTrue();
    }

    public List<Exhibition> searchByTitle(String keyword) {
        return exhibitionRepo.findByTitleContainingIgnoreCase(keyword);
    }

}
