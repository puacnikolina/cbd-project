package org.example.exhibitionservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ArtistRequest;
import org.example.exhibitionservice.exception.ArtistNotFoundException;
import org.example.exhibitionservice.model.Artist;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.repository.ArtistRepository;
import org.example.exhibitionservice.repository.ExhibitionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepo;
    private final ExhibitionRepository exhibitionRepo;

    public List<Artist> getAllArtists() {
        return artistRepo.findAll();
    }

    public ResponseEntity<Artist> createArtist(ArtistRequest request) {
        Artist artist = new Artist();

        artist.setName(request.getName());
        artist.setBirthYear(request.getBirthYear());
        artist.setDeathYear(request.getDeathYear());
        artist.setNationality(request.getNationality());

        Artist savedArtist = artistRepo.save(artist);
        return ResponseEntity.ok(savedArtist);
    }

    @Transactional
    public void deleteArtist(Integer id) {
        Artist artist = artistRepo.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));
        exhibitionRepo.deleteByArtistId(artist.getId());
        artistRepo.delete(artist);
    }

    public List<Exhibition> getExhibitionsByArtist(Integer id) {
        return exhibitionRepo.findByArtistId(id);
    }
}
