package com.example.sale.controller;

import com.example.sale.entity.Product;
import com.example.sale.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Resource
    private RedisTemplate<String, List<Product>> redisTemplate;

    private static final String REDIS_PRODUCT_KEY = "products";
    @PostMapping("/products/checkAndInsert")
    public ResponseEntity<Long> checkAndInsertProduct(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String imageUrl = request.get("imageUrl");

        if (name == null || imageUrl == null) {
            return ResponseEntity.badRequest().build();
        }

        Long productId = productService.checkAndInsertProduct(name, imageUrl);
        return ResponseEntity.ok(productId);
    }

    // 获取产品详情
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. 尝试从 Redis 获取产品数据
            List<Product> products = redisTemplate.opsForValue().get(REDIS_PRODUCT_KEY);
            if (products == null || products.isEmpty()) {
                // 2. 如果 Redis 中没有数据，从数据库查询
                products = productService.getAllProducts();
                // 3. 将查询结果存储到 Redis，并设置过期时间（例如1小时）
                redisTemplate.opsForValue().set(REDIS_PRODUCT_KEY, products, 1, TimeUnit.HOURS);
            }
            response.put("status", "success");
            response.put("data", products);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to retrieve product data");
        }
        return ResponseEntity.ok(response);
    }
}