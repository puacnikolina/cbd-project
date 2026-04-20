package org.example.bookingservice.repository;

import org.example.bookingservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByBookingId(Integer bookingId);
}
