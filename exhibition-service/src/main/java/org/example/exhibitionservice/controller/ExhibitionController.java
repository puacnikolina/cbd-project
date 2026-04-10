package org.example.exhibitionservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.exhibitionservice.dto.ExhibitionRequest;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.scheduler.ExhibitionScheduler;
import org.example.exhibitionservice.service.ExhibitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exhibitions")
@AllArgsConstructor
public class ExhibitionController {

    private final ExhibitionService exhibitionService;
    private final ExhibitionScheduler exhibitionScheduler;

    //za testiranje - ne updateuje ali vraca OK
    @GetMapping("/test/scheduler")
    public void runSchedulerManually() {
        exhibitionScheduler.updateStatus();
    }

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

    @PutMapping("/admin/update")
    public ResponseEntity<Exhibition> updateExhibition(@Valid @RequestBody Exhibition exhibition){
        Exhibition updatedExhibition = exhibitionService.updateExhibition(exhibition);
        return ResponseEntity.ok(updatedExhibition);
    }




}
