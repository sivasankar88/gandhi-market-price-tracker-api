package com.market.gandhi_market_price_tracker_api.service;

import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CropService {

    private final CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

}
