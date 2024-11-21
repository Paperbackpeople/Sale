package com.example.sale.mapper;

import com.example.sale.entity.Order;
import com.example.sale.entity.OrderItem;
import com.example.sale.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

        @Insert("INSERT INTO orders(orderId, userId, totalAmount, status, createdAt, updatedAt,token, location) " +
                "VALUES (#{orderId}, #{userId}, #{totalAmount}, #{status}, #{createdAt}, #{updatedAt}, #{token}, #{location})")
        int insertOrder(Order order);
        @Select("SELECT * FROM orders WHERE order_id = #{orderId}")
        Order selectOrderById(Long orderId);

        @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY created_at DESC")
        @Results({
                @Result(property = "orderId", column = "order_id"),
                @Result(property = "userId", column = "user_id"),
                @Result(property = "totalAmount", column = "total_amount"),
                @Result(property = "status", column = "status"),
                @Result(property = "createdAt", column = "created_at"),
                @Result(property = "updatedAt", column = "updated_at"),
                @Result(property = "location", column = "location"),
                @Result(property = "orderItems", column = "order_id", many = @Many(select = "getOrderItemsByOrderId"))
        })
        List<Order> getOrdersByUserId(int userId);

        @Select("SELECT * FROM order_items WHERE order_id = #{orderId}")
        @Results({
                @Result(property = "orderItemId", column = "order_item_id"),
                @Result(property = "orderId", column = "order_id"),
                @Result(property = "productId", column = "product_id"),
                @Result(property = "quantity", column = "quantity"),
                @Result(property = "price", column = "price"),
                @Result(property = "product", column = "product_id", one = @One(select = "getProductById"))
        })
        List<OrderItem> getOrderItemsByOrderId(Long orderId);

        @Select("SELECT * FROM products WHERE product_id = #{productId}")
        Product getProductById(Long productId);
}