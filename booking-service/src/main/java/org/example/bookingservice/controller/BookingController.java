package org.example.bookingservice.controller;

import lombok.AllArgsConstructor;
import org.example.bookingservice.feign.ExhibitionServiceProxy;
import org.example.bookingservice.feign.UserServiceProxy;
import org.example.bookingservice.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserServiceProxy userService;
    private final ExhibitionServiceProxy exhibitionService;

    @GetMapping("/user/{id}")
    public String testUser(@PathVariable Integer id) {

        boolean exists = userService.userExists(id);

        return "User exists: " + exists;
    }

    @GetMapping("/exhibition/{id}")
    public String testExhibition(@PathVariable Integer id) {
        boolean available = exhibitionService.isExhibitionAvailable(id);

        return "Exhibition available: " + available;
    }

}
