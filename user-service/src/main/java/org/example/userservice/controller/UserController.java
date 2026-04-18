package org.example.userservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.userservice.dto.LoginRequest;
import org.example.userservice.dto.RegisterRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.model.User;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/{id}/exists")
    public boolean userExists(@PathVariable Integer id) {
        return userService.userExists(id);
    }

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

    @PutMapping("/admin/{id}/promote")
    public ResponseEntity<String> promoteUser(@PathVariable Integer id) {
        userService.promoteToAdmin(id);
        return ResponseEntity.ok("User promoted to admin successfully");
    }






}
