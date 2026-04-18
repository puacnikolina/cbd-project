package org.example.bookingservice.controller;

import lombok.AllArgsConstructor;
import org.example.bookingservice.dto.CreateBookingRequest;
import org.example.bookingservice.model.Booking;
import org.example.bookingservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @DeleteMapping("/{bookingId}/user/{userId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }

}
