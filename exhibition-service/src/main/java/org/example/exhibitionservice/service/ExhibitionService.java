package org.example.exhibitionservice.service;


import jakarta.transaction.Transactional;
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

    public Exhibition getExhibitionById(Integer id) {
        return exhibitionRepo.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition not found with id: " + id));
    }

    public List<Exhibition> getAllExhibitions() {
        return exhibitionRepo.findAll();
    }

    public Exhibition createExhibition(ExhibitionRequest request) {
        Exhibition exhibition = new Exhibition();
        exhibition.setTitle(request.getTitle());
        exhibition.setDescription(request.getDescription());
        exhibition.setStartDate(request.getStartDate());
        exhibition.setEndDate(request.getEndDate());
        exhibition.setIsActive(isActive(request.getStartDate(), request.getEndDate()));
        exhibition.setReservedTickets(0);
        exhibition.setCapacity(20);

        if (request.getArtistId() != null) {
            Artist artist = artistRepo.findById(request.getArtistId())
                    .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + request.getArtistId()));
            exhibition.setArtistId(artist.getId());
        }



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

    public Exhibition updateExhibition(Integer id, ExhibitionRequest request) {

        Exhibition updatedExhibition = exhibitionRepo.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition not found with id: " + id));

        updatedExhibition.setTitle(request.getTitle());
        updatedExhibition.setDescription(request.getDescription());
        updatedExhibition.setStartDate(request.getStartDate());
        updatedExhibition.setEndDate(request.getEndDate());
        updatedExhibition.setIsActive(isActive(request.getStartDate(), request.getEndDate()));

        if (request.getArtistId() != null) {
            Artist artist = artistRepo.findById(request.getArtistId())
                    .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + request.getArtistId()));
            updatedExhibition.setArtistId(artist.getId());
        } else {
            updatedExhibition.setArtistId(null);
        }

        return exhibitionRepo.save(updatedExhibition);
    }

    public List<Exhibition> getActiveExhibitions() {
        return exhibitionRepo.findByIsActiveTrue();
    }

    public List<Exhibition> searchByTitle(String keyword) {
        return exhibitionRepo.findByTitleContainingIgnoreCase(keyword);
    }


    public boolean isExhibitionAvailable(Integer id) {

        return exhibitionRepo.findById(id)
                .map(exhibition -> {
                    LocalDate today = LocalDate.now();

                    return (exhibition.getStartDate().isEqual(today)
                            || exhibition.getStartDate().isBefore(today))
                            && (exhibition.getEndDate().isEqual(today)
                            || exhibition.getEndDate().isAfter(today));
                })
                .orElse(false);
    }

    @Transactional
    public void reserve(Integer id, int quantity) {
        Exhibition exhibition = exhibitionRepo.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition not found with id: " + id));

        if(exhibition.getReservedTickets() + quantity > exhibition.getCapacity()){
            throw new RuntimeException("Not enough available tickets for this exhibition");
        }

        exhibition.setReservedTickets(exhibition.getReservedTickets() + quantity);
    }

    @Transactional
    public void release(Integer id, int quantity) {
        Exhibition exhibition = exhibitionRepo.findById(id)
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition not found with id: " + id));

        int newReserved = exhibition.getReservedTickets() - quantity;

        if (newReserved < 0) {
            exhibition.setReservedTickets(0);
        } else {
            exhibition.setReservedTickets(newReserved);
        }
    }
}
