package com.example.sale.controller;

import com.example.sale.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class IpController {
    @GetMapping("/location")
    public ResponseEntity<Map<String, Object>> getLocation(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String ip = IpUtil.getIpAddr(request);
            System.out.println("IpController.getLocation: ip = " + ip);
            String cityInfo = IpUtil.getIpPossession(ip);
            response.put("status", "success");
            response.put("location", cityInfo);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "获取位置信息失败");
        }
        return ResponseEntity.ok(response);
    }

}
