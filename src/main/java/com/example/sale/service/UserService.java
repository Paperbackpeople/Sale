package com.example.sale.service;

import com.example.sale.mapper.LoginRecordMapper;
import com.example.sale.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.example.sale.entity.LoginRecord;

import java.sql.Timestamp;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private LoginRecordMapper loginRecordMapper;

    @Resource
    private RedisTemplate<String, Timestamp> redisTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplateString;

    public boolean checkEmailExists(String email) {
        //先检查redis中是否存在
        String key = "email:exists:" + email;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return true;
        }
        return userMapper.checkEmailExists(email);
    }

    public void recordLogin(String email) {
        int userId = userMapper.getUserIdByEmail(email);
        LoginRecord loginRecord = new LoginRecord(userId, new Timestamp(System.currentTimeMillis()));
        loginRecordMapper.insertLoginRecord(loginRecord);

        // 使用Redis记录登录时间
        String key = "login:times:" + email;
        redisTemplate.opsForList().leftPush(key, loginRecord.getLoginTime());
    }

    //记录用户名
    public void updateUsername(String username, String email) {
        userMapper.updateUsername(username, email);

        // 使用Redis记录用户名
        String key = "username:" + email;
        redisTemplateString.opsForValue().set(key, username);
    }
}

