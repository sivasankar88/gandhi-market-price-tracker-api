package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.PriceRequest;
import com.market.gandhi_market_price_tracker_api.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/prices")
public class AdminPriceController {

    private final PriceService priceService;

    public  AdminPriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public ResponseEntity<?> savePrice(@RequestBody PriceRequest request) {
        priceService.savePrice(request);
        return ResponseEntity.ok("Price saved successfully");
    }
}
