package org.example.userservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.userservice.dto.LoginRequest;
import org.example.userservice.dto.RegisterRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    public final UserService userService; //constructor injection

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
       return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }





}
