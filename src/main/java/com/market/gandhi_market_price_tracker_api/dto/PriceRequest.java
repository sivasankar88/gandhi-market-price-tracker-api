package com.market.gandhi_market_price_tracker_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRequest {

    private Long cropId;
    private Integer minPrice;
    private Integer maxPrice;
}
