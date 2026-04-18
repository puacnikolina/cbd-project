package org.example.bookingservice.repository;

import org.example.bookingservice.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Integer> {
}
