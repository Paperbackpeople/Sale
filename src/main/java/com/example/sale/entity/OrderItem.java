package com.example.sale.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderItemId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private Product product;

}