package com.market.gandhi_market_price_tracker_api.service;

import com.market.gandhi_market_price_tracker_api.dto.CropRequest;
import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.exception.custom.DuplicateResourceException;
import com.market.gandhi_market_price_tracker_api.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CropService {

    private final CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public void saveCrop(CropRequest request) {
        boolean exists = cropRepository.existsByName(request.getName());

        if(exists) throw new DuplicateResourceException("Crop already exists: " + request.getName());

        Crop crop = new Crop();
        crop.setName(request.getName());
        crop.setTamilName(request.getTamilName());
        cropRepository.save(crop);
    }

}
