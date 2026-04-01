package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.CropRequest;
import com.market.gandhi_market_price_tracker_api.dto.CurrentDayPriceResponse;
import com.market.gandhi_market_price_tracker_api.dto.PriceRequest;
import com.market.gandhi_market_price_tracker_api.service.CropService;
import com.market.gandhi_market_price_tracker_api.service.PriceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminPriceController {

    private final PriceService priceService;
    private final CropService cropService;

    public  AdminPriceController(PriceService priceService,CropService cropService) {
        this.priceService = priceService;
        this.cropService = cropService;
    }

    @PostMapping("/prices")
    public ResponseEntity<?> savePrice(@Valid @RequestBody PriceRequest request) {
        priceService.savePrice(request);
        return ResponseEntity.ok("Price saved successfully");
    }

    @PostMapping("/addCrop")
    public ResponseEntity<?> saveCrop(@Valid @RequestBody CropRequest request){
        cropService.saveCrop(request);
        return ResponseEntity.ok("Crop saved successfully");
    }

    @GetMapping("/getCropsWithPrices")
    public List<CurrentDayPriceResponse> cropPriceForCurrentDay() {
        return priceService.cropPriceForCurrentDay();
    }

}
