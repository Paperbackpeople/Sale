package com.example.sale.controller;

import com.example.sale.entity.Order;
import com.example.sale.service.OrderService;
import com.example.sale.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sale.util.SnowflakeIdGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/")
public class OrderController {

    private final OrderService orderService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public OrderController(OrderService orderService, RedisTemplate<String, String> redisTemplate) {
        this.orderService = orderService;
        this.snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
        this.redisTemplate = redisTemplate;
    }
    @GetMapping("/generateToken")
    public ResponseEntity<String> generateToken() {
        String token = TokenGenerator.generateToken();
        System.out.println("token: " + token);
        redisTemplate.opsForValue().set(token, "1",5, TimeUnit.MINUTES);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        String token = order.getToken();
        // 检查 token 是否存在于 Redis 中
        if (Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
            // Token 存在，继续处理订单创建

            // 生成雪花算法的订单号
            Long orderNumber = generateOrderNumber(); // 替换成你的雪花算法生成逻辑
            order.setOrderId(orderNumber);

            // 设置订单的创建和更新时间
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            // 插入订单和订单项
            orderService.sendOrderToQueue(order);

            // 删除 Redis 中的 token，防止重复使用
            redisTemplate.delete(token);

            // 返回订单号给前端
            return new ResponseEntity<>(orderNumber.toString(), HttpStatus.CREATED);
        } else {
            // Token 不存在或已失效，返回错误信息
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }
    }
    // 使用雪花算法生成订单号
    private Long generateOrderNumber() {
        return snowflakeIdGenerator.nextId();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getUserOrders(@RequestParam String email) {
        List<Order> orders = orderService.getOrdersByEmail(email);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}