package com.market.gandhi_market_price_tracker_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropRequest {

    @NotBlank(message = "Crop name is required")
    private String name;

    @NotBlank(message = "Tamil name is required")
    private String tamilName;
}
