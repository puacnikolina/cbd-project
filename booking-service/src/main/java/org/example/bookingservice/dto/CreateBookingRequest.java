package org.example.bookingservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateBookingRequest {

    private Integer userId;
    private Integer exhibitionId;
    private List<TicketRequest> tickets;

}
