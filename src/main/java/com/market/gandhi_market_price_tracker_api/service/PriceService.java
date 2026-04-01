package com.market.gandhi_market_price_tracker_api.service;

import com.market.gandhi_market_price_tracker_api.dto.*;
import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.entity.CropPrice;
import com.market.gandhi_market_price_tracker_api.exception.custom.InvalidRequestException;
import com.market.gandhi_market_price_tracker_api.exception.custom.ResourceNotFoundException;
import com.market.gandhi_market_price_tracker_api.repository.CropPriceRepository;
import com.market.gandhi_market_price_tracker_api.repository.CropRepository;
import org.springframework.stereotype.Service;
import com.market.gandhi_market_price_tracker_api.dto.PriceTrendResponse;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Locale;

@Service
public class PriceService {

    private final CropRepository cropRepository;
    private final CropPriceRepository cropPriceRepository;
    private final NotificationService notificationService;

    public PriceService(CropRepository cropRepository, CropPriceRepository cropPriceRepository, NotificationService notificationService) {
        this.cropRepository = cropRepository;
        this.cropPriceRepository = cropPriceRepository;
        this.notificationService = notificationService;
    }

    public void savePrice(PriceRequest request) {
        Crop crop = cropRepository.findById(request.getCropId()).orElseThrow(() -> new ResourceNotFoundException("Crop not found with id: " + request.getCropId()));
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
        //notificationService.sendWhatsappMessage(price.getCrop().getName(),price.getCrop().getTamilName(),price.getMinPrice(),price.getMaxPrice());
    }

    public List<CurrentDayPriceResponse> cropPriceForCurrentDay () {
        LocalDate date = LocalDate.now();
        return cropPriceRepository.cropPriceForCurrentDay(date);
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
                        price.getCrop().getId(),
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

    public List<PriceTrendResponse> getTrend(Long cropId, String type) {

        return switch (type) {
            case "LAST30" -> Last30DaysTrend(cropId);
            case "MONTHLY" -> monthlyTrend(cropId);
            case "YEARLY" -> yearlyTrend(cropId);
            default ->
                    throw new InvalidRequestException("Invalid trend type: " + type + ". Accepted: LAST30, MONTHLY, YEARLY");
        };
    }

    private List<PriceTrendResponse> Last30DaysTrend(Long cropId) {

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(29);

        List<CropPrice> prices =
                cropPriceRepository.findByCropIdAndPriceDateBetweenOrderByPriceDate(
                        cropId,
                        start,
                        end
                );

        return prices.stream()
                .map(p -> new PriceTrendResponse(
                        p.getPriceDate().toString(),
                        p.getAvgPrice()
                ))
                .collect(Collectors.toList());
    }

    private List<PriceTrendResponse> monthlyTrend(Long cropId) {

        List<Object[]> results = cropPriceRepository.getMonthlyAverage(cropId);

        return results.stream()
                .map(r -> new PriceTrendResponse(
                        Month.of(((Number) r[0]).intValue())
                                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                        ((Number) r[1]).intValue()
                ))
                .collect(Collectors.toList());
    }

    private List<PriceTrendResponse> yearlyTrend(Long cropId) {

        List<Object[]> results = cropPriceRepository.getYearlyAverage(cropId);

        return results.stream()
                .map(r -> new PriceTrendResponse(
                        String.valueOf(((Number) r[0]).intValue()),
                        ((Number) r[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
}
