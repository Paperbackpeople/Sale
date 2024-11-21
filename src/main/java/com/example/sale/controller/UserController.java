package com.example.sale.controller;

import com.example.sale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(@RequestParam String email) {
        String username = userService.getUsername(email);
        if (username != null && !username.isEmpty()) {
            return ResponseEntity.ok(username);
        } else {
            return ResponseEntity.ok("Username not set for this email.");
        }
    }
}