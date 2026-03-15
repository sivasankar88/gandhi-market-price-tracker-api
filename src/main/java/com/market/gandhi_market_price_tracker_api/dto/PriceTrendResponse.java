package com.market.gandhi_market_price_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriceTrendResponse {

    private String label;
    private Integer value;

}
