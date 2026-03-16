package com.market.gandhi_market_price_tracker_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${node.notification.url}")
    private String url;

    public void sendWhatsappMessage(String cropName, String tamilName, Integer minPrice, Integer maxPrice) {
        Map<String,Object> body = new HashMap<>();
        body.put("cropName",cropName);
        body.put("tamilName",tamilName);
        body.put("minPrice",minPrice);
        body.put("maxPrice",maxPrice);

        restTemplate.postForObject(url, body, String.class);
    }
}
