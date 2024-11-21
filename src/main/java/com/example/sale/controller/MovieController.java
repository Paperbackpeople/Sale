package com.example.sale.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class MovieController {

    private static final String MAOYAN_API_URL = "https://m.maoyan.com/ajax/movieOnInfoList";

    @GetMapping("/movie")
    public ResponseEntity<String> fetchMovies() {
        try {
            // 创建 URL 对象
            URL url = new URL(MAOYAN_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法和请求头
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

            // 检查响应代码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .body(response.toString());
            } else {
                return ResponseEntity.status(responseCode).body("Failed to fetch movies from MaoYan API.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while fetching movie data.");
        }
    }
}