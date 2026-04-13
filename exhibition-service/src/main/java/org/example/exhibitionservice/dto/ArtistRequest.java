package org.example.exhibitionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArtistRequest {

    @NotBlank
    @Size(max = 45)
    private String name;
    @NotNull
    @Size(max = 45)
    private Integer birthYear;
    @Size(max = 45)
    private Integer deathYear;
    @NotBlank
    @Size(max = 45)
    private String nationality;

}
