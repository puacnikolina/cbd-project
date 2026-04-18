package org.example.bookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "booking_id", nullable = false)
    private Integer bookingId;

    @NotNull
    @Column(name = "exhibition_id", nullable = false)
    private Integer exhibitionId;

    @NotNull
    @Column(name = "ticket_category_id", nullable = false)
    private Integer ticketCategoryId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "price_per_ticket", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerTicket;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reservation_date")
    private Instant reservationDate;


}