package org.example.exhibitionservice.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ExhibitionRequest;
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
                    .orElseThrow(() -> new RuntimeException("Artist not found"));
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
                .orElseThrow(() -> new RuntimeException("Exhibition not found"));
        exhibitionRepo.delete(exhibition);
    }

    public Exhibition updateExhibition(@Valid Exhibition exhibition) {
        Exhibition updatedExhibition = exhibitionRepo.findById(exhibition.getId())
                .orElseThrow(() -> new RuntimeException("Exhibition not found"));

        updatedExhibition.setTitle(exhibition.getTitle());
        updatedExhibition.setDescription(exhibition.getDescription());
        updatedExhibition.setStartDate(exhibition.getStartDate());
        updatedExhibition.setEndDate(exhibition.getEndDate());

        if (exhibition.getArtistId() != null) {
            Artist artist = artistRepo.findById(exhibition.getArtistId())
                    .orElseThrow(() -> new RuntimeException("Artist not found"));
            updatedExhibition.setArtistId(artist.getId());
        }

        updatedExhibition.setIsActive(isActive(exhibition.getStartDate(), exhibition.getEndDate()));

        return exhibitionRepo.save(updatedExhibition);
    }
}
