package com.example.sale.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Address {
    // Getters and Setters
    @Setter
    @Getter
    private Long addressId;
    @Setter
    @Getter
    private Long userId;
    @Setter
    @Getter
    private String addressLine;
    @Setter
    @Getter
    private String province;
    @Setter
    @Getter
    private String city;
    @Setter
    @Getter
    private String state;
    @Setter
    @Getter
    private boolean isPrimary; // 默认地址
    @Setter
    @Getter
    private LocalDateTime createdAt;
    @Setter
    @Getter
    private LocalDateTime updatedAt;
    @Setter
    @Getter
    private String tel;

    // toString
    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", userId=" + userId +
                ", addressLine='" + addressLine + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", isPrimary=" + isPrimary +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", tel='" + tel + '\'' +
                '}';
    }

}
