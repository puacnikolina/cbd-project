package org.example.exhibitionservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ArtistRequest;
import org.example.exhibitionservice.model.Artist;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@AllArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody ArtistRequest request) {
        return artistService.createArtist(request);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Integer id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok("Artist deleted successfully");
    }

    @GetMapping("/{id}/exhibitions")
    public ResponseEntity<List<Exhibition>> getExhibitionsByArtist(@PathVariable Integer id) {
        List<Exhibition> exhibitions = artistService.getExhibitionsByArtist(id);
        return ResponseEntity.ok(exhibitions);
    }

}
