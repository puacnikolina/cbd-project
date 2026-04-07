package org.example.userservice.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String username;
    private String email;
    private Integer role;

    public UserResponseDto(Integer id, String username, String email, Integer role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
