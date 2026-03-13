package com.market.gandhi_market_price_tracker_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropPriceTableResponse {
    private String cropName;
    private String tamilName;
    private Integer minPrice1;
    private Integer minPrice2;
    private Integer minPrice3;
    private Integer minPrice4;
    private Integer maxPrice1;
    private Integer maxPrice2;
    private Integer maxPrice3;
    private Integer maxPrice4;

    public CropPriceTableResponse(String cropName,String tamilName){
        this.cropName = cropName;
        this.tamilName = tamilName;
    }

}
