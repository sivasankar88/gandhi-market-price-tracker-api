package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.PriceRequest;
import com.market.gandhi_market_price_tracker_api.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminPriceController {

    private final PriceService priceService;

    public  AdminPriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/prices")
    public ResponseEntity<?> savePrice(@RequestBody PriceRequest request) {
        priceService.savePrice(request);
        return ResponseEntity.ok("Price saved successfully");
    }

}
