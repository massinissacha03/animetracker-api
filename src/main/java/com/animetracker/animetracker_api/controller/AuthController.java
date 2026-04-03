package com.animetracker.animetracker_api.controller;

import com.animetracker.animetracker_api.dto.AuthResponse;
import com.animetracker.animetracker_api.dto.LoginRequest;
import com.animetracker.animetracker_api.dto.RegisterRequest;
import com.animetracker.animetracker_api.entity.User;
import com.animetracker.animetracker_api.security.JwtUtil;
import com.animetracker.animetracker_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // POST /auth/register - Inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
            );
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Utilisateur cree avec succes");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    // POST /auth/login - Connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<User> userOpt = userService.authenticate(
            request.getUsername(),
            request.getPassword()
        );
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Generer le token JWT
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            
            // Retourner la reponse avec le token
            AuthResponse response = new AuthResponse(token, user.getUsername(), user.getRole());
            return ResponseEntity.ok(response);
            
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Identifiants invalides");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}
