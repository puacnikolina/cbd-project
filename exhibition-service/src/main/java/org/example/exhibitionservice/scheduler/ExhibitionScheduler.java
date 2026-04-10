package org.example.exhibitionservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.exhibitionservice.model.Exhibition;
import org.example.exhibitionservice.repository.ExhibitionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExhibitionScheduler {

    private final ExhibitionRepository exhibitionRepo;
    //@Scheduled(cron = "0 * * * * *") // svake minute
    @Scheduled(cron = "0 0 0 * * *") //izmeniti za potrebe tesitranja
    public void updateStatus() {

        List<Exhibition> all = exhibitionRepo.findAll();
        LocalDate today = LocalDate.now();

        for (Exhibition e : all) {
            boolean active =
                            e.getStartDate() != null &&
                            e.getEndDate() != null &&
                            !today.isBefore(e.getStartDate()) &&
                            !today.isAfter(e.getEndDate());

            e.setIsActive(active);
        }

        exhibitionRepo.saveAll(all);
    }
}