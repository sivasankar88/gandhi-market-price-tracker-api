package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.CropPriceTableResponse;
import com.market.gandhi_market_price_tracker_api.service.PriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/prices")
public class FarmerPriceController {

    private final PriceService priceService;

    public FarmerPriceController(PriceService priceService){
        this.priceService = priceService;
    }

    @GetMapping("/farmerDashboard")
    public List<CropPriceTableResponse> getLastFourDays(){
        return priceService.getLastFourDaysPrices();
    }
}
