package org.example.bookingservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequest {

    @NotNull
    private Integer ticketCategoryId;
    @NotNull
    @Min(value = 1)
    private Integer quantity;

}
