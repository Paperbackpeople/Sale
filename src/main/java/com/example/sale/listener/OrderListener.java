package com.example.sale.listener;
import com.example.sale.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.sale.config.RabbitMQConfig;

@Component
public class OrderListener {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrder(Order order) {
        // Handle the order processing asynchronously
        System.out.println("Processing order: " + order);
        // Add logic to process the order, such as sending confirmation emails, etc.
    }
}