package com.example.user_management.controller;

import com.example.user_management.dto.UserLoginDto;
import com.example.user_management.dto.UserRegistrationDto;
import com.example.user_management.model.User;
import com.example.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser (@RequestBody User user) {
        return userService.saveUser (user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        userService.deleteUser (id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    @PostMapping("/register")
    public ResponseEntity<User> registerUser (@RequestBody UserRegistrationDto registrationDto) {
        User newUser  = new User(registrationDto.getUsername(), registrationDto.getPassword());
        User createdUser  = userService.saveUser (newUser );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser );
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser (@RequestBody UserLoginDto loginDto) {
        boolean isAuthenticated = userService.authenticateUser(loginDto.getUsername(), loginDto.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}