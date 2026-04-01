package com.market.gandhi_market_price_tracker_api.exception.custom;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
