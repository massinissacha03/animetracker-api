package com.animetracker.animetracker_api.service;

import com.animetracker.animetracker_api.entity.User;
import com.animetracker.animetracker_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Creer un nouvel utilisateur
    public User createUser(String username, String email, String password, String role) {
        // Verifier si l'utilisateur existe deja
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Ce nom d'utilisateur existe deja");
        }
        
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Cet email est deja utilise");
        }
        
        // Creer le user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // Hash le mot de passe
        user.setRole(role != null && !role.isEmpty() ? role : "USER");  // Par defaut USER
        
        return userRepository.save(user);
    }
    
    // Authentifier un utilisateur
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Verifier le mot de passe
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
    
    // Trouver un user par username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
