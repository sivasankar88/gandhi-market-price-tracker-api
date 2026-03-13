package com.market.gandhi_market_price_tracker_api.repository;

import com.market.gandhi_market_price_tracker_api.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop,Long> {
}
