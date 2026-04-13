package org.example.exhibitionservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ExhibitionRequest {
    @NotBlank
    @Size(max = 255)
    private String title;
    @NotBlank
    @Size(max = 1000)
    private String description;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Integer artistId;
}