package com.market.gandhi_market_price_tracker_api.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String error;
    private String path;
    private LocalDateTime timestamp;
}
