package org.example.bookingservice.dto;

import lombok.Data;

@Data
public class TicketRequest {

    private Integer ticketCategoryId;
    private Integer quantity;

}
