package com.market.gandhi_market_price_tracker_api.exception.custom;

public class MailDeliveryException extends RuntimeException {
    public MailDeliveryException(String message){
        super(message);
    }
}
