package org.example.userservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private Integer role;
    //dodati role name umesto role id

    public UserResponse(Integer id, String username, String email, Integer role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
