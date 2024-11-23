package com.example.sale.mapper;

import com.example.sale.entity.Order;
import com.example.sale.entity.OrderItem;
import com.example.sale.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

        @Insert("INSERT INTO orders(orderId, userId, totalAmount, status, createdAt, updatedAt, token, location) " +
                "VALUES (#{orderId}, #{userId}, #{totalAmount}, #{status}, #{createdAt}, #{updatedAt}, #{token}, #{location})")
        int insertOrder(Order order);

        @Select("SELECT * FROM orders WHERE orderId = #{orderId}")
        Order selectOrderById(Long orderId);

        @Select("SELECT * FROM orders WHERE userId = #{userId} ORDER BY createdAt DESC")
        @Results({
                @Result(property = "orderId", column = "orderId"),
                @Result(property = "userId", column = "userId"),
                @Result(property = "totalAmount", column = "totalAmount"),
                @Result(property = "status", column = "status"),
                @Result(property = "createdAt", column = "createdAt"),
                @Result(property = "updatedAt", column = "updatedAt"),
                @Result(property = "location", column = "location"),
                @Result(property = "orderItems", column = "orderId", many = @Many(select = "getOrderItemsByOrderId"))
        })
        List<Order> getOrdersByUserId(String userId);

        @Select("SELECT * FROM order_items WHERE orderId = #{orderId}")
        @Results({
                @Result(property = "orderItemId", column = "orderItemId"),
                @Result(property = "orderId", column = "orderId"),
                @Result(property = "productId", column = "productId"),
                @Result(property = "quantity", column = "quantity"),
                @Result(property = "price", column = "price"),
                @Result(property = "product", column = "productId", one = @One(select = "getProductById"))
        })
        List<OrderItem> getOrderItemsByOrderId(Long orderId);


        @Select("SELECT * FROM orders WHERE orderId = #{orderId}")
        @Results({
                @Result(property = "orderId", column = "orderId"),
                @Result(property = "userId", column = "userId"),
                @Result(property = "totalAmount", column = "totalAmount"),
                @Result(property = "status", column = "status"),
                @Result(property = "createdAt", column = "createdAt"),
                @Result(property = "updatedAt", column = "updatedAt"),
                @Result(property = "location", column = "location"),
                @Result(property = "orderItems", column = "orderId", many = @Many(select = "getOrderItemsByOrderId"))
        })
        Order getOrderById(Long orderId);
        @Select("SELECT * FROM products WHERE product_id = #{productId}")
        Product getProductById(Long productId);
}