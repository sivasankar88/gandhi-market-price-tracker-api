package com.market.gandhi_market_price_tracker_api.controller;

import com.market.gandhi_market_price_tracker_api.dto.CropPriceTableResponse;
import com.market.gandhi_market_price_tracker_api.dto.PriceTrendResponse;
import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.service.CropService;
import com.market.gandhi_market_price_tracker_api.service.PriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class FarmerPriceController {

    private final PriceService priceService;
    public FarmerPriceController(PriceService priceService){
        this.priceService = priceService;
    }

    @GetMapping("/farmerDashboard")
    public List<CropPriceTableResponse> getLastFourDays(){
        return priceService.getLastFourDaysPrices();
    }

    @GetMapping("/prices/trend")
    public List<PriceTrendResponse> getPriceTrend(@RequestParam Long cropId, @RequestParam String type) {
        return priceService.getTrend(cropId,type);
    }
}
