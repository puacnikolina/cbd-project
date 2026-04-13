package org.example.exhibitionservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ExhibitionRequest;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.service.ExhibitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exhibitions")
@AllArgsConstructor
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @GetMapping
    public List<Exhibition> getAllExhibitions() {
        return exhibitionService.getAllExhibitions();
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Exhibition> createExhibition(@Valid @RequestBody ExhibitionRequest exhibitionRequest) {
        Exhibition createdExhibition = exhibitionService.createExhibition(exhibitionRequest);
        return ResponseEntity.ok(createdExhibition);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteExhibition(@PathVariable Integer id) {
        exhibitionService.deleteExhibition(id);
        return ResponseEntity.ok("Exhibition deleted successfully");
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Exhibition> updateExhibition(@PathVariable Integer id,@Valid @RequestBody ExhibitionRequest exhibitionRequest) {
        Exhibition updatedExhibition = exhibitionService.updateExhibition(id,exhibitionRequest);
        return ResponseEntity.ok(updatedExhibition);
    }

    @GetMapping("/active")
    public List<Exhibition> getActive() {
        return exhibitionService.getActiveExhibitions();
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {

        List<Exhibition> result = exhibitionService.searchByTitle(keyword);

        if (result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No exhibitions found for keyword: " + keyword);
        }

        return ResponseEntity.ok(result);
    }


}
