package org.example.bookingservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateBookingRequest {

    @NotNull
    private Integer userId;
    @NotNull
    private Integer exhibitionId;
    @NotEmpty
    private List<@Valid TicketRequest> tickets;

}
