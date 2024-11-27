package com.example.sale.listener;
import com.example.sale.entity.Order;
import com.example.sale.entity.OrderItem;
import com.example.sale.mapper.OrderItemMapper;
import com.example.sale.mapper.OrderMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.sale.config.RabbitMQConfig;

@Component
public class OrderListener {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderListener(OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

@RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
public void handleOrder(Order order) {
    try {
        // 处理订单的异步逻辑
        System.out.println("Processing order: " + order);

        // 插入订单
        orderMapper.insertOrder(order);

        // 插入订单项
        for (OrderItem item : order.getOrderItems()) {
            item.setOrderId(order.getOrderId());
            orderItemMapper.insertOrderItem(item);
        }
    } catch (Exception e) {
        // 捕获并记录异常
        System.err.println("Failed to process order: " + order.getOrderId());
        e.printStackTrace();
    }
}
}