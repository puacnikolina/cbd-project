package org.example.userservice.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.example.userservice.dto.RegisterRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.repository.RoleRepository;
import org.example.userservice.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRole()))
                .toList();
    }

    public UserResponse registerUser(RegisterRequest registerRequest){
        //username check
        if(userRepo.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email already exists: " + registerRequest.getEmail());
        }

        //user for database
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());

        Role userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("Role 'USER' not found"));
        int roleId = userRole.getId();
        user.setRole(roleId);


        User savedUser = userRepo.save(user);

        //response for controller
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public UserResponse loginUser(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials for email: " + email);
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
