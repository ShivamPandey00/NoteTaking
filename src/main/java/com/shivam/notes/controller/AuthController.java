package com.shivam.notes.controller;

import com.shivam.notes.service.JwtService;
import com.shivam.notes.models.UserNotes;
import com.shivam.notes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/notes/auth")
@Slf4j
public class AuthController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserNotes userNotes) {
        log.info("Entered Signup");

        if (userRepository.findByUsername(userNotes.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        userNotes.setPassword(passwordEncoder.encode(userNotes.getPassword()));
        userRepository.save(userNotes);
        String message = "User registered successfully!" + userNotes.getUsername();
        return ResponseEntity.ok(message);
    }

    @GetMapping("/userdetails")
    public ResponseEntity<?> getUserDetails() {
        // Get the authenticated user's username from SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch user details from the database
        Optional<UserNotes> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserNotes loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {
            HashMap<String, String> response = new HashMap<>();
            response.put("Login successful", jwtService.generateToken(loginRequest.getUsername()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }



}
