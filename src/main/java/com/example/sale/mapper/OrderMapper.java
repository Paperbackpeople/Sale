package com.example.sale.mapper;

import com.example.sale.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders(user_id, total_amount, status, created_at, updated_at, location) " +
            "VALUES (#{userId}, #{totalAmount}, #{status}, #{createdAt}, #{updatedAt}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insertOrder(Order order);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    Order selectOrderById(Long userId);
}