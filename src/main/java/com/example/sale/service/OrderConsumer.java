package com.example.sale.service;

import com.example.sale.entity.Order;
import com.example.sale.mapper.OrderMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderConsumer(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @RabbitListener(queues = "orderQueue")
    public void processOrder(@Payload Order order) {
        // 将订单插入数据库
        System.out.println("Received order: " + order.getOrderId());
//        orderMapper.insertOrder(order);
    }
}