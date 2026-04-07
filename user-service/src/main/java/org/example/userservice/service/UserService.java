package org.example.userservice.service;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.RegisterRequestDto;
import org.example.userservice.dto.UserResponseDto;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRole()))
                .toList();
    }

    public UserResponseDto registerUser(RegisterRequestDto registerRequestDto){
        //username check
        if(userRepo.existsByEmail(registerRequestDto.getEmail())){
            throw new IllegalArgumentException("Username already exists: " + registerRequestDto.getUsername());
        }

        //user for database
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setEmail(registerRequestDto.getEmail());
        user.setRole(2); //default role is user, which has id 2 in the database

        User savedUser = userRepo.save(user);

        //response for controller
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

}
