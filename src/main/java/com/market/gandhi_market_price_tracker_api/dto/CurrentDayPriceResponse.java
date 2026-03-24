package com.market.gandhi_market_price_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentDayPriceResponse {
    private Long id;
    private String name;
    private String tamilName;
    private int minPrice;
    private int maxPrice;
}
