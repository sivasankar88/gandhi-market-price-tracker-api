package com.market.gandhi_market_price_tracker_api.service;

import com.market.gandhi_market_price_tracker_api.dto.CropPriceTableResponse;
import com.market.gandhi_market_price_tracker_api.dto.PriceRequest;
import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.entity.CropPrice;
import com.market.gandhi_market_price_tracker_api.repository.CropPriceRepository;
import com.market.gandhi_market_price_tracker_api.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PriceService {

    private final CropRepository cropRepository;
    private final CropPriceRepository cropPriceRepository;

    public PriceService(CropRepository cropRepository, CropPriceRepository cropPriceRepository) {
        this.cropRepository = cropRepository;
        this.cropPriceRepository = cropPriceRepository;
    }

    public void savePrice(PriceRequest request) {
        Crop crop = cropRepository.findById(request.getCropId()).orElseThrow(() -> new RuntimeException("Crop not found"));
        Integer min = request.getMinPrice();
        Integer max = request.getMaxPrice();
        Integer avg = (min + max) / 2;

        LocalDate today = LocalDate.now();

        Optional<CropPrice> existing = cropPriceRepository.findByCropIdAndPriceDate(request.getCropId(),today);
        CropPrice price;

        if(existing.isPresent()) {
            price = existing.get();
            price.setMinPrice(min);
            price.setMaxPrice(max);
            price.setAvgPrice(avg);
        }
        else {
            price = new CropPrice();
            price.setCrop(crop);
            price.setPriceDate(today);
            price.setMinPrice(min);
            price.setMaxPrice(max);
            price.setAvgPrice(avg);
        }
        cropPriceRepository.save(price);
    }
    public List<CropPriceTableResponse> getLastFourDaysPrices(){

        LocalDate date = LocalDate.now().minusDays(3);

        List<CropPrice> prices = cropPriceRepository.findByPriceDateAfter(date);

        Map<Long,CropPriceTableResponse> map = new LinkedHashMap<>();

        for(CropPrice price : prices){
            Long cropId = price.getCrop().getId();
            CropPriceTableResponse row = map.get(cropId);

            if(row == null){
                row = new CropPriceTableResponse(
                        price.getCrop().getName(),
                        price.getCrop().getTamilName()
                );
                map.put(cropId,row);
            }
            if(row.getMinPrice1()==null){
                row.setMinPrice1(price.getMinPrice());
                row.setMaxPrice1(price.getMaxPrice());
            }
            else if(row.getMinPrice2()==null){
                row.setMinPrice2(price.getMinPrice());
                row.setMaxPrice2(price.getMaxPrice());
            }
            else if(row.getMinPrice3()==null){
                row.setMinPrice3(price.getMinPrice());
                row.setMaxPrice3(price.getMaxPrice());
            }
            else if(row.getMinPrice4()==null){
                row.setMinPrice4(price.getMinPrice());
                row.setMaxPrice4(price.getMaxPrice());
            }


        }

        return new ArrayList<>(map.values());
    }
}
