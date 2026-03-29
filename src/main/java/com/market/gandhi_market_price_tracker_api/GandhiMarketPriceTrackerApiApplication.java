package com.market.gandhi_market_price_tracker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GandhiMarketPriceTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GandhiMarketPriceTrackerApiApplication.class, args);
	}

}
