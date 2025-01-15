package com.shivam.notes.controller;

import com.shivam.notes.models.UserNotes;
import com.shivam.notes.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping("/notes/auth")
@Slf4j
public class AuthController {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate secret key

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        //return "User registered successfully!";
        String message = "User registered successfully!" + userNotes.getUsername();
        return ResponseEntity.ok(message);
    }
//    @GetMapping("/get/user/{username}")
//    public ResponseEntity<?> getUser(@PathVariable String user) {
//        log.info("Entered Signup");
//
//        if (userRepository.findByUsername(userNotes.getUsername()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
//        }
//
//        userNotes.setPassword(passwordEncoder.encode(userNotes.getPassword()));
//        userRepository.save(userNotes);
//        //return "User registered successfully!";
//        String message = "User registered successfully!" + userNotes.getUsername();
//        return ResponseEntity.ok(message);
//    }



//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserNotes loginRequest) {
//        Optional<UserNotes> user = userRepository.findByUsername(loginRequest.getUsername());
//
//        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
////            HashMap<String, String> response = new HashMap<>();
////            response.put("message", "Login successful!");
//            String message = "Login successful! Welcome, " + loginRequest.getUsername();
//            return ResponseEntity.ok(message); // Returns plain text
//        } else {
//            String error = "Invalid username or password";
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); // Returns plain text
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserNotes loginRequest) {
        Optional<UserNotes> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            // Generate JWT
            String token = Jwts.builder()
                    .setSubject(user.get().getUsername()) // Set username in token payload
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Token valid for 1 hour
                    .signWith(key) // Sign the token with the secret key
                    .compact();

            // Return the token
            HashMap<String, String> response = new HashMap<>();
            response.put("Login successful", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Expose the key for testing only (not recommended for production)
    public Key getKey() {
        return key;
    }
}
