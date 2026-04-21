package org.example.bookingservice.service;

import lombok.AllArgsConstructor;
import org.example.bookingservice.dto.CreateBookingRequest;
import org.example.bookingservice.dto.TicketRequest;
import org.example.bookingservice.exception.BookingServiceException;
import org.example.bookingservice.feign.ExhibitionServiceProxy;
import org.example.bookingservice.feign.UserServiceProxy;
import org.example.bookingservice.model.Booking;
import org.example.bookingservice.model.Ticket;
import org.example.bookingservice.model.TicketCategory;
import org.example.bookingservice.repository.BookingRepository;
import org.example.bookingservice.repository.TicketCategoryRepository;
import org.example.bookingservice.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final UserServiceProxy userService;
    private final ExhibitionServiceProxy exhibitionService;
    private final BookingRepository bookingRepo;
    private final TicketRepository ticketRepo;
    private final TicketCategoryRepository categoryRepo;

    public Booking createBooking(CreateBookingRequest req) {
        if (!userService.userExists(req.getUserId())) {
            throw new BookingServiceException("User not found", HttpStatus.NOT_FOUND);
        }

        if (!exhibitionService.isExhibitionAvailable(req.getExhibitionId())) {
            throw new BookingServiceException("Exhibition not available", HttpStatus.BAD_REQUEST);
        }

        if (req.getTickets() == null || req.getTickets().isEmpty()) {
            throw new BookingServiceException("Tickets cannot be empty", HttpStatus.BAD_REQUEST);
        }

        int totalQuantity = req.getTickets()
                .stream()
                .mapToInt(TicketRequest::getQuantity)
                .sum();

        exhibitionService.reserve(req.getExhibitionId(), totalQuantity);


        // 3. CREATE BOOKING (initial save)
        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setStatus("CONFIRMED");
        booking.setTotalPrice(0);
        booking.setBookingDate(Instant.now());

        Booking savedBooking = bookingRepo.save(booking);

        // 4. PROCESS TICKETS
        Integer total = 0;
        for (TicketRequest t : req.getTickets()){
            TicketCategory category = categoryRepo.findById(t.getTicketCategoryId())
                    .orElseThrow(() -> new BookingServiceException("Ticket category not found", HttpStatus.NOT_FOUND));

            Integer price = category.getPrice().intValue() * t.getQuantity();
            total += price;

            Ticket ticket = new Ticket();
            ticket.setBookingId(savedBooking.getId());
            ticket.setTicketCategoryId(category.getId());
            ticket.setExhibitionId(req.getExhibitionId());
            ticket.setQuantity(t.getQuantity());
            ticket.setPricePerTicket(category.getPrice());
            ticket.setReservationDate(Instant.now());

            ticketRepo.save(ticket);
        }


        // 5. UPDATE TOTAL PRICE
        savedBooking.setTotalPrice(total);

        return bookingRepo.save(savedBooking);

    }

    public List<Booking> getBookingsByUser(Integer userId) {
        if (!userService.userExists(userId)) {
            throw new BookingServiceException("User not found", HttpStatus.NOT_FOUND);
        }
        return bookingRepo.findByUserId(userId);
    }

    public void cancelBooking(Integer bookingId, Integer userId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingServiceException("Booking not found", HttpStatus.NOT_FOUND));

        if (!booking.getUserId().equals(userId)) {
            throw new BookingServiceException("You cannot cancel this booking",HttpStatus.FORBIDDEN);
        }

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new BookingServiceException("Booking already cancelled", HttpStatus.CONFLICT);
        }

        List<Ticket> tickets = ticketRepo.findByBookingId(bookingId);
        if (tickets.isEmpty()) {
            throw new BookingServiceException("No tickets found for booking", HttpStatus.NOT_FOUND);
        }
        int totalQuantity = tickets.stream()
                .mapToInt(Ticket::getQuantity)
                .sum();

        Integer exhibitionId = tickets.get(0).getExhibitionId(); //svi imaju isti id pa uzmemo id iz prvog ticketa
        exhibitionService.release(exhibitionId, totalQuantity);

        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);

    }
}
