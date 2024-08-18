package com.example.sale.security;

import com.example.sale.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] EXCLUDED_PATHS = {"/api/send_code", "/api/verify_code", "/images/"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Skip token authentication for excluded paths
        for (String path : EXCLUDED_PATHS) {
            if (requestURI.startsWith(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String header = request.getHeader("Authorization");
        System.out.println("JwtAuthenticationFilter.doFilterInternal: header = " + header);

        if (header != null) {

            if (JwtUtil.validateToken(header)) {
                System.out.println("JwtAuthenticationFilter.doFilterInternal: token is valid");
                String email = JwtUtil.getEmailFromToken(header);
                // 将用户信息存储在请求上下文中
                request.setAttribute("email", email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>()); // Add authorities if available
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}