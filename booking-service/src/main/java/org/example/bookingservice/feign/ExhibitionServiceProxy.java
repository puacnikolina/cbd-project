package org.example.bookingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exhibition-service")
public interface ExhibitionServiceProxy {

    @GetMapping("/exhibitions/{id}/available")
    boolean isExhibitionAvailable(@PathVariable Integer id);

    @PostMapping("/exhibitions/{id}/reserve")
    void reserve(@PathVariable Integer id, @RequestParam int quantity);

    @PostMapping("/exhibitions/{id}/release")
    void release(@PathVariable Integer id, @RequestParam int quantity);
}
