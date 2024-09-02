package com.example.sale.controller;
import com.example.sale.entity.user;
import com.example.sale.service.CaptchaService;
import com.example.sale.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.example.sale.service.UserService;
import com.example.sale.mapper.UserMapper;
import com.example.sale.mapper.LoginRecordMapper;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final CaptchaService captchaService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginRecordMapper loginRecordMapper;
    @Autowired
    private UserService userService;

    public AuthController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @PostMapping("/send_code")
    public ResponseEntity<String> sendCode(@RequestBody(required = false) Map<String, String> body) {
        try {
            String email = body.get("email");
            captchaService.sendEmail(email);
            return ResponseEntity.ok("Verification code sent to " + email);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return a 500 Internal Server Error response with a meaningful message
            return ResponseEntity.status(500).body("Failed to send verification code");
        }
    }

    @PostMapping("/verify_code")
    public ResponseEntity<String> verifyCode(@RequestBody(required = false) Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");
        try {
            if (captchaService.validateCaptcha(email, code)) {
                // Check if the email already exists
                boolean emailExists = userMapper.checkEmailExists(email);
                if (!emailExists) {
                    // Insert a new user
                    userMapper.insertUser(new user(email, new Timestamp(System.currentTimeMillis())));
                }

                // Record login regardless of whether it's a new user or an existing user
                String key = "email:exists:" + body.get("email");
                redisTemplate.opsForValue().set(key, "true", 5, TimeUnit.DAYS);
                userService.recordLogin(email);

                // Check if username already exists
                String username = userMapper.getUsernameByEmail(email);
                System.out.println(username);

                String token = JwtUtil.generateToken(email);
                if (username == null) {
                    // Redirect to username registration page
                    return ResponseEntity.ok()
                            .header("Authorization", "Bearer " + token)
                            .body("Verification successful, please register your username.");
                } else {
                    // User exists with a username, proceed with normal flow
                    return ResponseEntity.ok()
                            .header("Authorization", "Bearer " + token)
                            .body("Verification successful");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid verification code");
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return a 500 Internal Server Error response with a meaningful message
            return ResponseEntity.status(500).body("Failed to verify code");
        }
    }

    @PostMapping("/register_username")
    public ResponseEntity<String> registerUsername(@RequestBody(required = false) Map<String, String> body) {
        String email = body.get("email");
        String username = body.get("username");
        try {
            userService.updateUsername(username, email);
            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return a 500 Internal Server Error response with a meaningful message
            return ResponseEntity.status(500).body("Failed to register username");
        }
    }
}