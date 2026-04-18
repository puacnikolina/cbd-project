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


    @GetMapping("/{id}/available")
    public boolean isExhibitionAvailable(@PathVariable Integer id) {
        return exhibitionService.isExhibitionAvailable(id);
    }

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
    public ResponseEntity<String> deleteExhibition(@PathVariable Integer id) {
        exhibitionService.deleteExhibition(id);
        return ResponseEntity.ok("Exhibition deleted successfully");
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Exhibition> updateExhibition(@PathVariable Integer id,@Valid @RequestBody ExhibitionRequest request) {
        Exhibition updatedExhibition = exhibitionService.updateExhibition(id, request);
        return ResponseEntity.ok(updatedExhibition);
    }

    @GetMapping("/active")
    public List<Exhibition> getActive() {
        return exhibitionService.getActiveExhibitions();
    }

    //pretraga izlozbe po reci koja se nalazi u naslovu
    @GetMapping("/search")
    public ResponseEntity<List<Exhibition>> search(@RequestParam String keyword) {
        List<Exhibition> result = exhibitionService.searchByTitle(keyword);
        return ResponseEntity.ok(result);
    }


}
