package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.AuthRequest;
import com.market.gandhi_market_price_tracker_api.dto.AuthResponse;
import com.market.gandhi_market_price_tracker_api.dto.RefreshTokenRequest;
import com.market.gandhi_market_price_tracker_api.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }
}