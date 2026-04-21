package org.example.exhibitionservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ExhibitionRequest;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.service.ExhibitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exhibitions")
@AllArgsConstructor
@Validated
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @GetMapping
    public List<Exhibition> getAllExhibitions() {
        return exhibitionService.getAllExhibitions();
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Exhibition> createExhibition(@Valid @RequestBody ExhibitionRequest request) {
        Exhibition createdExhibition = exhibitionService.createExhibition(request);
        return ResponseEntity.ok(createdExhibition);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteExhibition(@Min(1) @PathVariable Integer id) {
        exhibitionService.deleteExhibition(id);
        return ResponseEntity.ok("Exhibition deleted successfully");
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Exhibition> updateExhibition(@Min(1) @PathVariable Integer id,@Valid @RequestBody ExhibitionRequest request) {
        Exhibition updatedExhibition = exhibitionService.updateExhibition(id, request);
        return ResponseEntity.ok(updatedExhibition);
    }

    @GetMapping("/active")
    public List<Exhibition> getActive() {
        return exhibitionService.getActiveExhibitions();
    }


    @GetMapping("/search")
    public ResponseEntity<List<Exhibition>> search(@RequestParam String keyword) {
        List<Exhibition> result = exhibitionService.searchByTitle(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/available")
    public boolean isExhibitionAvailable(@Min(1) @PathVariable Integer id) {
        return exhibitionService.isExhibitionAvailable(id);
    }

    @PostMapping("/{id}/reserve")
    public void reserve(@Min(1) @PathVariable Integer id, @RequestParam int quantity){
        exhibitionService.reserve(id,quantity);
    }

    @PostMapping("/{id}/release")
    public void release(@Min(1) @PathVariable Integer id, @RequestParam int quantity){
        exhibitionService.release(id,quantity);
    }


}
