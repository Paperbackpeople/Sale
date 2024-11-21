package com.example.sale.mapper;

import com.example.sale.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface OrderItemMapper {

    @Insert("INSERT INTO order_items(orderItemId, orderId, productId, quantity, price) " +
            "VALUES (#{orderItemId}, #{orderId}, #{productId}, #{quantity}, #{price})")
    int insertOrderItem(OrderItem orderItem);
}