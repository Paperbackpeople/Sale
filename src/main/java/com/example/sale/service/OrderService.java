package com.example.sale.service;

import com.example.sale.entity.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public OrderService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendOrderToQueue(Order order) {
        amqpTemplate.convertAndSend("orderQueue", order);
    }
}