package com.example.sale.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.example.sale.util.EmailUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final EmailUtil emailUtil;
    public CaptchaService(StringRedisTemplate redisTemplate, EmailUtil emailUtil) {
        this.redisTemplate = redisTemplate;
        this.emailUtil = emailUtil;
    }
    private static final String CAPTCHA_PREFIX = "captcha:";

    public String generateCaptcha(String email) {
        String captcha = generateRandomCaptcha();
        // 存储验证码到 Redis，设置过期时间为5分钟
        redisTemplate.opsForValue().set(CAPTCHA_PREFIX + email, captcha, 5, TimeUnit.MINUTES);
        return captcha;
    }

    public boolean validateCaptcha(String email, String captcha) {
        String storedCaptcha = redisTemplate.opsForValue().get(CAPTCHA_PREFIX + email);
        return captcha != null && captcha.equals(storedCaptcha);
    }

    private String generateRandomCaptcha() {
        Random random = new Random();
        int captcha = 100000 + random.nextInt(900000); // 生成六位随机验证码
        return String.valueOf(captcha);
    }

    public void sendEmail(String email) {
        // Send email here
        String captcha = generateCaptcha(email);
        System.out.println("Sending email to " + email + " with verification code: " + captcha);
        String subject = "Your Verification Code for Sale App";
        String emailText = "Your verification code is " + captcha + ". It is valid for 1 minute.";
        emailUtil.sendEmail(email, subject, emailText);

    }
}
