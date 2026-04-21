package org.example.bookingservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.bookingservice.dto.CreateBookingRequest;
import org.example.bookingservice.model.Booking;
import org.example.bookingservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@Min(1) @PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }


    @DeleteMapping("/cancel/{bookingId}/user/{userId}")
    public ResponseEntity<String> cancelBooking(@Min(1) @PathVariable Integer bookingId, @Min(1) @PathVariable Integer userId) {
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }

}
