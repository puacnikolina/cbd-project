package org.example.bookingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exhibition-service")
public interface ExhibitionServiceProxy {

    @GetMapping("/exhibitions/{id}/available")
    boolean isExhibitionAvailable(@PathVariable Integer id);

}
