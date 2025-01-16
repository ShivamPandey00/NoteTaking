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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

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
