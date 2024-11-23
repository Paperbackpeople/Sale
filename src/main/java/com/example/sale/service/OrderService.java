package com.example.sale.service;

import com.example.sale.entity.Order;
import com.example.sale.entity.OrderItem;
import com.example.sale.mapper.OrderItemMapper;
import com.example.sale.mapper.OrderMapper;
import com.example.sale.mapper.UserMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class OrderService {

    private final AmqpTemplate amqpTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    public OrderService(AmqpTemplate amqpTemplate, OrderMapper orderMapper, OrderItemMapper orderItemMapper, UserMapper userMapper) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendOrderToQueue(Order order) {
        amqpTemplate.convertAndSend("orderQueue", order);
    }
    public List<Order> getOrdersByEmail(String email) {
        return orderMapper.getOrdersByUserId(email);
    }
    public Order getOrderById(Long orderId) {
        return orderMapper.getOrderById(orderId);
    }

}