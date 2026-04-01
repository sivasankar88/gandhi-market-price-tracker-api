package com.market.gandhi_market_price_tracker_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRequest {

    @NotNull(message = "Crop ID is required")
    private Long cropId;

    @NotNull(message = "Min price is required")
    @Min(value = 0, message = "Min price cannot be negative")
    private Integer minPrice;

    @NotNull(message = "Max price is required")
    @Min(value = 0, message = "Max price cannot be negative")
    private Integer maxPrice;
}
