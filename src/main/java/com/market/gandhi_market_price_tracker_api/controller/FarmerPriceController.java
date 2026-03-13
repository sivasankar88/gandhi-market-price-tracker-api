package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.CropPriceTableResponse;
import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.service.CropService;
import com.market.gandhi_market_price_tracker_api.service.PriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class FarmerPriceController {

    private final PriceService priceService;
    private final CropService cropService;
    public FarmerPriceController(PriceService priceService, CropService cropService){
        this.priceService = priceService;
        this.cropService = cropService;
    }

    @GetMapping("/farmerDashboard")
    public List<CropPriceTableResponse> getLastFourDays(){
        return priceService.getLastFourDaysPrices();
    }

    @GetMapping("allCrops")
    public List<Crop> getAllCrops(){
        return this.cropService.getAllCrops();
    }
}
