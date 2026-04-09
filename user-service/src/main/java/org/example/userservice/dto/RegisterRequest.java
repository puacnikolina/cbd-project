package org.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(max = 45)
    @NotBlank
    private String username;
    @Size(max = 45)
    @NotBlank
    private String email;
    @Size(max = 255)
    @NotBlank
    private String password;

}
