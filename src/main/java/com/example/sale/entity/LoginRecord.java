package com.example.sale.entity;

import java.sql.Timestamp;

public class LoginRecord {
    private int userId;
    private Timestamp loginTime;

    public LoginRecord(int userId, Timestamp loginTime) {
        this.userId = userId;
        this.loginTime = loginTime;
    }

    // getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
