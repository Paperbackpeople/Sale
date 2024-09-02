package com.example.sale.util;

import java.util.UUID;

public class TokenGenerator {
    // 使用 UUID 生成唯一的 token
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}