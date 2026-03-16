package com.market.gandhi_market_price_tracker_api.service;

import com.market.gandhi_market_price_tracker_api.dto.AuthRequest;
import com.market.gandhi_market_price_tracker_api.dto.AuthResponse;
import com.market.gandhi_market_price_tracker_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {
        if (!request.getUsername().equals(adminUsername) ||
                !request.getPassword().equals(adminPassword)) {
            throw new RuntimeException("Invalid credentials");
        }
        String accessToken = jwtUtil.generateAccessToken(request.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {
        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }
        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(username);
        return new AuthResponse(newAccessToken, refreshToken);
    }
}